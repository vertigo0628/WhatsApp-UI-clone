package com.example.wahtsapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wahtsapp.presentation.ui.components.ChatBubble
import com.example.wahtsapp.presentation.ui.components.ChatInputBar
import com.example.wahtsapp.presentation.theme.*
import com.example.wahtsapp.presentation.viewModel.ChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val messages by viewModel.messages.collectAsState()
    val messageText by viewModel.messageText.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatBackgroundLight)
    ) {
        TopAppBar(
            title = { Text("John Doe", color = Neutral10) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Neutral10)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WhatsAppGreen
            )
        )
        
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }
        
        ChatInputBar(
            messageText = messageText,
            onMessageTextChanged = { viewModel.onMessageTextChanged(it) },
            onSendMessage = { 
                viewModel.sendMessage()
                coroutineScope.launch {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
    }
}

