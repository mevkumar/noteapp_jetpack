package com.app.notesapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.app.notesapp.data.viewmodel.NoteViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {

    val notes by viewModel.notes.collectAsState()

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("add/0//")
                }
            ) {
                Text("+")
            }
        }

    ) { padding ->

        if (notes.isEmpty()) {

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

            LazyColumn(contentPadding = padding) {

                items(notes) { note ->

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {

                            Text(
                                text = "Title Name: "+note.title,
                                fontSize = 20.sp
                            )

                            Text("Description: "+ note.description)

                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    tint = Color.Red,
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            viewModel.deleteNote(note)
                                        }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    tint = Color.Black,
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            navController.navigate("add/${note.id}/${note.title}/${note.description}")
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