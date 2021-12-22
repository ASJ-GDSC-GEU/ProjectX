package com.example.projectx.screens

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.projectx.R
import com.example.projectx.databinding.ActivityGetStartedBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GetStartedActivity : AppCompatActivity() {
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var binding : ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseSignUp()

        binding.continueEmailBTN.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun initialiseSignUp(){
        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }



}