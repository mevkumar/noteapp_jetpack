package com.app.notesapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.notesapp.data.local.NoteEntity
import com.app.notesapp.data.viewmodel.NoteViewModel

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    id: Int = 0,
    oldTitle: String = "",
    oldDesc: String = ""
) {

    var title by remember { mutableStateOf(oldTitle) }
    var desc by remember { mutableStateOf(oldDesc) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Spacer(modifier = Modifier.height(50.dp))
        BorderedTextFieldExample(
            value = title,
            onValueChange = { title = it },
            placeholder = "Enter Title"
        )

        Spacer(modifier = Modifier.height(30.dp))

        BorderedTextFieldExample(
            value = desc,
            onValueChange = { desc = it },
            placeholder = "Enter Description"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (title.isBlank()) {
                    Toast.makeText(context, "Please enter title", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (desc.isBlank()) {
                    Toast.makeText(context, "Please enter description", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (id == 0) {
                    viewModel.insertNote(title, desc)
                } else {
                    viewModel.updateNote(
                        NoteEntity(
                            id = id,
                            title = title,
                            description = desc
                        )
                    )
                }

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//
//        }
    }
}

@Composable
fun BorderedTextFieldExample(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    OutlinedTextField(
        shape = RoundedCornerShape(27.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF5722),
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color(0xFFFF5722)
        ),
        singleLine = true
    )
}