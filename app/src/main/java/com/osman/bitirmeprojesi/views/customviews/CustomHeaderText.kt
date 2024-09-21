package com.osman.bitirmeprojesi.views.customviews

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.ui.theme.Roboto

@Composable
fun CustomHeaderText(modifier: Modifier = Modifier, content :String) {

    Text(text = content,color = colorResource(id = R.color.textColor),
        style = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 16.sp
        )
    )

}