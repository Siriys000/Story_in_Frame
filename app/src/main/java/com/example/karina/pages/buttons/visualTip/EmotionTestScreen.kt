package com.example.karina.pages.buttons.visualTip

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.karina.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionTestScreen(navController: NavController, difficultyLevel: String?) {
    val level = difficultyLevel?.let { runCatching { DifficultyLevel.valueOf(it.uppercase()) }.getOrNull() } ?: DifficultyLevel.EASY // Более безопасный парсинг

    // Распределение картинок по уровням - ЭТОТ БЛОК РАБОТАЕТ ПРАВИЛЬНО
    val imageList = remember(level) {
        when (level) {
            DifficultyLevel.EASY -> listOf(
                R.drawable.emotion_1,
                R.drawable.emotion_2,
                R.drawable.emotion_3,
                R.drawable.emotion_4,
                R.drawable.emotion_5
            )
            DifficultyLevel.MEDIUM -> listOf(
                R.drawable.emotion_6,
                R.drawable.emotion_7,
                R.drawable.emotion_8,
                R.drawable.emotion_9,
                R.drawable.emotion_10
            )
            DifficultyLevel.HARD -> listOf(
                R.drawable.emotion_11,
                R.drawable.emotion_12,
                R.drawable.emotion_13,
                R.drawable.emotion_14,
                R.drawable.emotion_15
            )
        }
    }

    // Обновленная карта верных ответов
    val correctAnswerMap = remember {
        mapOf(
            R.drawable.emotion_1 to "Грусть",
            R.drawable.emotion_2 to "Злость",
            R.drawable.emotion_3 to "Радость",
            R.drawable.emotion_4 to "Спокойствие",
            R.drawable.emotion_5 to "Удивление",
            R.drawable.emotion_6 to "Грусть",
            R.drawable.emotion_7 to "Радость",
            R.drawable.emotion_8 to "Скука",
            R.drawable.emotion_9 to "Спокойствие",
            R.drawable.emotion_10 to "Тревога",
            R.drawable.emotion_11 to "Скука",
            R.drawable.emotion_12 to "Радость",
            R.drawable.emotion_13 to "Тревога",
            R.drawable.emotion_14 to "Удивление",
            R.drawable.emotion_15 to "Страх",
        )
    }

    val answerOptions = remember {
        // Убедимся, что все верные ответы есть в этом списке
        correctAnswerMap.values.distinct() + listOf("Злость", "Радость", "Спокойствие", "Грусть", "Удивление", "Страх", "Тревога", "Скука") // Добавляем на всякий случай, если какие-то ответы не использовались в map
        // Делаем список уникальным и неизменяемым
        correctAnswerMap.values.union(
            listOf("Злость", "Радость", "Спокойствие", "Грусть", "Удивление", "Страх", "Тревога", "Скука")
        ).toList()
    }

    var currentImageIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var testFinished by remember { mutableStateOf(false) }

    LaunchedEffect(selectedAnswerIndex) {
        if (selectedAnswerIndex != null) {
            delay(700)
            // Проверяем, есть ли еще картинки В ТЕКУЩЕМ СПИСКЕ imageList
            if (currentImageIndex < imageList.size - 1) {
                currentImageIndex++
                selectedAnswerIndex = null
            } else {
                testFinished = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тест: ${level.name.lowercase().replaceFirstChar { it.uppercase() }}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
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
        if (testFinished) {
            EmotionTestResultScreen( // Используем переданный NavController
                navController = navController,
                score = score,
                totalQuestions = imageList.size, // Общее кол-во вопросов = размеру текущего imageList

            )
        } else {
            // Отображаем основной контент теста, используя imageList[currentImageIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Что чувствует человек на картинке?",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Отображение картинки ИЗ ТЕКУЩЕГО imageList
                Image(
                    painter = painterResource(id = imageList[currentImageIndex]),
                    contentDescription = "Эмоция ${currentImageIndex + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Передаем ID ресурса ТЕКУЩЕЙ картинки
                AnswerButtons(
                    answers = answerOptions,
                    selectedAnswerIndex = selectedAnswerIndex,
                    onAnswerSelected = { index, isActuallyCorrect ->
                        if (selectedAnswerIndex == null) {
                            selectedAnswerIndex = index
                            if (isActuallyCorrect) {
                                score++
                            }
                        }
                    },
                    level = level,
                    currentQuestionIndex = currentImageIndex, // Индекс в текущем imageList
                    currentImageResource = imageList[currentImageIndex], // ID ресурса текущей картинки
                    correctAnswerMap = correctAnswerMap // Передаем мапу с верными ответами
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun AnswerButtons(
    answers: List<String>,
    selectedAnswerIndex: Int?,
    onAnswerSelected: (index: Int, isCorrect: Boolean) -> Unit,
    level: DifficultyLevel,
    currentQuestionIndex: Int,
    currentImageResource: Int,
    correctAnswerMap: Map<Int, String>
) {
    val numButtons = when (level) {
        DifficultyLevel.EASY -> 2
        DifficultyLevel.MEDIUM -> 3
        DifficultyLevel.HARD -> 4
    }

    val correctAnswerForImage = correctAnswerMap[currentImageResource] ?: answers.firstOrNull() ?: "Ошибка" // Находим верный ответ

    // Генерируем варианты ответов для ТЕКУЩЕГО вопроса
    val currentAnswers = remember(answers, level, currentQuestionIndex, correctAnswerForImage) {
        val incorrectAnswers = answers
            .filter { it != correctAnswerForImage } // Все ответы, кроме верного
            .shuffled() // Перемешиваем неправильные
            .take(numButtons - 1) // Берем нужное количество неправильных

        // Соединяем верный с неправильными и перемешиваем
        (listOf(correctAnswerForImage) + incorrectAnswers).shuffled()
    }

    // Индекс верного ответа В ПЕРЕМЕШАННОМ списке currentAnswers
    val correctIndexInShuffled = currentAnswers.indexOf(correctAnswerForImage)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentAnswers.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp), // Немного увеличим вертикальный отступ между рядами
                // Центрируем один элемент, распределяем два
                horizontalArrangement = if (rowItems.size == 1) Arrangement.Center else Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally), // Используем spacedBy для отступа между кнопками в ряду, но центрируем ряд
                verticalAlignment = Alignment.CenterVertically,

            ) {
                rowItems.forEach { answerText ->
                    val originalIndex = currentAnswers.indexOf(answerText) // Индекс в списке currentAnswers
                    val isCorrectChoice = (originalIndex == correctIndexInShuffled)

                    val backgroundColor = when {
                        selectedAnswerIndex == null -> MaterialTheme.colorScheme.secondaryContainer // Обычный цвет
                        isCorrectChoice -> Color.Green.copy(alpha = 0.8f) // Верный выбраный
                        originalIndex == selectedAnswerIndex -> Color.Red.copy(alpha = 0.7f) // Неверный выбранный
                        else -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f) // Остальные после выбора
                    }
                    val contentColor = when {
                        selectedAnswerIndex == null -> MaterialTheme.colorScheme.onSecondaryContainer
                        // Белый текст для выделенных (верный/неверный)
                        isCorrectChoice || originalIndex == selectedAnswerIndex -> Color.White
                        // Приглушенный цвет для невыбранных после ответа
                        else -> MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    }

                    Button(
                        onClick = {
                            if (selectedAnswerIndex == null) { // Позволяем выбрать только один раз
                                onAnswerSelected(originalIndex, isCorrectChoice)
                            }
                        },
                        modifier = Modifier
                            // Задаем фиксированную ширину или используем weight, если Row растянут
                            .widthIn(min = 140.dp, max = 200.dp) // Размеры кнопок
                            // Отступы между кнопками управляются Arrangement.spacedBy
                            .height(55.dp), // Можно задать высоту кнопки
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor,
                            contentColor = contentColor
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp) // Внутренние отступы текста
                    ) {
                        Text(
                            answerText,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp, // Размер шрифта
                            lineHeight = 20.sp // Для лучшего переноса строк, если текст длинный
                        )
                    }
                }
            }
        }
    }
}