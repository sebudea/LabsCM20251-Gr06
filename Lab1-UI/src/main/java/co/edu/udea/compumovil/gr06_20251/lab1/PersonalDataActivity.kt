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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


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
    var fechaNacimiento by remember { mutableStateOf("") }
    var sexoSeleccionado by remember { mutableStateOf<String?>(null) }
    var gradoEscolaridadSeleccionado by remember { mutableStateOf("") }
    val context = LocalContext.current
    var intentoValidacion by remember { mutableStateOf(false) }

    val nombresError = intentoValidacion && nombres.isBlank()
    val apellidosError = intentoValidacion && apellidos.isBlank()
    val fechaNacimientoError = intentoValidacion && fechaNacimiento.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Información Personal", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        /* Campo Nombres */

        CampoConIcono(
            value = nombres,
            onValueChange = { nombres = it },
            label = "*Nombres",
            icono = Icons.Default.Person,
            contentDescriptionIcono = "Icono de Nombres",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        /* Campo Apellidos */

        CampoConIcono(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = "*Apellidos",
            icono = Icons.Default.Person,
            contentDescriptionIcono = "Icono de Apellidos",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Campo Sexo */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Icono de Apellidos",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Sexo:", style = MaterialTheme.typography.bodyMedium)

            RadioButton(
                selected = sexoSeleccionado == "Hombre",
                onClick = { sexoSeleccionado = "Hombre" }
            )
            Text("Hombre")

            Spacer(modifier = Modifier.width(20.dp))

            RadioButton(
                selected = sexoSeleccionado == "Mujer",
                onClick = { sexoSeleccionado = "Mujer" }
            )
            Text("Mujer")

        }

        Spacer(modifier = Modifier.height(16.dp))

        /* Campo Fecha Nacimiento */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange, // Icono de persona predeterminado
                contentDescription = "Icono de Fecha",
                modifier = Modifier.padding(end = 8.dp) // Añade un espacio entre el icono y el texto
            )
            Text(text = "Fecha de Nacimiento:", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
            } }) {
                Text("Cambiar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* Campo Grado */

        var expanded by remember { mutableStateOf(false) }
        val opcionesGrado = listOf("Primaria", "Secundaria", "Universitario", "Otro")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                imageVector = Icons.Default.Info, // Icono de persona predeterminado
                contentDescription = "Icono de Escolaridad",
                modifier = Modifier.padding(end = 8.dp) // Añade un espacio entre el icono y el texto
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = gradoEscolaridadSeleccionado,
                    onValueChange = { /* No permitir edición directa */ },
                    readOnly = true,
                    label = { Text("Grado de escolaridad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
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

        }

        Spacer(modifier = Modifier.height(32.dp))

        /* Boton Siguiente */

        Button(onClick = {
            if (nombres.isNotBlank() && apellidos.isNotBlank() && fechaNacimiento.isNotBlank()) {
                android.util.Log.d("PersonalData", "Información personal:")
                android.util.Log.d("PersonalData", "${nombres} ${apellidos}")
                sexoSeleccionado?.let { android.util.Log.d("PersonalData", it) }
                android.util.Log.d("PersonalData", "Nació el $fechaNacimiento")
                if (gradoEscolaridadSeleccionado.isNotBlank()) {
                    android.util.Log.d("PersonalData", gradoEscolaridadSeleccionado)
                }
                // Aquí iría la lógica para navegar a la siguiente actividad soi
            } else {
                android.util.Log.e("PersonalData", "Por favor, completa los campos obligatorios (*).")
                // Opcionalmente, podrías mostrar un mensaje al usuario en la UI
            }
        }) {
            Text("Siguiente")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoConIcono(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icono: ImageVector,
    contentDescriptionIcono: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Row(
        modifier = Modifier.fillMaxWidth() ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = contentDescriptionIcono,
            modifier = Modifier.padding(end = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            keyboardOptions = keyboardOptions
        )
    }
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