package com.tuorg.notasmultimedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nav: NavController, id: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                actions = {
                    TextButton(onClick = { /* TODO editar */ }) { Text("Editar") }
                    TextButton(onClick = { /* TODO eliminar */ }) { Text("Eliminar") }
                }
            )
        }
    ) { pads ->
        Column(Modifier.padding(pads).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("ID: $id")
            Text("Descripción: (placeholder)")
            Text("Fecha/Hora objetivo: (si es Tarea)")
            Text("Recordatorios: (lista)")

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { /* TODO marcar cumplida */ }) { Text("Marcar cumplida") }
                OutlinedButton(onClick = { /* TODO posponer */ }) { Text("Posponer") }
            }

            Text("Adjuntos (miniaturas simuladas)")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = { }, label = { Text(" img") })
                AssistChip(onClick = { }, label = { Text(" vid") })
                AssistChip(onClick = { }, label = { Text(" aud") })
            }
        }
    }
}
