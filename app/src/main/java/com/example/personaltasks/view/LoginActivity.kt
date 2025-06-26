package com.example.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Launcher para iniciar a Activity de login do FirebaseUI
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Login bem-sucedido
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(this, "Bem-vindo, ${user?.email}", Toast.LENGTH_SHORT).show()

            // Ir para a tela principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Login cancelado ou falhou", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se o usuário já está logado, ir direto pra MainActivity
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Inicia o fluxo de login do FirebaseUI
        startLogin()
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val loginIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false) // evitar salvar login no dispositivo
            .setLogo(R.drawable.ic_launcher_foreground) // opcional
            .setTheme(R.style.Theme_PersonalTasks)       // opcional
            .build()

        signInLauncher.launch(loginIntent)
    }
}
