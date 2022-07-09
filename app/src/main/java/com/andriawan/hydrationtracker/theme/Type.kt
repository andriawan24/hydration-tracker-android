package com.andriawan.hydrationtracker.theme

import androidx.compose.material.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.andriawan.hydrationtracker.R

val QuickSand = FontFamily(
    Font(R.font.quicksand),
    Font(R.font.quicksand_bold, weight = FontWeight.Bold),
    Font(R.font.quicksand_semibold, weight = FontWeight.SemiBold),
    Font(R.font.quicksand_medium, weight = FontWeight.Medium),
    Font(R.font.quicksand_light, weight = FontWeight.Light)
)

val Typography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = QuickSand,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = QuickSand,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = QuickSand,
        fontSize = 16.sp
    )
)