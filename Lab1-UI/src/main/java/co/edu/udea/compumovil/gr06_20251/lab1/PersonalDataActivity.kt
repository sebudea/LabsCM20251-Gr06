package co.edu.udea.compumovil.gr06_20251.lab1

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
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

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Información Personal", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                CampoConIcono(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = "Nombres",
                    icono = Icons.Default.Person,
                    contentDescriptionIcono = "Icono de Nombres",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    isError = nombresError,
                    errorMessage = if (nombresError) "Este campo es obligatorio" else "",
                    modifier = Modifier.weight(1f) // Ocupa la mitad del espacio disponible
                )
                CampoConIcono(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = "Apellidos",
                    icono = Icons.Default.Person,
                    contentDescriptionIcono = "Icono de Apellidos",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    isError = apellidosError,
                    errorMessage = if (apellidosError) "Este campo es obligatorio" else "",
                    modifier = Modifier.weight(1f) // Ocupa la otra mitad del espacio disponible
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* Campo Sexo */

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Icono de Sexo",
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
                modifier = Modifier.fillMaxWidth(), // La fila exterior ocupa todo el ancho para permitir el centrado
                horizontalArrangement = Arrangement.Center // Centra horizontalmente su contenido
            ) {
                Row( // La fila interior contiene el icono y el TextField
                    verticalAlignment = Alignment.CenterVertically,
                    // No usamos Modifier.fillMaxWidth() aquí para que se ajuste al contenido
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Icono de Fecha",
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    TextField(
                        value = fechaNacimiento,
                        onValueChange = { /* No permitir edición directa */ },
                        readOnly = true,
                        label = { Text("Fecha de nacimiento") },
                        trailingIcon = { Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                            fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
                        } }) {
                            Text("Cambiar")
                        } },
                        // Remove Modifier.weight(1f) para que el TextField no ocupe todo el ancho de la fila interior
                        isError = fechaNacimientoError,
                        supportingText = { if (fechaNacimientoError) {
                            Text("Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                        } }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* Fila para el campo Grado y el botón Siguiente */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Coloca elementos a los extremos
            ) {
                /* Campo Grado */
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f) // Ocupa el espacio disponible a la izquierda
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Icono de Escolaridad",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    val opcionesGrado = listOf("Primaria", "Secundaria", "Universitario", "Otro")

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

                /* Boton Siguiente */
                Button(onClick = {
                    intentoValidacion = true
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

        } else {
            /* Campo Nombres */

            CampoConIcono(
                value = nombres,
                onValueChange = { nombres = it },
                label = "Nombres",
                icono = Icons.Default.Person,
                contentDescriptionIcono = "Icono de Nombres",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                isError = nombresError, // Nuevo parámetro para indicar error
                errorMessage = if (nombresError) "Este campo es obligatorio" else "" // Mensaje de error opcional
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* Campo Apellidos */

            CampoConIcono(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = "Apellidos",
                icono = Icons.Default.Person,
                contentDescriptionIcono = "Icono de Apellidos",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                isError = apellidosError, // Nuevo parámetro para indicar error
                errorMessage = if (apellidosError) "Este campo es obligatorio" else "" // Mensaje de error opcional
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* Campo Sexo */

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Icono de Sexo",
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
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Icono de Fecha",
                    modifier = Modifier.padding(end = 8.dp)
                )

                TextField(
                    value = fechaNacimiento,
                    onValueChange = { /* No permitir edición directa */ },
                    readOnly = true,
                    label = { Text("Fecha de nacimiento") },
                    trailingIcon = { Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                        fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
                    } }) {
                        Text("Cambiar")
                    } },
                    modifier = Modifier.weight(1f),
                    isError = fechaNacimientoError, // Nuevo parámetro para indicar error
                    supportingText = { if (fechaNacimientoError) {
                        Text("Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                    } }
                )

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
                    imageVector = Icons.Default.Create,
                    contentDescription = "Icono de Escolaridad",
                    modifier = Modifier.padding(end = 8.dp)
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
                intentoValidacion = true // Marcamos que se intentó la validación
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier // Añade un parámetro modifier con un valor por defecto
) {
    Row(
        modifier = modifier.fillMaxWidth() , // Usamos el modifier pasado desde el padre y luego fillMaxWidth
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = contentDescriptionIcono,
            modifier = Modifier.padding(end = 8.dp),
            tint = if (isError) MaterialTheme.colorScheme.error else LocalContentColor.current
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            keyboardOptions = keyboardOptions,
            isError = isError,
            supportingText = {
                if (isError && errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}
