@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.tuorg.notasmultimedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tuorg.notasmultimedia.model.db.ItemType


@Composable
fun DetailScreen(
    nav: NavController,
    id: String,
    vm: NoteDetailViewModel = viewModel(factory = NoteDetailViewModel.provideFactory(id))
) {
    val noteWithR by vm.note.collectAsState()
    var showConfirm by remember { mutableStateOf(false) }

    val n = noteWithR?.note

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle") },
                actions = {
                    TextButton(onClick = { nav.navigate("edit/$id") }) { Text("Editar") }
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { showConfirm = true }) { Text("Eliminar") }
                }
            )
        }
    ) { pads ->
        Column(
            modifier = Modifier.padding(pads).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (n == null) {
                Text("Cargando…")
            } else {
                Text("ID: ${n.id}")
                Text("Título: ${n.title}")
                Text("Descripción: ${n.description.ifBlank { "(vacía)" }}")
                Text(
                    text = if (n.type == ItemType.TASK)
                        "Fecha/Hora objetivo: ${n.dueAt ?: "(sin definir)"}"
                    else "Tipo: Nota"
                )
                Text("Completada: ${if (n.completed) "Sí" else "No"}")
                // … aquí lo que ya tenías de recordatorios/adjuntos
            }
        }
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Eliminar") },
            text = { Text("¿Seguro que deseas eliminar esta ${if (n?.type == ItemType.TASK) "tarea" else "nota"}?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirm = false
                    vm.delete { nav.popBackStack() }
                }) { Text("Sí, eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("Cancelar") }
            }
        )
    }
}
