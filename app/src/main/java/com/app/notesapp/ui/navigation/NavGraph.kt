package com.app.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.notesapp.data.viewmodel.NoteViewModel
import com.app.notesapp.ui.screen.AddEditNoteScreen
import com.app.notesapp.ui.screen.NoteListScreen

@Composable
fun NavGraph(navController: NavHostController, viewModel: NoteViewModel) {

    NavHost(
        navController = navController,
        startDestination = "list",


    ) {

        composable("list") {
            NoteListScreen(navController, viewModel)
        }
        composable(
            "add/{id}/{title}/{desc}"
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""

            AddEditNoteScreen(navController, viewModel, id, title, desc)
        }
//        composable("add") {
//          AddEditNoteScreen(navController, viewModel)
//        }
    }
}