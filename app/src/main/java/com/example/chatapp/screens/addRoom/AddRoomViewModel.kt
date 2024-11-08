package com.example.chatapp.screens.addRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.screens.entity.Category
import com.example.domain.entity.Room
import com.example.domain.useCases.chat.AddRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val addRoomUseCase: AddRoomUseCase
) : BaseViewModel() {
    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf("")
    val roomDescState = mutableStateOf("")
    val roomDescErrorState = mutableStateOf("")
    val isDropDownExpanded = mutableStateOf(false)
    val categoriesList: List<Category> = Category.getCategoriesList()
    val SelectedRoomCategoryName = mutableStateOf(categoriesList[0].categoryName)
    val selectedCategory = mutableStateOf(categoriesList[0])
    val isDone = mutableStateOf(false)


    fun validateFields(): Boolean {

        if (roomNameState.value.isEmpty() || roomNameState.value.isBlank()) {
            roomNameErrorState.value = "required"
            return false
        } else
            roomNameErrorState.value = ""

        if (roomDescState.value.isEmpty() || roomDescState.value.isBlank()) {
            roomDescErrorState.value = "required"
            return false
        } else
            roomDescErrorState.value = ""

        return true
    }

    fun addRoomToFirestore() {
        if (validateFields())
            viewModelScope.launch {
                showLoading()
                val room = Room(
                    name = roomNameState.value,
                    categoryName = selectedCategory.value.categoryName,
                    categoryId = selectedCategory.value.categoryId,
                    descriptionName = roomDescState.value
                )
                addRoomUseCase(room,{
                    hideLoading()
                    isDone.value = true
                },{
                    hideLoading()
                    showErrorMessage("${it.message}")
                })
            }
    }

}