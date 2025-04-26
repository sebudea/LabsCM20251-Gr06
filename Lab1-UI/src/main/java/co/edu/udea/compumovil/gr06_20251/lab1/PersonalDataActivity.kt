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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource


class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "personal_data") {
                        composable("personal_data") { PersonalDataScreen(navController = navController) }
                        composable("contact_data") { ContactDataScreen() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(navController: NavController) {
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
        Text(stringResource(R.string.personal_info_title), style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                CampoConIcono(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = stringResource(R.string.name_label),
                    icono = Icons.Default.Person,
                    contentDescriptionIcono = stringResource(R.string.name_label),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    isError = nombresError,
                    errorMessage = if (nombresError) stringResource(R.string.required_field) else "",
                    modifier = Modifier.weight(1f)
                )
                CampoConIcono(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = stringResource(R.string.last_name_label),
                    icono = Icons.Default.Person,
                    contentDescriptionIcono = stringResource(R.string.last_name_label),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    isError = apellidosError,
                    errorMessage = if (apellidosError) stringResource(R.string.required_field) else "",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(R.string.sex_label),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = stringResource(R.string.sex_label), style = MaterialTheme.typography.bodyMedium)

                RadioButton(
                    selected = sexoSeleccionado == "Hombre",
                    onClick = { sexoSeleccionado = "Hombre" }
                )
                Text(stringResource(R.string.male_label))

                Spacer(modifier = Modifier.width(20.dp))

                RadioButton(
                    selected = sexoSeleccionado == "Mujer",
                    onClick = { sexoSeleccionado = "Mujer" }
                )
                Text(stringResource(R.string.female_label))

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.birth_date_label),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    TextField(
                        value = fechaNacimiento,
                        onValueChange = { /* No permitir edición directa */ },
                        readOnly = true,
                        label = { Text(stringResource(R.string.birth_date_label)) },
                        trailingIcon = { Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                            fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
                        } }) {
                            Text(stringResource(R.string.change_date_button))
                        } },
                        // Remove Modifier.weight(1f) para que el TextField no ocupe todo el ancho de la fila interior
                        isError = fechaNacimientoError,
                        supportingText = { if (fechaNacimientoError) {
                            Text(stringResource(R.string.required_field), color = MaterialTheme.colorScheme.error)
                        } }
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = stringResource(R.string.school_grade_label),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    val opcionesGrado = listOf("Primaria", "Secundaria", "Universitario", "Otro") // Estos también deberían ser recursos si necesitas traducirlos

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = gradoEscolaridadSeleccionado,
                            onValueChange = { /* No permitir edición directa */ },
                            readOnly = true,
                            label = { Text(stringResource(R.string.school_grade_label)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcionesGrado.forEach { seleccion -> // Si necesitas traducir las opciones, considera otra estrategia (List<StringResource>)
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

                Button(onClick = {
                    intentoValidacion = true
                    if (nombres.isNotBlank() && apellidos.isNotBlank() && fechaNacimiento.isNotBlank()) {
                        // ... lógica de validación
                        navController.navigate("contact_data")
                    } else {
                        // ... mostrar error usando stringResource(R.string.required_field)
                    }
                }) {
                    Text(stringResource(R.string.next_button))
                }
            }

        } else {
            CampoConIcono(
                value = nombres,
                onValueChange = { nombres = it },
                label = stringResource(R.string.name_label),
                icono = Icons.Default.Person,
                contentDescriptionIcono = stringResource(R.string.name_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                isError = nombresError,
                errorMessage = if (nombresError) stringResource(R.string.required_field) else ""
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoConIcono(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = stringResource(R.string.last_name_label),
                icono = Icons.Default.Person,
                contentDescriptionIcono = stringResource(R.string.last_name_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                isError = apellidosError,
                errorMessage = if (apellidosError) stringResource(R.string.required_field) else ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(R.string.sex_label),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = stringResource(R.string.sex_label), style = MaterialTheme.typography.bodyMedium)

                RadioButton(
                    selected = sexoSeleccionado == "Hombre",
                    onClick = { sexoSeleccionado = "Hombre" }
                )
                Text(stringResource(R.string.male_label))

                Spacer(modifier = Modifier.width(20.dp))

                RadioButton(
                    selected = sexoSeleccionado == "Mujer",
                    onClick = { sexoSeleccionado = "Mujer" }
                )
                Text(stringResource(R.string.female_label))

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.birth_date_label),
                    modifier = Modifier.padding(end = 8.dp)
                )

                TextField(
                    value = fechaNacimiento,
                    onValueChange = { /* No permitir edición directa */ },
                    readOnly = true,
                    label = { Text(stringResource(R.string.birth_date_label)) },
                    trailingIcon = { Button(onClick = { showDatePicker(context) { year, month, dayOfMonth ->
                        fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
                    } }) {
                        Text(stringResource(R.string.change_date_button))
                    } },
                    // Remove Modifier.weight(1f) para que el TextField no ocupe todo el ancho de la fila interior
                    isError = fechaNacimientoError,
                    supportingText = { if (fechaNacimientoError) {
                        Text(stringResource(R.string.required_field), color = MaterialTheme.colorScheme.error)
                    } }
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            var expanded by remember { mutableStateOf(false) }
            val opcionesGrado = listOf("Primaria", "Secundaria", "Universitario", "Otro") // Estos también deberían ser recursos si necesitas traducirlos

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(R.string.school_grade_label),
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
                        label = { Text(stringResource(R.string.school_grade_label)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        opcionesGrado.forEach { seleccion -> // Si necesitas traducir las opciones, considera otra estrategia (List<StringResource>)
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

            Button(onClick = {
                intentoValidacion = true
                if (nombres.isNotBlank() && apellidos.isNotBlank() && fechaNacimiento.isNotBlank()) {
                    navController.navigate("contact_data")
                } else {
                    // ... mostrar error usando stringResource(R.string.required_field)
                }
            }) {
                Text(stringResource(R.string.next_button))
            }
        }

    }
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth() ,
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
