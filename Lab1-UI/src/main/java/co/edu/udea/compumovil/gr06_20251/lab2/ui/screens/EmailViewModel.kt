package co.edu.udea.compumovil.gr06_20251.lab2.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr06_20251.lab2.data.local.EmailDatabase
import co.edu.udea.compumovil.gr06_20251.lab2.data.model.Email
import co.edu.udea.compumovil.gr06_20251.lab2.data.repository.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmailRepository
    private val _searchQuery = MutableStateFlow("")
    private val _currentTab = MutableStateFlow(EmailTab.ALL)
    private val _selectedEmailId = MutableStateFlow<Long?>(null)

    init {
        val database = EmailDatabase.getDatabase(application)
        repository = EmailRepository(database.emailDao(), application)
        // Iniciar la sincronización periódica
        repository.startEmailSync()
    }

    val searchQuery: StateFlow<String> = _searchQuery
    val currentTab: StateFlow<EmailTab> = _currentTab
    val selectedEmailId: StateFlow<Long?> = _selectedEmailId

    val emailsUiState = combine(
        repository.allEmails,
        repository.starredEmails,
        repository.unreadEmails,
        _searchQuery,
        _currentTab
    ) { allEmails, starredEmails, unreadEmails, query, tab ->
        val emails = when (tab) {
            EmailTab.ALL -> allEmails
            EmailTab.STARRED -> starredEmails
            EmailTab.UNREAD -> unreadEmails
        }

        if (query.isBlank()) {
            EmailsUiState.Success(emails)
        } else {
            EmailsUiState.Success(emails.filter {
                it.subject.contains(query, ignoreCase = true) ||
                it.body.contains(query, ignoreCase = true)
            })
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EmailsUiState.Loading
    )

    val selectedEmail = combine(
        repository.allEmails,
        _selectedEmailId
    ) { emails, selectedId ->
        selectedId?.let { id ->
            emails.find { it.id == id }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateCurrentTab(tab: EmailTab) {
        _currentTab.value = tab
    }

    fun selectEmail(emailId: Long) {
        _selectedEmailId.value = emailId
        viewModelScope.launch {
            repository.markEmailAsRead(selectedEmail.value ?: return@launch)
        }
    }

    fun unselectEmail() {
        _selectedEmailId.value = null
    }

    fun toggleEmailStar(emailId: Long) {
        viewModelScope.launch {
            val email = (emailsUiState.value as? EmailsUiState.Success)?.emails?.find { it.id == emailId }
            email?.let { repository.toggleEmailStar(it) }
        }
    }

    fun deleteEmail(emailId: Long) {
        // TODO: Implementar eliminación de correos
    }
}

enum class EmailTab {
    ALL, STARRED, UNREAD
}

sealed interface EmailsUiState {
    data object Loading : EmailsUiState
    data class Success(val emails: List<Email>) : EmailsUiState
    data class Error(val message: String) : EmailsUiState
} 
