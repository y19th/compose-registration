package y19th.example.compose_registration.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import y19th.example.compose_registration.room.UserDatabase
import y19th.example.compose_registration.room.entity.User

class DatabaseViewModel : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user.asStateFlow()

    private var _database: UserDatabase? = null
    private val database: UserDatabase get() = requireNotNull(_database)

    fun init(context: Context) {
        _database = UserDatabase.getDatabase(context = context)
    }
    fun setUserPassword(newPassword: String, userId: Int) {
        viewModelScope.launch (Dispatchers.IO){
            database.userDao().setUserPassword(
                userPassword = newPassword,
                userId = userId
            )
            _user.update { oldUser ->
                oldUser.copy(
                    id = oldUser.id,
                    name = oldUser.name,
                    email = oldUser.email,
                    password = newPassword
                )
            }
        }
    }

    fun getUserByName(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = database.userDao().getUserByName(userName)
            _user.update {
                result
            }
        }
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().insertUser(user)
        }
    }
}