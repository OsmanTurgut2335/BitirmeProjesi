    package com.osman.bitirmeprojesi.viewmodels

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.google.firebase.auth.FirebaseUser
    import com.osman.bitirmeprojesi.repo.Repository
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject

    @HiltViewModel
    class LoginScreenViewModel @Inject constructor(var repository: Repository) : ViewModel(){
        private val _loginResult = MutableLiveData<Result<FirebaseUser>>()
        val loginResult: LiveData<Result<FirebaseUser>> = _loginResult

             fun login(username :String, password:String){
                repository.login(username, password) { result ->
                    _loginResult.postValue(result)
    }
    }
    }