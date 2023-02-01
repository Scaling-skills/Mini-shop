package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSecondBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.BrokenBarrierException
import java.util.concurrent.TimeUnit

class Otp : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var verifyBtn : Button
    private lateinit var resendTV : TextView
    private lateinit var inputOTP1 : EditText
    private lateinit var inputOTP2 : EditText
    private lateinit var inputOTP3 : EditText
    private lateinit var inputOTP4 : EditText
    private lateinit var inputOTP5 : EditText
    private lateinit var inputOTP6 : EditText

    private lateinit var OTP : String
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber : String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_act)


        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        init()
        addTextChangeListener()

        verifyBtn.setOnClickListener{
            val typedOTP = (inputOTP1.text.toString() + inputOTP2.text.toString() + inputOTP3.text.toString()
                           + inputOTP4.text.toString() + inputOTP5.text.toString() + inputOTP6.text.toString())

            if (typedOTP.isNotEmpty()){
                if (typedOTP.length == 6){
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP , typedOTP
                    )
                    signInWithPhoneAuthCredential(credential)
                }else{
                    Toast.makeText(this,"Enter correct OTP", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }
    }



    private fun resendVerificationCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {


            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {


            if (e is FirebaseAuthInvalidCredentialsException) {

                Log.d("TAG", "VerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("TAG", "VerificationFailed: ${e.toString()}")
            }


        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            OTP = verificationId
            resendToken = token

        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Authorization successful" , Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    Log.d("TAG", "signInWithPhoneAuthorization: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }

                }

            }
    }

    private fun sendToMain(){
        startActivity(Intent(this , MainActivity::class.java))
    }
    private fun addTextChangeListener(){
        inputOTP1.addTextChangedListener(EditTextWatcher(inputOTP1))
        inputOTP2.addTextChangedListener(EditTextWatcher(inputOTP2))
        inputOTP3.addTextChangedListener(EditTextWatcher(inputOTP3))
        inputOTP4.addTextChangedListener(EditTextWatcher(inputOTP4))
        inputOTP5.addTextChangedListener(EditTextWatcher(inputOTP5))
        inputOTP6.addTextChangedListener(EditTextWatcher(inputOTP6))


    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        verifyBtn = findViewById(R.id.verifyOTPBtn)
        resendTV = findViewById(R.id.resendTextView)
        inputOTP1 = findViewById(R.id.otpEditText1)
        inputOTP2 = findViewById(R.id.otpEditText2)
        inputOTP3 = findViewById(R.id.otpEditText3)
        inputOTP4 = findViewById(R.id.otpEditText4)
        inputOTP5 = findViewById(R.id.otpEditText5)
        inputOTP6 = findViewById(R.id.otpEditText6)



    }


    inner class EditTextWatcher(private val view : View) : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val text = s.toString()
            when(view.id){
                R.id.otpEditText1 -> if(text.length == 1) inputOTP2.requestFocus()
                R.id.otpEditText2 -> if(text.length == 1) inputOTP3.requestFocus() else if (text.isEmpty()) inputOTP1.requestFocus()
                R.id.otpEditText3 -> if(text.length == 1) inputOTP4.requestFocus() else if (text.isEmpty()) inputOTP2.requestFocus()
                R.id.otpEditText4 -> if(text.length == 1) inputOTP5.requestFocus() else if (text.isEmpty()) inputOTP3.requestFocus()
                R.id.otpEditText5 -> if(text.length == 1) inputOTP6.requestFocus() else if (text.isEmpty()) inputOTP4.requestFocus()
                R.id.otpEditText6 -> if (text.isEmpty()) inputOTP5.requestFocus()
            }

        }


    }


}