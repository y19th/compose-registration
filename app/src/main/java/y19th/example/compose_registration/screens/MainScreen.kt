package y19th.example.compose_registration.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
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

}


@Composable
fun MainScreen(navigateForward: () -> Unit) {

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.insert_data),
                    style = MaterialTheme.typography.bodyLarge
                )

                TextWithSpacer(headerId = R.string.name)

                OutlinedTextField(
                    value = nameValue,
                    onValueChange = {
                        if(it.text.length <= 16)nameValue = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),

                )

                TextWithSpacer(headerId = R.string.email)

                OutlinedTextField(
                    value = emailValue,
                    onValueChange = {
                        if(it.text.length <= 16)emailValue = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                TextWithSpacer(headerId = R.string.password)

                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = {
                        if(it.text.length <= 16)passwordValue = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                    )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
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
                            navigateForward.invoke()
                        } else {
                            context.shortToast(context.getString(R.string.error_message))
                        }
                    },
                    modifier = Modifier.padding(all = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.end_register))
                }
            }
        }
    }
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