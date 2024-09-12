package com.osman.bitirmeprojesi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osman.bitirmeprojesi.ui.theme.BitirmeProjesiTheme
import com.osman.bitirmeprojesi.viewmodels.DetailsScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.HomeScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.LoginScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val loginScreenViewModel :LoginScreenViewModel by viewModels()
    val homeScreenViewModel : HomeScreenViewModel by viewModels ()
    val detailsScreenViewModel : DetailsScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BitirmeProjesiTheme {
            Navigations(loginScreenViewModel,homeScreenViewModel,detailsScreenViewModel)
            }
        }
    }
}

