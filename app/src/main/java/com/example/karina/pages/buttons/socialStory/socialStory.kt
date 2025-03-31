package com.example.karina.pages.buttons.socialStory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.karina.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 6 }) // Указываем pageCount
    val images = listOf(
        R.drawable.story_in_frame_1,
        R.drawable.story_in_frame_2,
        R.drawable.story_in_frame_3,
        R.drawable.story_in_frame_4,
        R.drawable.story_in_frame_5,
        R.drawable.story_in_frame_6,
    )

    // Определяем TextStyle ВНУТРИ Composable функции
    val baseTextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = Color.Black,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )

    val parentTextStyle = baseTextStyle.copy(
        fontStyle = FontStyle.Italic, // Делаем текст "Для родителей" курсивом
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Социальные истории") },
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
            horizontalAlignment = Alignment.CenterHorizontally
            // Убираем verticalArrangement = Arrangement.Center, чтобы текст был снизу
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Занимает все доступное пространство сверху
            ) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Image ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                )
            }

            // Индикатор (точки) - самодельный
            Row(
                Modifier
                    .height(50.dp) // Уменьшаем высоту, если нужно
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), // Вертикальные отступы для ряда точек
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically // Выравниваем точки по центру вертикально
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp) // Горизонтальные отступы между точками
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            // Получаем контент для текущей страницы
            val currentPageContent = storyPagesContent.getOrNull(pagerState.currentPage)

            // Используем Column для отображения текстовых блоков друг под другом
            // без дополнительных отступов между ними
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Занимает всю ширину
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp) // Отступы по краям
            ) {
                if (currentPageContent != null) {
                    // Основной текст
                    Text(
                        text = currentPageContent.mainText,
                        style = baseTextStyle
                    )
                    // Текст для родителей
                    Text(
                        text = currentPageContent.parentText,
                        style = parentTextStyle // Применяем стиль с курсивом
                    )
                    // Текст задания (с AnnotatedString для жирного шрифта)
                    Text(
                        text = currentPageContent.taskText,
                        style = baseTextStyle // Используем базовый стиль, жирный шрифт применится из AnnotatedString
                    )
                }
            }
        }
    }
}

