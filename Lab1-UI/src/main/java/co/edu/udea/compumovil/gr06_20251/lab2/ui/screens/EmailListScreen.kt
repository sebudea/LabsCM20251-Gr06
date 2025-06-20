package co.edu.udea.compumovil.gr06_20251.lab2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr06_20251.lab2.R
import co.edu.udea.compumovil.gr06_20251.lab2.ui.components.EmailItem
import co.edu.udea.compumovil.gr06_20251.lab2.ui.components.EmailSearchBar
import co.edu.udea.compumovil.gr06_20251.lab2.ui.components.EmailTabs

@Composable
fun EmailListScreen(
        uiState: EmailsUiState,
        searchQuery: String,
        currentTab: EmailTab,
        onSearchQueryChange: (String) -> Unit,
        onTabSelected: (EmailTab) -> Unit,
        onEmailClick: (String) -> Unit,
        onEmailStar: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        EmailSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(16.dp)
        )

        EmailTabs(selectedTab = currentTab, onTabSelected = onTabSelected)

        when (uiState) {
            is EmailsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is EmailsUiState.Success -> {
                if (uiState.emails.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                                text =
                                        when (currentTab) {
                                            EmailTab.ALL -> stringResource(R.string.no_emails)
                                            EmailTab.STARRED ->
                                                    stringResource(R.string.no_starred_emails)
                                            EmailTab.UNREAD ->
                                                    stringResource(R.string.no_unread_emails)
                                        },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = uiState.emails, key = { it.id }) { email ->
                            EmailItem(
                                    email = email,
                                    onEmailClick = { onEmailClick(email.id) },
                                    onStarClick = { onEmailStar(email.id) }
                            )
                        }
                    }
                }
            }
            is EmailsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                            text = stringResource(R.string.error_loading_emails),
                            color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
