package com.app.notesapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.app.notesapp.data.viewmodel.NoteViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

                                Button(onClick = {

                                    viewModel.deleteNote(note)

                                }) {
                                    Text("Delete")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(onClick = {

                                    navController.navigate("add/${note.id}/${note.title}/${note.description}")

                                }) {
                                    Text("Edit")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}