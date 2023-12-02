package com.logic.satish.learnifyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.logic.satish.learnifyapp.ui.theme.LearnifyAppTheme

class MainActivity : ComponentActivity() {

    companion object{
        var OTP : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            LearnifyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var navController = rememberNavController()
                    ComposeNavigation(context = context,navController = navController)
                }
            }
        }
    }


}

@Composable
fun ComposeNavigation(context: Context, navController: NavHostController){
    
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){

        composable(
            route = Screen.LoginScreen.route
        ){
            LoginScreen(context = context, navController)
        }
        
        composable(
            route = Screen.OTPScreen.route
        ){
                entry->
            OTPScreen(context = context, navController = navController, phone = entry.arguments?.getString("phone"))

        }
    }
}



