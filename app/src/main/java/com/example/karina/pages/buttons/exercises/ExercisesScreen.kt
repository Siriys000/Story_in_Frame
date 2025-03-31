package com.example.karina.pages.buttons.ExercisesScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.karina.R
import com.example.karina.ui.theme.Purple80
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseSelectionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Выбор упражнения") },
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
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ExerciseItem(
                title = "Поход в магазин",
                description = "Упражнение на тренировку внимания и памяти. Необходимо пройти историю похода в магазин, запоминая детали.",
                onClick = { navController.navigate("shoppingExercise") } // Navigate to the Shopping Exercise
            )
            // Add more ExerciseItems here as needed
        }
    }
}

@Composable
fun ExerciseItem(title: String, description: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            shape = RoundedCornerShape(4.dp), // More rectangular shape
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        var isExpanded by remember { mutableStateOf(false) } // State to manage expansion

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(16.dp)
                .clickable { isExpanded = !isExpanded } // Toggle expansion on click
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = if (!isExpanded) 2 else Int.MAX_VALUE, // Limit lines when collapsed
                overflow = TextOverflow.Ellipsis // Add ellipsis for overflow
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShoppingExerciseScreen(navController: NavController) { // Renamed from ExercisesScreen
    val pagerState = rememberPagerState(pageCount = { 3 })
    val images = listOf(
        R.drawable.exercise_1_1,
        R.drawable.exercise_1_2,
        // Intentionally left empty for the third page, no image resource provided
    )
    val topTitles = listOf(
        "Куда идут мама и ребенок? Зачем им нужен список?",
        "Что делают мама и ребенок в магазине? Какие продукты они выбирают?",
        "Поход в магазин" // for placeholder page
    )
    val explanationTexts = listOf(
        """Мама и ребенок идут в магазин за продуктами. Посмотри, у них есть список покупок. Сначала нужно составить список, чтобы ничего не забыть.

Для родителей: Объясните ребенку, зачем нужен список покупок. Обсудите, какие продукты обычно покупают в магазине. Можно показать ребенку свой список покупок, если он есть.""",
        """В магазине много разных продуктов! Мама и ребенок ищут то, что написано в списке. Они выбирают фрукты, овощи, молоко...

Для родителей: Опишите процесс выбора продуктов в магазине. Обратите внимание на разнообразие продуктов. Спросите ребенка, какие продукты он знает и любит."""
    )

    var isExplanationDialogVisible by remember { mutableStateOf(false) }
    var currentExplanationText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Упражнение: Поход в магазин") }, // More specific title
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
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = topTitles.getOrElse(pagerState.currentPage) { "Поход в магазин" }, // Fallback title
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                if (page < images.size) {
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = "Exercise Image ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                    )
                } else {
                    // Custom Placeholder for the third page
                    ExercisePlaceholder(onPlaceholderClick = {
                        navController.navigate("exerciseComplete") // Navigate on placeholder click - generic completion
                    })
                }
            }

            Spacer(modifier = Modifier.height(4.dp)) // Reduced spacer height

            // Индикатор (точки) - самодельный
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            if (pagerState.currentPage < 2) { // Show explanation text for first two pages
                Button( // Changed from TextButton to Button
                    onClick = {
                        currentExplanationText = explanationTexts[pagerState.currentPage]
                        isExplanationDialogVisible = true
                    },
                    colors = ButtonDefaults.buttonColors( // Set button colors
                        containerColor = Purple80,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Нажми, чтобы увидеть пояснение")
                }
            }
            // "Нажми, чтобы завершить историю" text, appearing only on the last page
            if (pagerState.currentPage == 2) { // Index 2 is the last page (0, 1, 2)
                Text(
                    text = "Нажми на изображение, чтобы завершить историю",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(24.dp)) // Keep some space for consistent layout
            }
        }
    }

    if (isExplanationDialogVisible) {
        ExplanationDialog(text = currentExplanationText) {
            isExplanationDialogVisible = false
        }
    }
}

@Composable
fun ExplanationDialog(text: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
            )
        }
    }
}



@Composable
fun ExercisePlaceholder(onPlaceholderClick: () -> Unit) {
    val cornerRadius = 8.dp
    val strokeWidth = 3.dp
    val dashLength = 10f
    val gapLength = 10f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onPlaceholderClick() }
            .padding(32.dp)
            .background(Color.LightGray, RoundedCornerShape(cornerRadius)) // Apply background with shape
            .drawBehind { // Use drawBehind for custom border drawing
                val stroke = Stroke(
                    width = strokeWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
                )
                val radius = cornerRadius.toPx()

                // Draw the rounded rectangle border
                drawRoundRect(
                    color = Color.Black,
                    topLeft = androidx.compose.ui.geometry.Offset.Zero, // Start drawing from top-left
                    size = size, // Use the full size of the Box after padding
                    cornerRadius = CornerRadius(radius, radius),
                    style = stroke
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "?",
            fontSize = 90.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCompleteScreen(navController: NavController) {
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    val isCorrectSelection = selectedAnswerIndex == 0

    val initialText = "Посмотри на две картинки. Что происходит? Какая картинка показывает правильное продолжение истории - 1 или 2? Выбери правильный вариант и расскажи, почему ты так думаешь."
    val correctText = """Продукты выбрали! Теперь нужно заплатить за них на кассе и сложить в пакеты. Вот и все покупки сделаны!

Для родителей: Опишите завершающий этап похода в магазин - оплата и упаковка продуктов. Обсудите, что происходит на кассе."""
    val incorrectText = """Ой! А здесь ребенок ведет себя плохо в магазине. Он бегает, кричит и мешает другим. Так делать нельзя!

Для родителей: Обсудите, почему такое поведение в магазине неприемлемо. Объясните правила поведения в общественных местах. Спросите ребенка, видел ли он когда-нибудь такое поведение и как он к нему относится."""

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Завершение упражнения") },
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
                .background(Color.White)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedAnswerIndex == null) {
                Text(
                    text = initialText,
                    fontSize = (MaterialTheme.typography.headlineMedium.fontSize.value - 10).sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            } else {
                Text(
                    text = if (isCorrectSelection) "Верно!" else "Неверно!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = if (isCorrectSelection) Color.Green else Color.Red,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Первый вариант ответа (правильный)
            AnswerItem(
                imageResId = R.drawable.exercise_1_answer_1,
                index = 0,
                selectedAnswerIndex = selectedAnswerIndex,
                isCorrectAnswer = true,
                onClick = { if (selectedAnswerIndex == null) selectedAnswerIndex = 0 }
            )

            Spacer(modifier = Modifier.height(16.dp)) // Отступ между вариантами

            // Второй вариант ответа (неправильный)
            AnswerItem(
                imageResId = R.drawable.exercise_1_answer_2,
                index = 1,
                selectedAnswerIndex = selectedAnswerIndex,
                isCorrectAnswer = false,
                onClick = { if (selectedAnswerIndex == null) selectedAnswerIndex = 1 }
            )

            if (selectedAnswerIndex != null) {
                Spacer(modifier = Modifier.height(24.dp)) // Space before explanation text
                Text(
                    text = if (isCorrectSelection) correctText else incorrectText,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for text
                )
            }
        }
    }
}

@Composable
fun AnswerItem(
    imageResId: Int,
    index: Int,
    selectedAnswerIndex: Int?,
    isCorrectAnswer: Boolean,
    onClick: () -> Unit
) {
    val isSelected = selectedAnswerIndex == index
    val isOtherSelected = selectedAnswerIndex != null && !isSelected
    // val selectionWasCorrect = selectedAnswerIndex == 0 // Not needed here anymore

    val highlightColor = when {
        isSelected && isCorrectAnswer -> Color.Green.copy(alpha = 0.5f)
        isSelected && !isCorrectAnswer -> Color.Red.copy(alpha = 0.5f)
        else -> Color.Transparent
    }
    val shape = RoundedCornerShape(8.dp)
    val borderWidth = 8.dp

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(200.dp)
            .clip(shape)
            .clickable(enabled = selectedAnswerIndex == null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Removed duplicated "Верно!/Неверно!" Text here

        val painter = painterResource(id = imageResId)
        val density = LocalDensity.current

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val effectiveMaxWidthPx = with(density) { maxWidth.toPx() } - 2 * with(density) { borderWidth.toPx() }
            val effectiveMaxHeightPx = with(density) { maxHeight.toPx() } - 2 * with(density) { borderWidth.toPx() }

            val safeEffectiveMaxWidthPx = maxOf(0f, effectiveMaxWidthPx)
            val safeEffectiveMaxHeightPx = maxOf(0f, effectiveMaxHeightPx)

            val imageSizeAndOffset = remember(painter.intrinsicSize, maxWidth, maxHeight, borderWidth) {
                calculateFitImageSizeAndOffset(
                    painter.intrinsicSize,
                    Size(safeEffectiveMaxWidthPx, safeEffectiveMaxHeightPx)
                )
            }
            val (imageSizePx, _) = imageSizeAndOffset

            if (imageSizePx != Size.Zero) {
                val borderSizePx = Size(
                    imageSizePx.width + 2 * with(density) { borderWidth.toPx() },
                    imageSizePx.height + 2 * with(density) { borderWidth.toPx() }
                )
                val borderSizeDp = with(density) { Size(borderSizePx.width.toDp().value, borderSizePx.height.toDp().value) }
                val imageSizeDp = with(density) { Size(imageSizePx.width.toDp().value, imageSizePx.height.toDp().value) }

                Box(
                    modifier = Modifier
                        .size(width = borderSizeDp.width.dp, height = borderSizeDp.height.dp)
                        .background(highlightColor, shape)
                )

                Image(
                    painter = painter,
                    contentDescription = "Answer Option ${index + 1}",
                    modifier = Modifier
                        .size(width = imageSizeDp.width.dp, height = imageSizeDp.height.dp)
                        .clip(shape),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Spacer(Modifier.fillMaxSize())
            }
        }
    }
}

/**
 * Рассчитывает конечный размер и смещение изображения при масштабировании ContentScale.Fit.
 * @param intrinsicSize Оригинальные размеры изображения (Painter.intrinsicSize).
 * @param containerSize Размеры контейнера, в который вписывается изображение.
 * @return Pair<Size, Offset> - Рассчитанные размеры (в пикселях) и смещение (в пикселях) верхнего левого угла изображения относительно контейнера.
 */
fun calculateFitImageSizeAndOffset(intrinsicSize: Size, containerSize: Size): Pair<Size, Offset> {
    if (intrinsicSize == Size.Unspecified || intrinsicSize.width <= 0f || intrinsicSize.height <= 0f || containerSize.width <= 0f || containerSize.height <= 0f) {
        return Pair(Size.Zero, Offset.Zero)
    }

    val imageWidth = intrinsicSize.width
    val imageHeight = intrinsicSize.height
    val containerWidth = containerSize.width
    val containerHeight = containerSize.height

    val widthRatio = containerWidth / imageWidth
    val heightRatio = containerHeight / imageHeight
    val scale = min(widthRatio, heightRatio) // Определяем масштабный коэффициент

    val scaledWidth = imageWidth * scale
    val scaledHeight = imageHeight * scale

    // Рассчитываем смещение для центрирования
    val offsetX = (containerWidth - scaledWidth) / 2f
    val offsetY = (containerHeight - scaledHeight) / 2f

    return Pair(Size(scaledWidth, scaledHeight), Offset(offsetX, offsetY))
}