package co.edu.udea.compumovil.gr06_20251.lab2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Forward
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Forward
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr06_20251.lab2.R
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailScreen(
    email: Email?,
    onBackClick: () -> Unit,
    onStarClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReplyClick: () -> Unit,
    onForwardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onStarClick) {
                        Icon(
                            imageVector = if (email?.isStarred == true) 
                                Icons.Default.Star 
                            else 
                                Icons.Default.StarBorder,
                            contentDescription = if (email?.isStarred == true)
                                stringResource(R.string.unstar)
                            else
                                stringResource(R.string.star)
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onReplyClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Reply,
                            contentDescription = stringResource(R.string.reply)
                        )
                    }
                    IconButton(onClick = onForwardClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Forward,
                            contentDescription = stringResource(R.string.forward)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (email == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Encabezado
                Text(
                    text = email.senderName,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = email.senderEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = email.createdAt,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Asunto
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Cuerpo del correo
                Text(
                    text = email.body,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
} 