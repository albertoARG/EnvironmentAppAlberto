package com.example.environmentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.environmentapp.ui.theme.EnvironmentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnvironmentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        MainScreen(
                            onNavigateToSecond = { startActivity(Intent(this@MainActivity, SecondActivity::class.java)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(onNavigateToSecond: () -> Unit) {
    Button(onClick = onNavigateToSecond) {
        Text("GO TO SECOND ACTIVITY")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EnvironmentAppTheme {
        MainScreen {}
    }
}
