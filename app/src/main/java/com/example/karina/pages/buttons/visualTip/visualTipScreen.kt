package com.example.karina.pages.buttons.visualTip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.karina.ui.theme.ForestGreen
import com.example.karina.ui.theme.Gold
import com.example.karina.ui.theme.RedOrange

enum class DifficultyLevel {
    EASY, MEDIUM, HARD
}

@OptIn(ExperimentalMaterial3Api::class) // Добавляем OptIn
@Composable
fun VisualTipScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Визуальные подсказки") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LevelButton(
                text = "Легкий",
                level = DifficultyLevel.EASY,
                navController = navController,
                buttonColor = ForestGreen
            )
            Spacer(modifier = Modifier.height(20.dp))
            LevelButton(
                text = "Средний",
                level = DifficultyLevel.MEDIUM,
                navController = navController,
                buttonColor = Gold
            )
            Spacer(modifier = Modifier.height(20.dp))
            LevelButton(
                text = "Сложный",
                level = DifficultyLevel.HARD,
                navController = navController,
                buttonColor = RedOrange
            )
        }
    }
}

@Composable
fun LevelButton(text: String, level: DifficultyLevel, navController: NavController, buttonColor: Color) {
    Button(
        onClick = {
            navController.navigate("emotionTest/$level")
        },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(IntrinsicSize.Min),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}