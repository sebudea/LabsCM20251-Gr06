package co.edu.udea.compumovil.gr06_20251.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.udea.compumovil.gr06_20251.lab2.ui.navigation.EmailDestinations
import co.edu.udea.compumovil.gr06_20251.lab2.ui.navigation.Screen
import co.edu.udea.compumovil.gr06_20251.lab2.ui.screens.EmailDetailScreen
import co.edu.udea.compumovil.gr06_20251.lab2.ui.screens.EmailListScreen
import co.edu.udea.compumovil.gr06_20251.lab2.ui.screens.EmailViewModel
import co.edu.udea.compumovil.gr06_20251.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    private val viewModel: EmailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val uiState by viewModel.emailsUiState.collectAsState()
                    val searchQuery by viewModel.searchQuery.collectAsState()
                    val currentTab by viewModel.currentTab.collectAsState()
                    val selectedEmail by viewModel.selectedEmail.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.EmailList.route
                    ) {
                        composable(Screen.EmailList.route) {
                            EmailListScreen(
                                uiState = uiState,
                                searchQuery = searchQuery,
                                currentTab = currentTab,
                                onSearchQueryChange = viewModel::updateSearchQuery,
                                onTabSelected = viewModel::updateCurrentTab,
                                onEmailClick = { emailId ->
                                    viewModel.selectEmail(emailId)
                                    navController.navigate(Screen.EmailDetail.createRoute(emailId))
                                },
                                onEmailStar = viewModel::toggleEmailStar
                            )
                        }

                        composable(
                            route = Screen.EmailDetail.route,
                            arguments = listOf(
                                navArgument(EmailDestinations.EMAIL_ID_KEY) {
                                    type = NavType.LongType
                                }
                            )
                        ) {
                            EmailDetailScreen(
                                email = selectedEmail,
                                onBackClick = {
                                    viewModel.unselectEmail()
                                    navController.navigateUp()
                                },
                                onStarClick = {
                                    selectedEmail?.id?.let { emailId ->
                                        viewModel.toggleEmailStar(emailId)
                                    }
                                },
                                onDeleteClick = {
                                    selectedEmail?.id?.let { emailId ->
                                        viewModel.deleteEmail(emailId)
                                        navController.navigateUp()
                                    }
                                },
                                onReplyClick = { /* TODO: Implementar respuesta */ },
                                onForwardClick = { /* TODO: Implementar reenvío */ }
                            )
                        }
                    }
                }
            }
        }
    }
}