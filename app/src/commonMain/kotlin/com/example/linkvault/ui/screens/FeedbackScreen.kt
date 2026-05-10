package com.example.linkvault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.linkvault.ui.viewmodel.LinkViewModel
import kotlinx.coroutines.launch

@Composable
fun FeedbackScreen(viewModel: LinkViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Submit Feedback",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "Have a problem or a suggestion? Let us know!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth().widthIn(max = 500.dp),
            singleLine = true,
            placeholder = { Text("Vinayak Kumar") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Your Email") },
            modifier = Modifier.fillMaxWidth().widthIn(max = 500.dp),
            singleLine = true,
            placeholder = { Text("email@example.com") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Feedback / Problem Description") },
            modifier = Modifier.fillMaxWidth().widthIn(max = 500.dp).height(200.dp),
            placeholder = { Text("Please describe your issue or feedback in detail...") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (statusMessage != null) {
            Text(
                text = statusMessage!!,
                color = if (statusMessage!!.contains("Error") || statusMessage!!.contains("Failed")) 
                    MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                    isSubmitting = true
                    viewModel.submitFeedback(name, email, message) { success, msg ->
                        isSubmitting = false
                        statusMessage = msg
                        if (success) {
                            name = ""
                            email = ""
                            message = ""
                        }
                    }
                } else {
                    statusMessage = "Please fill in all fields (Name, Email, and Feedback)."
                }
            },
            enabled = !isSubmitting,
            modifier = Modifier.width(200.dp)
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Send Feedback")
            }
        }
    }
}
