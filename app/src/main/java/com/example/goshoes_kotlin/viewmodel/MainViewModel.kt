package com.example.goshoes_kotlin.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.goshoes_kotlin.model.AppDataBase
import com.example.goshoes_kotlin.model.ShoeEntity
import com.example.goshoes_kotlin.model.SizeInfoEntity
import com.example.goshoes_kotlin.model.UserInfoEntity
import com.example.goshoes_kotlin.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.util.prefs.Preferences

enum class FilterOption {
    BRAND,
    COLOR,
    STYLE,
}

data class UIState(
    val isBackBtnVisible: Boolean = true,
    val isActionBtnVisible: Boolean = true,
    val isFABVisible: Boolean = true,
)

data class FilterState(
    val selectedBrand: Set<String> = emptySet(),
    val selectedColor: Set<String> = emptySet(),
    val selectedStyle: Set<String> = emptySet()
)

class MainViewModel(
    context: Context,
) : ViewModel() {

    private val shoeDAO = AppDataBase.getDataBase(context).shoeDAO()
    private val sizeInfoDAO = AppDataBase.getDataBase(context).sizeInfoDAO()
    private val userInfoDAO = AppDataBase.getDataBase(context).userInfoDAO()

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    fun updateUIState(
        isBackBtnVisible: Boolean,
        isActionBtnVisible: Boolean,
        isFABVisible: Boolean
    ) {
        _uiState.value = _uiState.value.copy(
            isBackBtnVisible = isBackBtnVisible,
            isActionBtnVisible = isActionBtnVisible,
            isFABVisible = isFABVisible,
        )
    }

    fun toggleFilter(option: FilterOption, value: String, isChecked: Boolean) {
        _filterState.value = _filterState.value.copy(
            selectedBrand = if (option == FilterOption.BRAND) toggleItem(value, isChecked, _filterState.value.selectedBrand) else _filterState.value.selectedBrand,
            selectedColor = if (option == FilterOption.COLOR) toggleItem(value, isChecked, _filterState.value.selectedColor) else _filterState.value.selectedColor,
            selectedStyle = if (option == FilterOption.STYLE) toggleItem(value, isChecked, _filterState.value.selectedStyle) else _filterState.value.selectedStyle
        )
    }

    private fun toggleItem(value: String, isChecked: Boolean, currentSet: Set<String>): Set<String> {
        return if (isChecked) currentSet + value else currentSet - value
    }

    fun resetFilters() {
        _filterState.value = FilterState()
    }

    fun getFilteredShoes(): Flow<List<ShoeEntity>> {

        val filters = _filterState.value

        val brands = filters.selectedBrand.toList()
        val colors = filters.selectedColor.toList()
        val styles = filters.selectedStyle.toList()

        // SQL 쿼리 동적 생성
        val queryBuilder = StringBuilder("SELECT * FROM shoe WHERE 1=1")

        if (brands.isNotEmpty()) {
            queryBuilder.append(" AND brand IN (${brands.joinToString { "'$it'" }})")
        }

        if (colors.isNotEmpty()) {
            queryBuilder.append(" AND color IN (${colors.joinToString { "'$it'" }})")
        }

        if (styles.isNotEmpty()) {
            queryBuilder.append(" AND style IN (${styles.joinToString { "'$it'" }})")
        }

        val query = SimpleSQLiteQuery(queryBuilder.toString())
        return shoeDAO.getFilteredShoesRawQuery(query)
    }

    val shoeList = shoeDAO.getAllShoes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getShoe(productCode: String): Flow<ShoeEntity> {
        return shoeDAO.getShoe(productCode)
    }

    fun getSizeInfo(productCode: String): Flow<List<SizeInfoEntity>> {
        return sizeInfoDAO.getSizesByProductCode(productCode)
    }

    suspend fun getImages(productCode: String): List<String> {

        val shoe = getShoe(productCode).first()

        return JSONArray(shoe.images).let { jsonArray ->
            List(jsonArray.length()) { index -> jsonArray.getString(index) }
        }
    }

    fun getDistinctFilter(option: FilterOption): Flow<List<String>> {
        return when (option) {
            FilterOption.BRAND -> shoeDAO.getDistinctBrand()
            FilterOption.COLOR -> shoeDAO.getDistinctColor()
            FilterOption.STYLE -> shoeDAO.getDistinctStyle()
        }
    }

    suspend fun addUser(user: UserInfoEntity) {
        userInfoDAO.addUser(user)
    }

    fun getUserInfo(email: String) : Flow<UserInfoEntity> {
        return userInfoDAO.getUserInfo(email)
    }



    private val dataStore = context.dataStore

    private val USERNAME_KEY = stringPreferencesKey("username_key")
    //private val PASSWORD_KEY = stringPreferencesKey("password_key")
    val username: Flow<String> = dataStore.data
        .map { preferences -> preferences[USERNAME_KEY] ?: "" }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[USERNAME_KEY] = username
            }
        }
    }

    fun clearUsername() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(USERNAME_KEY)
            }
        }
    }
}

