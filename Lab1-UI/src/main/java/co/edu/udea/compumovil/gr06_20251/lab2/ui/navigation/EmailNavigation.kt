package co.edu.udea.compumovil.gr06_20251.lab2.ui.navigation

sealed class Screen(val route: String) {
    object EmailList : Screen("emailList")
    object EmailDetail : Screen("emailDetail/{emailId}") {
        fun createRoute(emailId: String) = "emailDetail/$emailId"
    }
}

object EmailDestinations {
    const val EMAIL_LIST_ROUTE = "emailList"
    const val EMAIL_DETAIL_ROUTE = "emailDetail/{emailId}"
    const val EMAIL_ID_KEY = "emailId"
}
