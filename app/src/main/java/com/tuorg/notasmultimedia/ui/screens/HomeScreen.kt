package com.tuorg.notasmultimedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuorg.notasmultimedia.model.db.ItemType
import com.tuorg.notasmultimedia.model.db.NoteWithRelations
import com.tuorg.notasmultimedia.model.NoteItem
import com.tuorg.notasmultimedia.nav.Routes
import java.time.LocalDateTime
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import com.tuorg.notasmultimedia.R
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController, vm: HomeViewModel = viewModel()) {
    val items by vm.items.collectAsState()
    val tab by vm.tab.collectAsState()
    val query by vm.query.collectAsState()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_home)) },
                    selected = true, onClick = {}
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_new)) },
                    selected = false, onClick = { nav.navigate(Routes.EDIT) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_settings)) },
                    selected = false, onClick = { nav.navigate(Routes.SETTINGS) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.title_home)) },
                    actions = {
                        TextField(
                            value = query,
                            onValueChange = vm::setQuery,
                            placeholder = { Text(stringResource(R.string.search_placeholder)) },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            singleLine = true,
                            modifier = Modifier.width(220.dp)
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { nav.navigate(Routes.EDIT) }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        ) { pads ->
            Column(Modifier.padding(pads)) {
                TabRow(selectedTabIndex = tab) {
                    listOf(
                        stringResource(R.string.tab_all),
                        stringResource(R.string.tab_tasks),
                        stringResource(R.string.tab_notes)
                    ).forEachIndexed { i, t ->
                        Tab(selected = tab == i, onClick = { vm.setTab(i) }, text = { Text(t) })
                    }
                }
                LazyColumn(Modifier.fillMaxSize().padding(8.dp)) {
                    items(items) { it ->
                        ElevatedCard(
                            onClick = { nav.navigate("detail/${it.note.id}") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(if (it.note.type == ItemType.TASK) "🗓 ${it.note.title}" else it.note.title,
                                    style = MaterialTheme.typography.titleMedium)
                                Text(it.note.description.take(80), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: NoteWithRelations, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = if (note.note.type == ItemType.TASK)
                    "Tarea: ${note.note.title}" else note.note.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = note.note.description.take(60),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = if (note.note.type == ItemType.TASK)
                    "Vence: ${note.note.dueAt}"
                else
                    "Creada: ${note.note.createdAt}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
