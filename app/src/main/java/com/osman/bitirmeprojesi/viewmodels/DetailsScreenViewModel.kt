package com.osman.bitirmeprojesi.viewmodels

import androidx.lifecycle.ViewModel
import com.osman.bitirmeprojesi.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(val repository: Repository) : ViewModel() {
}