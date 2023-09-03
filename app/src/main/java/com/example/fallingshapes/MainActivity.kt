package com.example.fallingshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.fallingshapes.ui.theme.FallingShapesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FallingShapesTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FallingShapes()
                }
            }
        }
    }
}

@Composable
fun FallingShapes() {
    var visible by remember { mutableStateOf(false) }
    val shapeAlpha by animateFloatAsState(
        targetValue = if (visible) 1F else 0F, label = "",
        animationSpec = tween(1000)
    )
    val shapeSize by animateFloatAsState(
        targetValue = if (visible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )
    Box(
        modifier = Modifier.fillMaxSize(0.1F).scale(shapeSize).alpha(shapeAlpha).background(Color.Red)
            .clickable {
                visible = !visible
            }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FallingShapesTheme {
        FallingShapes()
    }
}