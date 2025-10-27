package com.tuorg.notasmultimedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.tuorg.notasmultimedia.nav.AppNavHost
import com.tuorg.notasmultimedia.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.tuorg.notasmultimedia.di.Graph.init(applicationContext)
        setContent { App() }
    }
}

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        AppNavHost(navController)
    }
}
