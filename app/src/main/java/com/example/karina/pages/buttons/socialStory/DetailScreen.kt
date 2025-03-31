package com.example.karina.pages.buttons.socialStory

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

// Data class для хранения частей текста для одной страницы
data class StoryPageContent(
    val mainText: String,
    val parentText: String,
    val taskText: AnnotatedString // Используем AnnotatedString только здесь для "Задание:"
)

// Список объектов StoryPageContent
val storyPagesContent = listOf(
    StoryPageContent(
        mainText = "Вот мальчик. Он хочет гулять! Он взял свою любимую игрушку.",
        parentText = "Для родителей: Начните обсуждение с вопроса: \"Куда собирается мальчик? Что он взял с собой?\" Обсудите, что чувствует мальчик, возможно, он рад или немного волнуется.\"",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"Как ты думаешь, что мальчик сейчас сделает? Он пойдет играть с другими детьми или останется дома?\"")
        }
    ),
    StoryPageContent(
        mainText = "Мальчик пришел на площадку и говорит: \"Привет!\" Другие дети приветствуют его в ответ. Это вежливо и приятно!",
        parentText = "Для родителей: Объясните важность приветствия при встрече. Обсудите, почему важно здороваться.",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"Что говорят дети, когда встречаются? Покажи, как ты здороваешься!\"")
        }
    ),
    StoryPageContent(
        mainText = "Дети играют вместе в мяч. Играть с друзьями весело. Посмотри, они смеются и радуются!",
        parentText = "Для родителей: Опишите совместную игру как позитивный опыт. Обратите внимание на эмоции детей на картинке: радость, веселье. Обсудите, какие игры бывают на площадке и какие игры ребенок любит.",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"В какую игру играют дети? Ты любишь играть в мяч? С кем ты любишь играть?\"")
        }
    ),
    StoryPageContent(
        mainText = "У мальчика есть интересная игрушка. Другой мальчик смотрит на нее. Мальчик делится своей игрушкой!",
        parentText = "Для родителей: Подчеркните важность умения делиться. Обсудите, что значит \"делиться\" и почему это важно для дружбы. Спросите ребенка, делится ли он своими игрушками и как он себя при этом чувствует.",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"Что делает мальчик со своей игрушкой? Почему делиться игрушками - это хорошо?\"")
        }
    ),
    StoryPageContent(
        mainText = "Другой мальчик поиграл и возвращает игрушку. Он говорит: \"Спасибо, было интересно!\" Надо говорить \"спасибо\", когда тебе помогают или делятся с тобой.",
        parentText = "Для родителей: Обратите внимание на слова благодарности. Объясните, что \"спасибо\" - это вежливое слово. Попросите ребенка вспомнить, когда он говорил \"спасибо\" и за что.",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"Что говорит мальчик, когда ему возвращают игрушку? Когда мы говорим спасибо?\"")
        }
    ),
    StoryPageContent(
        mainText = "День закончился, пора домой. Дети говорят друг другу \"Пока! До завтра!\" Прощаться тоже важно, когда уходишь.",
        parentText = "Для родителей: Объясните, что прощание показывает, что мы были рады общению и надеемся на новую встречу.",
        taskText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Задание:")
            }
            append(" \"Как прощаются дети? Покажи, как ты прощаешься! С кем ты прощаешься, когда идешь домой с прогулки?\"")
        }
    )
)
