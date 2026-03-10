package com.app.notesapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.notesapp.data.viewmodel.NoteViewModel
import com.app.notesapp.ui.theme.LightGrey
import com.app.notesapp.ui.theme.darkgrey
import com.app.notesapp.ui.utils.NotesTopBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {

    val notes by viewModel.notes.collectAsState()

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showFilter by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("NEWEST") }
    val filteredNotes = notes.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.description.contains(searchText, ignoreCase = true)
    }

    fun formatDate(time: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date(time))
    }

    Scaffold(
        topBar = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search notes...") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            searchText = ""
                            isSearching = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                )
            } else {
                NotesTopBar(
                    onSearchClick = { isSearching = true },
                    onFilterClick = { showFilter = !showFilter }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = LightGrey,
                onClick = { navController.navigate("add/0//") }
            ) {
                Text("+", fontSize = 30.sp)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {

            // Filter dropdown
            if (showFilter) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Newest
                    Text(
                        "Newest",
                        color = if (selectedFilter == "NEWEST") Color.Red else Color.Black,
                        modifier = Modifier
                            .clickable {
                                viewModel.sortNewest()
                                selectedFilter = "NEWEST"
                            }
                            .padding(10.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Oldest
                    Text(
                        "Oldest",
                        color = if (selectedFilter == "OLDEST") Color.Red else Color.Black,
                        modifier = Modifier
                            .clickable {
                                viewModel.sortOldest()
                                selectedFilter = "OLDEST"
                            }
                            .padding(10.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Reset (same as newest)
                    Text(
                        "Reset",
                        color = Color.Black, // Reset is never highlighted
                        modifier = Modifier
                            .clickable {
                                viewModel.sortNewest()
                                selectedFilter = "NEWEST"
                                showFilter = false
                            }
                            .padding(10.dp)
                    )
                }
            }

            if (filteredNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Notes Available",
                        fontSize = 20.sp
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        bottom = 115.dp
                    )
                ) {
                    items(filteredNotes) { note ->

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 9.dp, vertical = 5.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {

                                Text(text = note.title, fontSize = 18.sp)

                                Spacer(modifier = Modifier.height(10.dp))

                                Divider(
                                    color = darkgrey,
                                    thickness = 1.dp,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(note.description)

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = if (note.createdAt != 0L) formatDate(note.createdAt) else "---",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        tint = Color.Red,
                                        contentDescription = "Delete",
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { viewModel.deleteNote(note) }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        tint = Color.Black,
                                        contentDescription = "Edit",
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable {
                                                navController.navigate(
                                                    "add/${note.id}/${note.title}/${note.description}"
                                                )
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}