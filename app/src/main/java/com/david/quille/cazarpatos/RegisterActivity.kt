package com.david.quille.cazarpatos

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.editTextRegisterEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextRegisterPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextRegisterConfirmPassword)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        val backToLoginButton = findViewById<Button>(R.id.buttonBackToLogin)


        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        confirmPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

        backToLoginButton.setOnClickListener {
            finish()
        }

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            var valid = true


            if (email.isEmpty()) {
                emailEditText.error = getString(R.string.error_email_required)
                valid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = getString(R.string.error_email_invalid)
                valid = false
            } else {
                emailEditText.error = null
            }


            if (password.isEmpty()) {
                passwordEditText.error = getString(R.string.error_password_required)
                valid = false
            } else if (password.length < 6) {
                passwordEditText.error = getString(R.string.error_password_min_length_6)
                valid = false
            } else {
                passwordEditText.error = null
            }


            if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.error = getString(R.string.error_confirm_password_required)
                valid = false
            } else if (password != confirmPassword) {
                confirmPasswordEditText.error = getString(R.string.error_passwords_not_match)
                valid = false
            } else {
                confirmPasswordEditText.error = null
            }

            if (valid) {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, getString(R.string.text_sign_up) + " exitoso", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {

                            Toast.makeText(this, task.exception?.localizedMessage ?: "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
