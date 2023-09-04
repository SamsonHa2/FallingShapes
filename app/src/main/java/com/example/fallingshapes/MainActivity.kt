package com.example.fallingshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.fallingshapes.ui.theme.FallingShapesTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FallingShapesTheme {

                FallingShapes()
            }
        }
    }
}

@Composable
fun FallingShapes() {
    var shapes by remember { mutableStateOf(emptyList<Shape>()) }
    val shapeList = listOf("circle", "square")
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val horizontalOffset =  Random.nextFloat() * 1000
            val verticalOffset =  Random.nextFloat() * 2000
            for (shape in shapes) {
                when (shape.type) {
                    "circle" -> DrawCircleShape(shape)
                    "square" -> DrawSquareShape(shape)
                }
            }
            Button(
                onClick = {
                    shapes += Shape(
                        type = shapeList[Random.nextInt(0, shapeList.size)],
                        horizontalOffset = horizontalOffset,
                        verticalOffset = verticalOffset
                    )
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = "Trigger")
            }
        }
    }
}
@Composable
private fun DrawCircleShape(circle:Shape) {
    var updatedVisible by remember { mutableStateOf(circle.visible) }

    val shapeSize by animateFloatAsState(
        targetValue = if (updatedVisible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )
    LaunchedEffect(circle) {
        updatedVisible = true
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scale(shapeSize)
    ) {
        drawCircle(
            color = Color.Red,
            center = Offset(circle.horizontalOffset, circle.verticalOffset),
            radius = 450F / 2
        )
    }
}
data class Shape(
    val type: String,
    var visible: Boolean = false,
    val horizontalOffset: Float = 0F,
    val verticalOffset: Float = 0F
)
@Composable
private fun DrawSquareShape(square:Shape) {
    var updatedVisible by remember { mutableStateOf(square.visible) }

    val shapeSize by animateFloatAsState(
        targetValue = if (updatedVisible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )
    LaunchedEffect(square) {
        updatedVisible = true
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scale(shapeSize)
    ) {
        drawRect(
            color = Color.Blue,
            topLeft = Offset(square.horizontalOffset, square.verticalOffset),
            size = Size(450F, 450F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FallingShapesTheme {
        FallingShapes()
    }
}