package com.osman.bitirmeprojesi.views.customviews

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.osman.bitirmeprojesi.R

@Composable
fun Chip(content :String, onClick: () -> Unit) {

    OutlinedButton(onClick = onClick,
            modifier = Modifier.background(colorResource(id = R.color.buttonBackground)),
        border = BorderStroke(0.dp, Color.Transparent),) {

        Text(text = content, color = colorResource(R.color.black))

    }

}