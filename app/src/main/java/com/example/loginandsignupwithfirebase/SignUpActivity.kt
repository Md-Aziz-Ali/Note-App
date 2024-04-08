package com.example.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupwithfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        binding.signIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        binding.register.setOnClickListener {
            val email = binding.email.text.toString()
            val userName = binding.username.text.toString()
            val password = binding.password.text.toString();
            val repeatPassword = binding.repeatpassword.text.toString()

            if(email.isEmpty() || userName.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else if(password != repeatPassword) {
                Toast.makeText(this, "Repeat Password Must be Same", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "Registeration Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,LoginActivity::class.java))
                            finish()
                        }
                        else {
                            Toast.makeText(this, "Registration failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }
    }
}