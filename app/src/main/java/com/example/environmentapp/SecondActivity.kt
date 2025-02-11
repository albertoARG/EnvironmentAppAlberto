package com.example.environmentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.environmentapp.ui.theme.EnvironmentAppTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnvironmentAppTheme {
                SecondScreen(
                    onNavigateToThird = { startActivity(Intent(this, ThirdActivity::class.java)) },
                    onNavigateToMain = { finish() }
                )
            }
        }
    }
}

@Composable
fun SecondScreen(onNavigateToThird: () -> Unit, onNavigateToMain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onNavigateToThird) {
            Text("GO TO THIRD ACTIVITY")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onNavigateToMain) {
            Text("GO TO MAIN ACTIVITY")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    EnvironmentAppTheme {
        SecondScreen({}, {})
    }
}