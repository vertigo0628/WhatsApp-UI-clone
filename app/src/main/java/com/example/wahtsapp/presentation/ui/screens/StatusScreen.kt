package com.example.wahtsapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wahtsapp.R
import com.example.wahtsapp.domain.model.Status
import com.example.wahtsapp.presentation.theme.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(
    onNavigateBack: () -> Unit
) {
    // Sample data - in a real app, this would come from a ViewModel
    val statusUpdates = remember {
        listOf(
            Status(
                id = "1",
                userName = "My Status",
                timeAgo = "Tap to add status update",
                isMe = true,
                hasUnviewed = false,
                imageUrl = null
            ),
            Status(
                id = "2",
                userName = "John",
                timeAgo = "10 minutes ago",
                isMe = false,
                hasUnviewed = true,
                imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            Status(
                id = "3",
                userName = "Sarah",
                timeAgo = "2 hours ago",
                isMe = false,
                hasUnviewed = false,
                imageUrl = "https://randomuser.me/api/portraits/women/2.jpg"
            ),
            Status(
                id = "4",
                userName = "Mike",
                timeAgo = "5 hours ago",
                isMe = false,
                hasUnviewed = true,
                imageUrl = "https://randomuser.me/api/portraits/men/3.jpg"
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Status", 
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
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
            actions = {
                IconButton(onClick = { /* Handle new status */ }) {
                    Icon(
                        Icons.Default.PhotoCamera,
                        contentDescription = "Camera",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            )
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // My status
            item {
                StatusItem(
                    status = statusUpdates[0],
                    onClick = { /* Handle status click */ }
                )
                
                // Recent updates
                Text(
                    text = "Recent updates",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Other statuses
            items(statusUpdates.drop(1)) { status ->
                StatusItem(
                    status = status,
                    onClick = { /* Handle status click */ }
                )
            }
        }
    }
}

@Composable
private fun StatusItem(
    status: Status,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status indicator ring
        val borderColor = if (status.hasUnviewed) WhatsAppGreen else Color.Gray
        
        Box(
            modifier = Modifier
                .size(56.dp)
                .padding(2.dp)
                .background(
                    color = borderColor,
                    shape = CircleShape
                )
        ) {
            // Profile picture
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .padding(2.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (status.imageUrl != null) {
                    // In a real app, use Coil or Glide to load the image
                    // For now, using a placeholder
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_placeholder),
                        contentDescription = status.userName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Add status icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(WhatsAppGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PhotoCamera,
                            contentDescription = "Add status",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = status.userName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = if (status.hasUnviewed) FontWeight.Bold else FontWeight.Normal
            )
            
            Text(
                text = status.timeAgo,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
        }
    }
}
