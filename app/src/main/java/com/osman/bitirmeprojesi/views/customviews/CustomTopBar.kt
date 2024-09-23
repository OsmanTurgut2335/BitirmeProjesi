package com.osman.bitirmeprojesi.views.customviews


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.ui.theme.Roboto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,

    navController: NavController,
    sortExpanded: Boolean,
    onSortExpandedChange: (Boolean) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    allFoodList : List<Food>,
    onSortSelected: (SortCriteria) -> Unit // Callback for sort selection

) {
    TopAppBar(
        title = { TopBarText(title = title) },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text for sorting
                CustomHeaderText(content = "Ürünleri Sırala")

                // IconButton for sorting
                IconButton(onClick = { onSortExpandedChange(true) }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_compare_arrows_24), contentDescription = "Sırala")
                }

                // Dropdown menu for sorting options
                DropdownMenu(
                    expanded = sortExpanded,
                    onDismissRequest = { onSortExpandedChange(false) }
                ) {
                    DropdownMenuItem(
                      //  text = { Text("İsime Göre Sırala") },
                        text = { CustomHeaderText(content = "İsime Göre Sırala") },
                        onClick = {
                            onSortExpandedChange(false)
                            onSortSelected(SortCriteria.Name) // Trigger sorting by name
                        }
                    )
                    DropdownMenuItem(

                        text = { CustomHeaderText(content = "Artan Fiyata Göre Sırala") },
                        onClick = {
                            onSortExpandedChange(false)
                            onSortSelected(SortCriteria.PriceAscending) // Trigger sorting by price ascending
                        }
                    )
                    DropdownMenuItem(
                        text = { CustomHeaderText(content = "Azalan Fiyata Göre Sırala") },
                        onClick = {
                            onSortExpandedChange(false)
                            onSortSelected(SortCriteria.PriceDescending) // Trigger sorting by price descending
                        }
                    )
                }
            }

            // The three-dot menu (More options)
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
            }

            // Dropdown menu for the three-dot menu
            Box {
                Spacer(modifier = Modifier.padding(top = 40.dp,))
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onExpandedChange(false) },

                    ) {

                    DropdownMenuItem(
                       // modifier = Modifier.padding(end = 10.dp),
                        text = { CustomHeaderText(content = "Çıkış Yap") },

                        onClick = {
                            onExpandedChange(false)
                            FirebaseAuth.getInstance().signOut()

                            navController.navigate("loginscreen") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )

                }
            }

        }
    )
}
