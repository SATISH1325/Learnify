package com.logic.satish.learnifyapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay


@Composable
fun OTPScreen(context: Context, navController: NavHostController, phone:String?){

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.text_otp_verify),
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

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.text_otp_sent_to),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = phone?.let { it }.toString(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        OTPView()

        Spacer(modifier = Modifier.height(30.dp))

        VerifyOtpButton(context = context, navController = navController)

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

@Composable
fun showAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){

    if (showDialog){

        AlertDialog(
            title = {
                Text(
                    text = "Your details has been submitted.",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                    )
                    },
            onDismissRequest = {
                onDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            confirmButton = {

                Box(
                    contentAlignment = Alignment.Center
                ){
                    TextButton(
                        onClick = {
                            onConfirm()
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(width = 0.5.dp, color = Color.Gray),
                    ) {
                        Text(
                            text = "OK",
                            textAlign = TextAlign.Center
                        )
                    }
                }

            },
            dismissButton = {
                onDismiss
            }
        )

    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun OTPView(){

    val textList = listOf(

        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange.Zero
            )
        ),

        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange.Zero
            )
        ),

        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange.Zero
            )
        ),

        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange.Zero
            )
        )

    )

    val requestList = listOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester()
    )

    OTPContentView(
        textList = textList,
        requestList = requestList
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPContentView(
    textList : List<MutableState<TextFieldValue>>,
    requestList : List<FocusRequester>
){


        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Row (

            ){

                for ( i in textList.indices) {
                    InputView(
                        value = textList[i].value,
                        onValueChange ={ newValue->

                            //if old value is not empty just return
                            if (textList[i].value.text != ""){

                                if (newValue.text == "") {
                                    //before return if the new value is empty, set the textfield empty
                                    textList[i].value = TextFieldValue(
                                        text = "",
                                        selection = TextRange(0)
                                    )

                                    var code = ""
                                    for (text in textList){
                                        code += text.value.text
                                    }
                                    MainActivity.OTP = code
                                }

                                return@InputView
                            }

                            //set new value and set cursor to the end
                            textList[i].value = TextFieldValue(
                                text = newValue.text,
                                selection = TextRange(newValue.text.length)
                            )

                            var code = ""
                            for (text in textList){
                                code += text.value.text
                            }
                            MainActivity.OTP = code


                            /*inputedCode(textList){
                                focusManager.clearFocus()
                                keyboardController?.hide()

                                if (it){
                                    Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context,"Error Input Again",Toast.LENGTH_SHORT).show()
                                    for (text in textList){
                                        text.value = TextFieldValue(
                                            text = "",
                                            selection = TextRange(0)
                                        )
                                    }
                                }
                            }*/

                            nextFocus(textList,requestList)
                        },
                        focusRequester = requestList[i]
                    )
                }
            }
        }



    LaunchedEffect(
        key1 = null,
        block = {
            delay(300)
            requestList[0].requestFocus()
        }
    )

}

private fun nextFocus(
    textList: List<MutableState<TextFieldValue>>,
    requestList: List<FocusRequester>
){
    for ( index in textList.indices){
        if (textList[index].value.text == ""){
            if (index < textList.size){
                requestList[index].requestFocus()
                break
            }
        }
    }
}

@Composable
fun InputView(
    value: TextFieldValue,
    onValueChange : (value:TextFieldValue)-> Unit,
    focusRequester: FocusRequester
){


        BasicTextField(
            value = value,
            readOnly = false,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(4.dp)
                //.clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF9F9F9))
                .wrapContentSize()
                .border(
                    width = 0.5.dp,
                    color = Color(0xFF6F6D6D),
                    shape = RoundedCornerShape(10.dp)
                )
                .focusRequester(focusRequester),
            maxLines = 1,
            cursorBrush = SolidColor(Color.Gray),
            decorationBox = @Composable { innerTextField ->

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()
                }


            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = null
            )
        )

}

private fun inputedCode(
    textList: List<MutableState<TextFieldValue>>,
    onVerifyCode: ((success:Boolean) -> Unit)? = null
){
    var code = ""
    for (text in textList){
        code += text.value.text
    }

    MainActivity.OTP = code

    if (code.length == 4){

        verifyCode(
            code = code,
            onSuccess = {
               onVerifyCode?.let {
                   it(true)
               }
            },
            onError = {
                onVerifyCode?.let {
                    it(false)
                }
            }
        )
    }
}

private fun verifyCode(
    code : String,
    onSuccess : ()-> Unit,
    onError : () -> Unit
){
    if (code == "1234"){
        onSuccess()
    } else {
        onError()
    }
}

@Composable
fun VerifyOtpButton(
    context: Context,
    navController: NavHostController
){

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        showAlertDialog(
            showDialog = showDialog.value,
            onDismiss = {
                showDialog.value = false
            },
            onConfirm = {
                showDialog.value = false

                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.LoginScreen.route){
                        inclusive = true
                    }
                }


            }
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {

            if(MainActivity.OTP.length ==4){
                showDialog.value = true
            } else {
                Toast.makeText(context,"Please Enter Valid OTP",Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE34532)
        )
    ) {
        Text(
            text = stringResource(id = R.string.btn_verify_otp),
            fontSize = 20.sp,
            modifier = Modifier.padding(2.dp)
        )
    }

}


@Preview
@Composable
fun ShowView(){
    var context = LocalContext.current
    var navController = rememberNavController()
    OTPScreen(context,navController,phone = null)
}