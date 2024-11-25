package danif.android.app_registro_rb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import danif.android.app_registro_rb.modelo.RegistroClass

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                AppRegistroServicios()
            }
        }
    }


@Preview(showBackground = false, showSystemUi = true)
@Composable
fun AppRegistroServicios(
    navController: NavHostController = rememberNavController(),
) {
    val listaRegistros = remember { mutableStateListOf<RegistroClass>() }

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            PageInicioUI(
                onButtonSettingsClicked = {
                    navController.navigate("agregarRegistros")
                },
                listaRegistros
            )
        }
        composable("agregarRegistros") {
            AgregarRegistrosPageUI(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                listaRegistros
            )
        }
    }
}


//Componente header
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerNav(
    title:String = "",
    showSettingsButton:Boolean = true,
    onButtonSettingsClicked:() -> Unit = {},
    showBackButton:Boolean = false,
    onBackButtonClicked:() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if(showBackButton) {
                IconButton(onClick = {
                    onBackButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
            }
        },
        actions = {
            if( showSettingsButton ) {
                IconButton(onClick = {
                        onButtonSettingsClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Configuración"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}



// Pagina de inicio y muestra de registros
@Preview(showSystemUi = true)
@Composable
fun PageInicioUI(
    onButtonSettingsClicked:() -> Unit = {},
    registros: MutableList<RegistroClass>
) {
    Scaffold(
        topBar = {
            BannerNav(
                title = "Listado Tareas",
                onButtonSettingsClicked = onButtonSettingsClicked
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = "Registros"
                )

                LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 16.dp))
                {
                    items(registros.size) { index: Int ->
                        RegistroItem(
                            registro = registros[index],
                            onDelete = { registros.removeAt(index) }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun RegistroItem(
    registro: RegistroClass,
    onDelete: () -> Unit
) {
    Column {
        Text(text = "Nombre Registro:  ${registro.registro}")
        Text(text = "Fecha:  ${registro.fecha}")
        Text(text = "Tipo Registro:  ${registro.tipoRegistro}")
    }
    IconButton(onClick = onDelete) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "EliminarRegistro",
            tint = Color.Red,
        )
    }
}



//Pagina de registros
//@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun AgregarRegistrosPageUI(
    onBackButtonClicked:() -> Unit = {},
    registros: MutableList<RegistroClass>
) {
    var registroMedidorValue by remember { mutableStateOf("") }
    var registroFechaValue by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Agua") }

    Scaffold(
        topBar = {
            BannerNav(
                title = "Agregar registros",
                showSettingsButton = false,
                showBackButton = true,
                onBackButtonClicked = onBackButtonClicked
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = paddingValues.calculateTopPadding())
            ){

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = registroMedidorValue,
                    placeholder = { Text(text = "ingrese registro") },
                    onValueChange = { registroMedidorValue = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = registroFechaValue,
                    placeholder = { Text(text = "ingrese registro") },
                    onValueChange = { registroFechaValue = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tipo de medidior:",
                )

                Spacer(modifier = Modifier.height(16.dp))

                //Opcion Agua
                Row {
                    RadioButton(
                        selected = selectedOption == "Agua",
                        onClick = { selectedOption = "Agua" }
                    )
                    Text(
                        text = "Agua"
                    )
                }
                //Opcion Luz
                Row {
                    RadioButton(
                        selected = selectedOption == "Luz",
                        onClick = { selectedOption = "Luz" }
                    )
                    Text(
                        text = "Luz"
                    )
                }
                //Opcion Gas
                Row {
                    RadioButton(
                        selected = selectedOption == "Gas",
                        onClick = { selectedOption = "Gas" }
                    )
                    Text(
                        text = "Gas"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val registro = RegistroClass(
                            registro = registroMedidorValue,
                            fecha = registroFechaValue,
                            tipoRegistro = selectedOption
                        )

                        registros.add(registro)
                    }
                ) {
                Text("Agregar Registro")
                }
            }
        }
    )
}