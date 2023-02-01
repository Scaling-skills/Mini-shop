package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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


class PhoneAct : AppCompatActivity() {

    private lateinit var sendOTPBn : Button
    private lateinit var phoneNumberET : EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var number : String
    private lateinit var mProgressBar : ProgressBar



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_act)

        init()
        sendOTPBn.setOnClickListener {
            number = phoneNumberET.text.trim().toString()
            if (number.isNotEmpty()){
                if (number.length == 9){
                    number = "+48$number"
                    mProgressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                }else{
                    Toast.makeText(this , "incorrect number" , Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this , "Enter number" , Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun init(){
        mProgressBar = findViewById(R.id.phoneProgressBar)
        mProgressBar.visibility = View.INVISIBLE
        sendOTPBn = findViewById(R.id.sendOTPBtn)
        phoneNumberET = findViewById(R.id.phoneEditTextNumber)
        auth = FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Authorization successful" , Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("TAG", "signInWithPhoneAuthorization: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }

                }
            }
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

            val intent = Intent(this@PhoneAct , Otp::class.java)
            intent.putExtra("OTP" , verificationId)
            intent.putExtra("resendToken" , token)
            intent.putExtra("phoneNumber" , number)
            startActivity(intent)
            mProgressBar.visibility = View.INVISIBLE

        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this , MainActivity::class.java))

        }
    }
}
