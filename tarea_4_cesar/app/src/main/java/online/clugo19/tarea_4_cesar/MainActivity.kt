package online.clugo19.tarea_4_cesar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.clugo19.tarea_4_cesar.ui.theme.Tarea_4_cesarTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea_4_cesarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Multiplication", "Factorial", "Guessing Game", "Text Conversion")

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when (selectedTab) {
            0 -> MultiplicationTableScreen()
            1 -> FactorialScreen()
            2 -> GuessingGameScreen()
            3 -> TextConversionScreen()
        }
    }
}

@Composable
fun MultiplicationTableScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Enter a number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val num = number.toIntOrNull()
            if (num != null) {
                result = (1..12).joinToString("\n") { "$num x $it = ${num * it}" }
            } else {
                result = "Please enter a valid number"
            }
        }) {
            Text("Generate Table")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(result)
    }
}

@Composable
fun FactorialScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Enter a positive number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val num = number.toIntOrNull()
            result = when {
                num == null -> "Please enter a valid number"
                num < 0 -> "Please enter a positive number"
                else -> {
                    val factorial = calculateFactorial(num)
                    "Factorial of $num = $factorial"
                }
            }
        }) {
            Text("Calculate Factorial")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(result)
    }
}

@Composable
fun GuessingGameScreen() {
    val secretNumber = remember { Random.nextInt(1, 11) }
    var guess by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Guess the number (1-10)")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = guess,
            onValueChange = { guess = it },
            label = { Text("Enter your guess") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val num = guess.toIntOrNull()
            result = when {
                num == null -> "Please enter a valid number"
                num < 1 || num > 10 -> "Please enter a number between 1 and 10"
                num == secretNumber -> "Correct! You guessed the number!"
                num < secretNumber -> "Too low! Try a higher number."
                else -> "Too high! Try a lower number."
            }
        }) {
            Text("Submit Guess")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(result)
    }
}

@Composable
fun TextConversionScreen() {
    var text by remember { mutableStateOf("") }
    var upperCase by remember { mutableStateOf("") }
    var lowerCase by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter a phrase") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            upperCase = text.uppercase()
            lowerCase = text.lowercase()
        }) {
            Text("Convert Text")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Uppercase: $upperCase")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Lowercase: $lowerCase")
    }
}

private fun calculateFactorial(n: Int): Long {
    return if (n == 0 || n == 1) 1
    else n * calculateFactorial(n - 1)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Tarea_4_cesarTheme {
        MainScreen()
    }
}