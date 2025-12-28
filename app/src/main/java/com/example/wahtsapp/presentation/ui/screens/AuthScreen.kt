package com.example.wahtsapp.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformer
import androidx.compose.ui.unit.dp
import com.example.wahtsapp.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onNavigateToHome: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var isSignIn by remember { mutableStateOf(true) }
    var verificationCode by remember { mutableStateOf("") }
    var showVerificationCode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Center,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isSignIn) "Sign In" else "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = WhatsAppGreen
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (showVerificationCode) {
            OutlinedTextField(
                value = verificationCode,
                onValueChange = { verificationCode = it },
                label = { Text("Verification Code") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    // Verify code and navigate to home
                    onNavigateToHome()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
            ) {
                Text("Verify Code")
            }
        } else {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = { 
                    Text(
                        text = "+1 ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    // In a real app, you would send a verification code here
                    showVerificationCode = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = phoneNumber.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
            ) {
                Text(if (isSignIn) "Sign In" else "Create Account")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = { isSignIn = !isSignIn }
            ) {
                Text(
                    text = if (isSignIn) "Create new account" else "Already have an account? Sign in",
                    color = WhatsAppGreen
                )
            }
        }
    }
}
