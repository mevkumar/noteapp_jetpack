package com.app.notesapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.app.notesapp.ui.theme.LightGrey
import com.app.notesapp.ui.theme.orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightGrey
        ),
        title = {
            Text(
                "QuickNotes",
                color = orange,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {

            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search,
                    tint = orange, // icon color
                    contentDescription = "Search",)
            }

            IconButton(onClick = onFilterClick) {
                Icon(Icons.Default.FilterList,
                    tint = orange, // icon color
                    contentDescription = "Filter")
            }
        }
    )
}