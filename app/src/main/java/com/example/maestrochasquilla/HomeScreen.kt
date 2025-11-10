package com.example.maestrochasquilla

import WorkerRequestViewModel
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class MainScreen() {
    Start,
    SignUp,
    ClientRequests,
    WorkerRequests
}

@Composable
fun MaestroChasquillaApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MainScreen.valueOf(
        backStackEntry?.destination?.route ?: MainScreen.Start.name
    )
    NavHost(
        navController=navController,
        startDestination = MainScreen.Start.name
    ) {
        composable(MainScreen.Start.name){
            StartDisplayer(navController)
        }
        composable(MainScreen.WorkerRequests.name){
            WorkerRequestsDisplayer(navController)
        }
        composable(MainScreen.SignUp.name){
            SignUpDisplayer(navController)
        }
        composable(MainScreen.ClientRequests.name){
            ClientRequestDisplayer(navController)
        }
    }
}
@Composable
fun StartDisplayer(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido a maestro chasquilla!",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "¿Aún no tienes cuenta?",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { navController.navigate(MainScreen.SignUp.name)}) {
            Text("Regístrate")
        }
        Text(
            text = "¿Buscas un trabajo?",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { navController.navigate(MainScreen.ClientRequests.name)}) {
            Text("Encuentra un cliente")
        }
        Text(
            text = "¿Buscas un especialista?",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { navController.navigate(MainScreen.WorkerRequests.name)}){
            Text("Encuentra uno aquí")
        }
    }
}

@Composable
fun WorkerRequestsDisplayer(navController: NavController) {
    val viewModel: WorkerRequestViewModel = viewModel()
    val works = viewModel.workerRequest.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = works,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.fetchWorkerRequests() }) {
            Text("Refrescar solicitudes")
        }
    }
}

@Composable
fun SignUpDisplayer(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HOLA ESTO ES PARA REGISTRARSE",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ClientRequestDisplayer(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HOLA ESTO ES PARA ENTCONTRAR CLIENTES",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}