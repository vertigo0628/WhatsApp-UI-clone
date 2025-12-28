package com.example.wahtsapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wahtsapp.R
import com.example.wahtsapp.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("John Doe") }
    var about by remember { mutableStateOf("Hey there! I'm using WhatsApp") }
    var phoneNumber by remember { mutableStateOf("+1 234 567 890") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Profile", 
                    color = MaterialTheme.colorScheme.onBackground
                ) 
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.Default.ArrowBack, 
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
                
                IconButton(
                    onClick = { /* Handle edit photo */ },
                    modifier = Modifier
                        .background(WhatsAppGreen, CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Photo",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = about,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile Info Section
            ProfileInfoItem("Name", name) { newName ->
                name = newName
            }
            
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            
            ProfileInfoItem("About", about) { newAbout ->
                about = newAbout
            }
            
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            
            ProfileInfoItem("Phone", phoneNumber, editable = false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileInfoItem(
    title: String,
    value: String,
    editable: Boolean = true,
    onValueChange: (String) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedValue by remember { mutableStateOf(value) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            
            if (isEditing) {
                OutlinedTextField(
                    value = editedValue,
                    onValueChange = { editedValue = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        
        if (editable) {
            if (isEditing) {
                TextButton(
                    onClick = {
                        onValueChange(editedValue)
                        isEditing = false
                    }
                ) {
                    Text("Save", color = WhatsAppGreen)
                }
            } else {
                IconButton(
                    onClick = { 
                        isEditing = true
                        editedValue = value
                    }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit $title",
                        tint = WhatsAppGreen
                    )
                }
            }
        }
    }
}
