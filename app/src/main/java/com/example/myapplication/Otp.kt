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
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        verifyBtn = findViewById(R.id.verifyOTPBtn)
        resendTV = findViewById(R.id.resendTextView)
    }

}