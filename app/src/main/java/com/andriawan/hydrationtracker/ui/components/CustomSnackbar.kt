package com.andriawan.hydrationtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.theme.SecondaryColor

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        snackbar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryColor.copy(alpha = 0.6F)),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 18.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_water),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = it.message,
                        modifier = Modifier
                            .weight(2F)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )

                    it.actionLabel?.let { label ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .clickable {
                                    it.performAction()
                                }
                                .weight(1F),
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    )
}