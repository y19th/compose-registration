package y19th.example.compose_registration.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import y19th.example.compose_registration.room.entity.User
import y19th.example.compose_registration.ui.theme.ComposeTheme
import y19th.example.compose_registration.viewmodel.DatabaseViewModel

@Composable
fun ProfileScreen() {
    
    val context = LocalContext.current
    
    var userList by remember {
        mutableStateOf(emptyList<User>())
    }

    val databaseViewModel by remember {
        mutableStateOf(DatabaseViewModel().also { it.init(context) })
    }

    LaunchedEffect(Unit) {
        databaseViewModel.users.collect {
            userList = it
        }
    }
    LazyColumnWithItems(users = userList)
}

@Composable
fun UserCard(user: User) {
    Row {
        Text(text = user.name, modifier = Modifier.padding(all = 5.dp))
        Text(text = user.email, modifier = Modifier.padding(all = 5.dp))
        Text(text = user.password, modifier = Modifier.padding(all = 5.dp))
    }
}

@Composable
fun LazyColumnWithItems(users: List<User>) {
    ComposeTheme {
        Surface {
            LazyColumn {
                items(users) { user ->
                    UserCard(user = user)
                }
            }
        }
    }
}