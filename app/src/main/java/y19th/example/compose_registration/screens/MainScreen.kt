package y19th.example.compose_registration.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import y19th.example.compose_registration.R
import y19th.example.compose_registration.extension.shortToast
import y19th.example.compose_registration.room.entity.User
import y19th.example.compose_registration.ui.theme.ComposeTheme
import y19th.example.compose_registration.viewmodel.DatabaseViewModel

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen {}
}


@Composable
fun MainScreen(navigateForward: (String) -> Unit) {

    val context = LocalContext.current

    val databaseViewModel by remember {
        mutableStateOf(DatabaseViewModel().also { it.init(context = context)})
    }

    var nameValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var emailValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var passwordValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    ComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 40.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.insert_data),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.7F)
                )
                TextFieldWithHeader(headerId = R.string.name, value = nameValue) {
                    if(it.text.length <= 16)nameValue = it
                }
                TextFieldWithHeader(headerId = R.string.email, value = emailValue) {
                    if(it.text.length <= 16)emailValue = it
                }
                TextFieldWithHeader(headerId = R.string.password, value = passwordValue) {
                    if(it.text.length <= 16)passwordValue = it
                }
                Spacer(
                    modifier = Modifier.height(40.dp)
                )
                OutlinedButton(
                    onClick = {
                        if(
                            nameValue.text.isNotEmpty()
                            && emailValue.text.isNotEmpty()
                            && passwordValue.text.isNotEmpty()
                        ) {
                            databaseViewModel.insert(
                                User(
                                    id = 0,
                                    name = nameValue.text,
                                    email = emailValue.text,
                                    password = passwordValue.text
                                )
                            )
                            context.shortToast("user added")
                            navigateForward.invoke(nameValue.text)
                        } else {
                            context.shortToast(context.getString(R.string.error_message))
                        }
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.end_register),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
@Composable
fun TextFieldWithHeader(headerId: Int,value: TextFieldValue,onValueChange: (TextFieldValue) -> Unit) {
    TextWithSpacer(headerId = headerId)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true
    )
}

@Composable
fun TextWithSpacer(headerId: Int) {
    Spacer(modifier = Modifier.height(40.dp))

    Text(
        text = stringResource(id = headerId),
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodySmall
    )
}