package com.osman.bitirmeprojesi.viewmodels

import androidx.lifecycle.ViewModel
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(val repository: Repository) : ViewModel(){



    // Method to get the favorite foods list
    fun getFavoriteFoods(): List<Food> {
        return repository.getFavoriteFoods()
    }


}