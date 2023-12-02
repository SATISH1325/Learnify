package com.logic.satish.learnifyapp


sealed class Screen(val route:String){
    object LoginScreen : Screen(route = "login_screen")
   // object OTPScreen : Screen(route = "otp_screen")

    object OTPScreen : Screen("{phone}/otp_screen") {
        fun sendPhone(phone: String) = "$phone/otp_screen"
    }

}
