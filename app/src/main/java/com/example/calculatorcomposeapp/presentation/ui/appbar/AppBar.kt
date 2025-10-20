package com.example.calculatorcomposeapp.presentation.ui.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.calculatorcomposeapp.R

@Composable
fun AppBar() {
    @OptIn(ExperimentalMaterial3Api::class)
    TopAppBar(
        title = { Text("") },
        actions = {
            IconButton(
                modifier = Modifier.padding(top = 40.dp),
                onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = "Menu"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.tertiary)
    )
}