package com.example.projectesmian

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectesmian.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        binding.Mlogin.setOnClickListener {

            val email: String = binding.etEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Input Email"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Iil"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.etPassword.error = "password be more tthan 6 characters"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }


            loginUser(email, password)


        }


//        binding.Mlogin.setOnClickListener()
//        {
//            val intenhome = Intent(this, Home::class.java)
//            startActivity(intenhome)
//        }

//       binding.lupapassword.setOnClickListener()
//        {
//            val intentlupasandi = Intent(this,lupapasword::class.java)
//            startActivity(intentlupasandi)
//        }
        binding.register.setOnClickListener()
        {
            val intentregistrasi = Intent(this, Register::class.java)
            startActivity(intentregistrasi)
        }


//        forgot password// lupas password
                binding.lupapassword.setOnClickListener {
                    val builder = AlertDialog.Builder(this)

                    val tampilkan = layoutInflater.inflate(R.layout.forgot, null)

                    val userEmail = tampilkan.findViewById<EditText>(R.id.editBox)

                    builder.setView(tampilkan)

                    val dialog = builder.create()

                    tampilkan.findViewById<Button>(R.id.btnReset).setOnClickListener {

                        compareEmail(userEmail)
                    }

                    tampilkan.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                        dialog.dismiss()
                    }

                    if (dialog.window != null){
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
                    }
                    dialog.show()
                }



    }

    private fun compareEmail(userEmail: EditText) {
        if (userEmail.text.toString().isEmpty()){
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()){
            return
        }

        firebaseAuth.sendPasswordResetEmail(userEmail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Coba Cek Email", Toast.LENGTH_SHORT).show()
                }
            }
    }
//    private fun compareEmail(email: EditText) {
//        if (email.text.toString().isEmpty()){
//            return
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
//            return
//        }
//
//        firebaseAuth.sendPasswordResetEmail(email.text.toString())
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//    }


    private fun loginUser(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Intent(this, Home::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            } else {
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

