package com.andriawan.hydrationtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme

@Composable
fun DialogEditValue(
    title: String,
    value: String,
    onSubmit: (newValue: String) -> Unit,
    onDismiss: () -> Unit
) {
    var newValue by remember { mutableStateOf(value) }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = MaterialTheme.shapes.small) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.h3
                )

                OutlinedTextField(
                    value = newValue,
                    onValueChange = {
                        newValue = it
                    },
                    placeholder = {
                        Text(text = "Please enter a new value")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(onClick = { onSubmit(newValue) }) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogEditValuePreview() {
    HydrationTrackerTheme {
        DialogEditValue(title = "Hello", value = "100", onSubmit = {}, onDismiss = {})
    }
}
