package com.example.karina.pages.buttons.visualTip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@OptIn(ExperimentalMaterial3Api::class) // Добавляем OptIn
@Composable
// Добавляем NavController в параметры
fun EmotionTestResultScreen(navController: NavController, score: Int, totalQuestions: Int) {
    Scaffold( // Оборачиваем в Scaffold
        topBar = {
            TopAppBar(
                title = { Text("Результат теста") }, // Заголовок
                navigationIcon = {
                    IconButton(onClick = {
                        // Возвращаемся на экран выбора уровня
                        navController.popBackStack("visualTip", inclusive = false)
                    }) {
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
    ) { innerPadding -> // Получаем внутренние отступы
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Применяем отступы от Scaffold
                .background(Color.White) // Добавляем белый фон
                .padding(16.dp), // Дополнительные отступы для контента
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Результат: $score из $totalQuestions", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Молодец!", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(32.dp)) // Добавим больше отступа перед кнопкой

            // --- Новая кнопка "Закончить тест" ---
            Button(
                onClick = {
                    navController.navigate("main") {
                        // Очищаем весь бэкстек до стартового экрана ("main")
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true // Удаляем и сам стартовый экран из стека (если он там был)
                        }
                        // Гарантируем, что не создастся новая копия main, если он уже наверху стека
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Ширина как у кнопок выбора уровня
                    .height(IntrinsicSize.Min), // Высота подстраивается под контент
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary, // Другой цвет для завершающего действия
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
                // Можно добавить рамку, если нужно единство стиля
                // border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
            ) {
                Text(
                    text = "Закончить тест",
                    style = MaterialTheme.typography.bodyLarge, // Стиль как у других кнопок
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}
