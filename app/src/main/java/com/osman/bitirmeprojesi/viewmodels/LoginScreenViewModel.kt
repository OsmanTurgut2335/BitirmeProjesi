    package com.osman.bitirmeprojesi.viewmodels

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import com.osman.bitirmeprojesi.repo.Repository
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject

    @HiltViewModel
    class LoginScreenViewModel @Inject constructor(var repository: Repository) : ViewModel(){
        private val _loginResult = MutableLiveData<Result<FirebaseUser>?>()
        val loginResult: LiveData<Result<FirebaseUser>?> = _loginResult

        private val _createUserResult = MutableLiveData<Result<Boolean>?>()
        val createUserResult: LiveData<Result<Boolean>?> get() = _createUserResult

             fun login(username :String, password:String){
                repository.login(username, password) { result ->
                    _loginResult.postValue(result)
    }
    }

        // Function to reset the login result
        fun clearLoginResult() {
            _loginResult.value = null
        }
        fun clearCreateUserResult() {
            _createUserResult.value = null
        }


        fun createUser(email: String, password: String) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _createUserResult.value = if (task.isSuccessful) {
                        Result.success(true)
                    } else {
                        Result.failure(task.exception ?: Exception("Unknown error"))
                    }
                }
        }
    }