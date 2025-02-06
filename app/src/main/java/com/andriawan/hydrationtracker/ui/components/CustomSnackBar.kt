package com.andriawan.hydrationtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
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
fun CustomSnackBar(state: SnackbarHostState) {
    SnackbarHost(
        hostState = state,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        snackbar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryColor.copy(alpha = 0.6F)),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 18.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_water),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        modifier = Modifier
                            .weight(2F)
                            .fillMaxWidth(),
                        text = it.message,
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Medium)
                    )

                    it.actionLabel?.let { label ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .clickable { it.performAction() }
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
