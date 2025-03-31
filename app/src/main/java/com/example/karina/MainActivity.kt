package com.example.karina

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.karina.pages.buttons.ExercisesScreen.ExerciseCompleteScreen
import com.example.karina.pages.buttons.ExercisesScreen.ExerciseSelectionScreen
import com.example.karina.pages.buttons.ExercisesScreen.ShoppingExerciseScreen
import com.example.karina.pages.buttons.socialStory.DetailScreen
import com.example.karina.pages.buttons.visualTip.EmotionTestScreen
import com.example.karina.pages.buttons.visualTip.VisualTipScreen
import com.example.karina.ui.theme.KarinaTheme


class MainActivity : ComponentActivity() {
    private val TAG = "STORY_IN_FRAMES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KarinaTheme {
                // Создаем NavController
                val navController = rememberNavController()

                // Используем NavHost
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.story_in_frame_background),
                                    contentDescription = "Background Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                ButtonScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    tag = TAG,
                                    navController = navController
                                ) // Передаем navController
                            }
                        }
                    }
                    composable("detail") { // Маршрут для DetailScreen
                        DetailScreen(navController = navController)
                    }
                    composable("exerciseSelection") {
                        ExerciseSelectionScreen(navController = navController)
                    }
                    composable("shoppingExercise") { // Route for Shopping Exercise (renamed)
                        ShoppingExerciseScreen(navController = navController)
                    }
                    composable("exerciseComplete") {
                        ExerciseCompleteScreen(navController = navController)
                    }
                    composable("visualTip") { // Новый маршрут
                        VisualTipScreen(navController = navController)
                    }
                    composable(
                        "emotionTest/{level}", // Маршрут с параметром
                        arguments = listOf(navArgument("level") { type = NavType.StringType })
                    )
                    { backStackEntry ->
                        EmotionTestScreen(
                            navController = navController,
                            difficultyLevel = backStackEntry.arguments?.getString("level")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonScreen(modifier: Modifier = Modifier, tag: String, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val verticalSpacing = screenHeight * 0.1f

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Добавляем onClick для перехода
        MyButton(
            text = stringResource(R.string.button_social_stories),
            tag = tag,
            onClick = { navController.navigate("detail") }
        ) // Переход на "detail"
        Spacer(modifier = Modifier.height(verticalSpacing))
        MyButton(
            text = stringResource(R.string.button_visual_cues),
            tag = tag,
            onClick = { navController.navigate("visualTip") }
        )
        Spacer(modifier = Modifier.height(verticalSpacing))
        MyButton(
            text = stringResource(R.string.button_exercises),
            tag = tag,
            onClick = { navController.navigate("exerciseSelection") }
        )
    }
}

@Composable
fun MyButton(text: String, tag: String, onClick: (() -> Unit)? = null) { // Добавляем onClick
    Button(
        onClick = {
            onClick?.invoke() ?: Log.d(tag, "$text clicked")
        },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(70.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface,
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KarinaTheme {
        val navController = rememberNavController() // Создаем NavController для Preview
        //  ButtonScreen(tag = "STORY_IN_FRAMES", navController = navController)
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.story_in_frame_background),
                            contentDescription = "Background Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        ButtonScreen(
                            modifier = Modifier.padding(innerPadding),
                            tag = "STORY_IN_FRAMES",
                            navController = navController
                        ) // Передаем navController
                    }
                }
            }
            composable("detail") { // Маршрут для DetailScreen
                DetailScreen(navController = navController)
            }
        }
    }
}