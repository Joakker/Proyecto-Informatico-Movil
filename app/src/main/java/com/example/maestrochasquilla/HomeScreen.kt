package com.example.maestrochasquilla

import WorkerRequestViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    Login,
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
        composable(MainScreen.Login.name){
            LoginDisplayer(navController)
        }
    }
}
@Composable
fun StartDisplayer(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Log in",
                modifier = Modifier.clickable {
                    navController.navigate("Login")
                }
            )
            Text(
                text = "Sign up",
                modifier = Modifier.clickable {
                    navController.navigate("SignUp")
                }
            )
        }
    }
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
fun LoginDisplayer(
    navController: NavController,
    viewModel: LoginViewModel = LoginViewModel()
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            viewModel.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            ) {
                navController.navigate("Start")
            }
        }) {
            Text("Iniciar sesión")
        }

    }
}
@Composable
fun SignUpDisplayer(
    navController: NavController,
    viewModel: SignupViewModel = viewModel()
) {
    var fname by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = fname,
            onValueChange = { fname = it },
            label = { Text("First name") }
        )

        TextField(
            value = lname,
            onValueChange = { lname = it },
            label = { Text("Last name") }
        )

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") }
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.signup(
                SignupRequest(
                    fname = fname,
                    lname = lname,
                    phone = phone,
                    address = address,
                    email = email,
                    password = password
                )
            ) {
                navController.navigate("Start")
            }
        }) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(viewModel.message)
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