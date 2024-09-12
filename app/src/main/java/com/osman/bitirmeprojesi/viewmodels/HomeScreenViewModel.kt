package com.osman.bitirmeprojesi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(val repository: Repository) : ViewModel(){
   val allFoodList = MutableLiveData<List<Food>>()

    init {
        loadAllFood()
    }

    fun loadAllFood() {

        CoroutineScope(Dispatchers.Main).launch {

            allFoodList.value=repository.loadAllFood()

        }
    }


}