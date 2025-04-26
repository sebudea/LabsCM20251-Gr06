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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
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


/* PARA CONTACTO */
@Composable
fun PhoneNumber() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    val numericRegex = Regex("[^0-9]")
    TextField(
        value = phoneNumber,
        onValueChange = {
            // Remove non-numeric characters.
            val stripped = numericRegex.replace(it, "")
            phoneNumber = if (stripped.length >= 10) {
                stripped.substring(0..9)
            } else {
                stripped
            }
        },
        label = { Text("Enter Phone Number") },
        visualTransformation = NanpVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

class NanpVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

        var out = if (trimmed.isNotEmpty()) "(" else ""

        for (i in trimmed.indices) {
            if (i == 3) out += ") "
            if (i == 6) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Add 1 for opening parenthesis.
                in 1..3 -> offset + 1
                // Add 3 for both parentheses and a space.
                in 4..6 -> offset + 3
                // Add 4 for both parentheses, space, and hyphen.
                else -> offset + 4
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Subtract 1 for opening parenthesis.
                in 1..5 -> offset - 1
                // Subtract 3 for both parentheses and a space.
                in 6..10 -> offset - 3
                // Subtract 4 for both parentheses, space, and hyphen.
                else -> offset - 4
            }
    }
}