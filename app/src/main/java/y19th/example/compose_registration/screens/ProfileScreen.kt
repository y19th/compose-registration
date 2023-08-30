package y19th.example.compose_registration.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import y19th.example.compose_registration.R
import y19th.example.compose_registration.extension.shortToast
import y19th.example.compose_registration.room.entity.User
import y19th.example.compose_registration.ui.theme.ComposeTheme
import y19th.example.compose_registration.viewmodel.DatabaseViewModel

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(userName = "some name")
}

@Composable
fun ProfileScreen(userName : String?) {
    
    val context = LocalContext.current

    val databaseViewModel by remember {
        mutableStateOf(DatabaseViewModel().also { it.init(context) })
    }
    var currentPasswordValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var newPasswordValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var user by remember {
        mutableStateOf(User())
    }
    if(userName != null) {
        LaunchedEffect(Unit) {
            databaseViewModel.also {
                it.getUserByName(userName)
            }.user.collect {
                user = it
            }
        }
    }

    ComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.contacts),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                ProfileLabel(
                    header = stringResource(id = R.string.name),
                    value = user.name
                )
                ProfileLabel(
                    header = stringResource(id = R.string.email),
                    value = user.email
                )
                ProfileLabel(
                    header = stringResource(id = R.string.password),
                    value = user.password
                )
                TextFieldWithHeader(
                    value = currentPasswordValue,
                    headerId = R.string.current_password
                ) {
                    currentPasswordValue = it
                }
                TextFieldWithHeader(
                    value = newPasswordValue,
                    headerId = R.string.new_password
                ) {
                    newPasswordValue = it
                }
                Spacer(
                    modifier = Modifier.height(40.dp)
                )
                OutlinedButton(
                    onClick = {
                        if(currentPasswordValue.text == user.password) {
                            when(newPasswordValue.text.length) {
                                0 -> context.shortToast("Новый пароль не может быть пустым")
                                in 1..5 -> context.shortToast("Длина пароля не меньше 6 симоволов")
                                else -> {
                                    databaseViewModel.setUserPassword(
                                        newPassword = newPasswordValue.text,
                                        userId = user.id
                                    )
                                    context.shortToast("Пароль успешно изменен")
                                }
                            }
                        } else {
                            context.shortToast("Неправильно введенный текущий пароль")
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.change_password),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}


@Composable
fun ProfileLabel(header: String, value: String) {
    Column (
        modifier = Modifier.padding(vertical = 12.dp)
    ){
        Text(
            text = value,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = header,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}