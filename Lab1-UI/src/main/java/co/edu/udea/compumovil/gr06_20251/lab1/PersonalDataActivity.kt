package co.edu.udea.compumovil.gr06_20251.lab1

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.edu.udea.compumovil.gr06_20251.lab1.PersonalDataScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface


class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PersonalDataScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen() {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var sexoSeleccionado by remember { mutableStateOf<String?>(null) }
    var fechaNacimiento by remember { mutableStateOf("") }
    var gradoEscolaridadSeleccionado by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Información Personal", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = { Text("*Nombres") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("*Apellidos") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "*Sexo", style = MaterialTheme.typography.bodyMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sexoSeleccionado == "Hombre",
                    onClick = { sexoSeleccionado = "Hombre" }
                )
                Text("Hombre")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sexoSeleccionado == "Mujer",
                    onClick = { sexoSeleccionado = "Mujer" }
                )
                Text("Mujer")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sexoSeleccionado == "Otro",
                    onClick = { sexoSeleccionado = "Otro" }
                )
                Text("Otro")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { /* No permitir edición directa */ },
            label = { Text("*Fecha de nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                    fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
                } }) {
                    Text("Cambiar")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        val opcionesGrado = listOf("Primaria", "Secundaria", "Universitario", "Otro")

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = gradoEscolaridadSeleccionado,
                onValueChange = { /* No permitir edición directa */ },
                readOnly = true,
                label = { Text("*Grado de escolaridad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesGrado.forEach { seleccion ->
                    DropdownMenuItem(
                        text = { Text(text = seleccion) },
                        onClick = {
                            gradoEscolaridadSeleccionado = seleccion
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Aquí puedes implementar la lógica para guardar los datos
            println("Nombres: $nombres")
            println("Apellidos: $apellidos")
            println("Sexo: $sexoSeleccionado")
            println("Fecha de Nacimiento: $fechaNacimiento")
            println("Grado de Escolaridad: $gradoEscolaridadSeleccionado")
        }) {
            Text("Guardar")
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateSelected(year, month, dayOfMonth)
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

@Preview(showBackground = true)
@Composable
fun PersonalDataScreenPreview() {
    PersonalDataScreen()
}