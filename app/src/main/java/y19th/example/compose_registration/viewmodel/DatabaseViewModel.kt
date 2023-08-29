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

class DatabaseViewModel(val context: Context) : ViewModel() {

    private val _users = MutableStateFlow(listOf<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private var _database: UserDatabase? = null
    val database: UserDatabase get() = requireNotNull(_database)

    init {
        _database = UserDatabase.getDatabase(context = context)
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = database.userDao().getUsers()
            _users.update {
                result
            }
        }
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.Default) {
            database.userDao().insertUser(user)
        }
    }
}