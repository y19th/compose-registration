package y19th.example.compose_registration.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import y19th.example.compose_registration.ui.theme.ComposeTheme
import y19th.example.compose_registration.viewmodel.DatabaseViewModel

@Composable
fun ProfileScreen() {
    
    val context = LocalContext.current

    val databaseViewModel by remember {
        mutableStateOf(DatabaseViewModel(context = context))
    }
    
    ComposeTheme {
        Surface {
            LazyColumn {
                databaseViewModel
            }
        }
    }
}