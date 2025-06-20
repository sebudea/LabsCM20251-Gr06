package co.edu.udea.compumovil.gr06_20251.lab2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr06_20251.lab2.R
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import co.edu.udea.compumovil.gr06_20251.lab2.ui.screens.EmailTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text(stringResource(R.string.search_emails)) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
            },
            modifier = modifier.fillMaxWidth()
    ) {}
}

@Composable
fun EmailTabs(
        selectedTab: EmailTab,
        onTabSelected: (EmailTab) -> Unit,
        modifier: Modifier = Modifier
) {
    TabRow(selectedTabIndex = selectedTab.ordinal, modifier = modifier) {
        EmailTab.values().forEach { tab ->
            Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                                text =
                                        stringResource(
                                                when (tab) {
                                                    EmailTab.ALL -> R.string.all
                                                    EmailTab.STARRED -> R.string.starred
                                                    EmailTab.UNREAD -> R.string.unread
                                                }
                                        )
                        )
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailItem(
        email: Email,
        onEmailClick: () -> Unit,
        onStarClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    Card(
            onClick = onEmailClick,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = email.senderName, style = MaterialTheme.typography.titleMedium)
                    Text(
                            text = email.senderEmail,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onStarClick) {
                    Icon(
                            imageVector =
                                    if (email.isStarred) Icons.Default.Star
                                    else Icons.Default.StarBorder,
                            contentDescription =
                                    stringResource(
                                            if (email.isStarred) R.string.unstar else R.string.star
                                    ),
                            tint =
                                    if (email.isStarred) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = email.subject, style = MaterialTheme.typography.bodyLarge)

            Text(
                    text = email.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
            )

            Text(
                    text = email.createdAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
