package co.edu.udea.compumovil.gr06_20251.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactDataScreen() // Llamamos al Composable que creamos
                }
            }
        }
    }
}


@Composable
fun ContactDataScreen() {
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Información de Contacto", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { newTelefono ->
                telefono = newTelefono.filter { it.isDigit() }
            },
            label = { Text("*Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("*Email") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = pais,
            onValueChange = { pais = it },
            label = { Text("*País") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ciudad,
            onValueChange = { ciudad = it },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
            // Aquí podrías integrar un Autocomplete para ciudades (investigar API)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Aquí puedes implementar la lógica para guardar los datos de contacto
            println("Teléfono: $telefono")
            println("Dirección: $direccion")
            println("Email: $email")
            println("País: $pais")
            println("Ciudad: $ciudad")
        }) {
            Text("Siguiente") // El teclado debe mostrar "Siguiente" en lugar de "Enter"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDataScreenPreview() {
    ContactDataScreen()
}