package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.viewmodel.MainViewModel


private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    private lateinit var rootPath: String
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootPath = filesDir.path
        setContent {
            Screen {
                mainViewModel.handleButtonClick(rootPath)
            }
        }
    }
}

@Composable
fun Screen(buttonClickHandler: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { buttonClickHandler() }) {
            Text(text = "Check files")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Screen{ }
}