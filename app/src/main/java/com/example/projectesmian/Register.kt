package com.example.projectesmian

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projectesmian.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registrasi.setOnClickListener {
            val email : String = binding.emailE.text.toString().trim()
            val password : String = binding.passwordE.text.toString().trim()
            val  confirmPassword : String = binding.confirmB.text.toString().trim()

            if (email.isEmpty()){
                binding.emailE.error = "Input Email"
                binding.emailE.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailE.error ="Iil"
                binding.emailE.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6){
                binding.passwordE.error = "password be more tthan 6 characters"
                binding.passwordE.requestFocus()
                return@setOnClickListener            }

            if(password != confirmPassword){
                binding.passwordE.error = "password mus be match"
                binding.passwordE.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password)
        }







    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else{
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            Intent(this, Home::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}