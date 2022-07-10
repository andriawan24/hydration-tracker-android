package com.andriawan.hydrationtracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DialogEditValue(
    title: String,
    value: String,
    onSubmit: (newValue: String) -> Unit,
    onDismiss: () -> Unit
) {
    var newValue by remember { mutableStateOf(value) }

    AlertDialog(
        title = {
            Text(text = title, style = MaterialTheme.typography.h3)
        },
        onDismissRequest = {
            onDismiss()
        },
        text = {
            Column {
                OutlinedTextField(
                    modifier = Modifier.padding(top = 12.dp),
                    value = newValue,
                    onValueChange = {
                        newValue = it
                    },
                    placeholder = {
                        Text(text = "Please enter a new value")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(newValue)
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text(text = "Cancel")
            }
        }
    )
}