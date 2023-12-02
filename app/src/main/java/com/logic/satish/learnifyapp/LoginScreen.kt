package com.logic.satish.learnifyapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(context: Context, navController: NavHostController){

    var expanded  by remember { mutableStateOf(false)   }
    val countryCodes = listOf("+91","+44","+11","+81","+86","+97","+88")
    var selectedCountryCode: String by remember { mutableStateOf(countryCodes[0])    }
    var phoneNumber by remember{mutableStateOf("")}


    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.text_log_in),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.learnify_image),
            contentDescription = "image",
            modifier = Modifier
                .padding(16.dp)
                .width(140.dp)
                .height(120.dp),
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFF9F9F9))
                .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
        ) {
            Row(

            ) {

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier
                        .width(100.dp)
                        .background(color = Color.White)
                ) {
                    TextField(
                        value = "$selectedCountryCode",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            containerColor = Transparent
                        ),
                        modifier = Modifier.menuAnchor(),
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        countryCodes.forEach{ code->

                            DropdownMenuItem(
                                text = { Text(text = code) },
                                onClick = {
                                    selectedCountryCode = code
                                    expanded = false
                                }
                            )

                        }

                    }


                }

                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        if(it.isDigitsOnly() && it.length <= 10) {
                            phoneNumber = it
                        }
                    },
                    placeholder = {
                        Text(text = "Phone Number")
                    },
                    singleLine = true,
                    maxLines = 1,
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        containerColor = Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                ) //

            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        GetOtpButton(context, onClick = {

            if (!validatePhoneNumber(phoneNumber))
                Toast.makeText(context,"Phone Number can not be empty",Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(context, "OTP sent to $selectedCountryCode $phoneNumber Successfully", Toast.LENGTH_SHORT)
                    .show()
                val numberWithCode: String = selectedCountryCode.plus(" ").plus(phoneNumber)
                    navController.navigate(Screen.OTPScreen.sendPhone(numberWithCode))
            }
        })

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.text_term_condition),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )



    }

}

private fun validatePhoneNumber(phoneNumber:String):Boolean{

    if (phoneNumber.trim().isEmpty())
        return false
    return true
}

@Preview
@Composable
fun LoginScreenPreview(){

    var context = LocalContext.current
    var navController = rememberNavController()

    LoginScreen(context, navController)
}

@Composable
fun GetOtpButton(context: Context,onClick: ()-> Unit){

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {

            onClick()

        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE34532)
        )
    ) {
        Icon(
            painterResource(id = R.drawable.ic_otp) ,
            contentDescription ="",
            tint = Color(0xFF098CF5),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.btn_get_otp),
            fontSize = 20.sp,

            modifier = Modifier.padding(2.dp)
        )
    }

}
