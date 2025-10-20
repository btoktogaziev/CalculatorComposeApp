package com.example.calculatorcomposeapp.presentation.ui.appbar

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorcomposeapp.R

@Composable
fun AppBar(onThemeSelected: (String) -> Unit) {
    var opened by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    @OptIn(ExperimentalMaterial3Api::class)
    TopAppBar(
        title = { Text("") },
        actions = {
            IconButton(
                modifier = Modifier.padding(top = 40.dp),
                onClick = {
                    opened = true
                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = opened,
                onDismissRequest = { opened = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier.padding(end = 12.dp),
                            text = "Выбрать тему"
                        )
                    },
                    onClick = {
                        showDialog = true
                        opened = false
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.tertiary)
    )
    if (showDialog) {
        AlertDialogForTheme(
            onThemeSelected = onThemeSelected,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun AlertDialogForTheme(onThemeSelected: (String) -> Unit, onDismiss: () -> Unit) {
    var selectedItem by remember { mutableStateOf("Light") }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Theme"
            )
        },
        title = {
            Text(text = "Выбор темы")
        },
        text = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedItem == "Light",
                        onClick = { selectedItem = "Light" }
                    )
                    Text("Светлая тема", modifier = Modifier.padding(start = 8.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedItem == "Dark",
                        onClick = { selectedItem = "Dark" }
                    )
                    Text("Тёмная тема", modifier = Modifier.padding(start = 8.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedItem == "System",
                        onClick = { selectedItem = "System" }
                    )
                    Text("Системная тема", modifier = Modifier.padding(start = 8.dp))
                }
            }
        },
        onDismissRequest = {
            onDismiss
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onThemeSelected(selectedItem)
                    onDismiss
                }
            ) {
                Text("Ок")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss
                }
            ) {
                Text("Отмена")
            }
        })
}