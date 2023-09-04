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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import com.example.fallingshapes.ui.theme.FallingShapesTheme
import kotlin.math.cos
import kotlin.math.sin
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
    val shapeList = listOf("circle", "triangle", "square", "pentagon", "hexagon")
    val colorList = listOf(Color.Blue, Color.Magenta, Color.Yellow, Color.Green, Color.Red)
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
                    "triangle" -> DrawTriangleShape(shape)
                    "square" -> DrawSquareShape(shape)
                    "pentagon" -> DrawPentagonShape(shape)
                    "hexagon" -> DrawHexagonShape(shape)
                }
            }
            Button(
                onClick = {
                    shapes += Shape(
                        type = shapeList[Random.nextInt(0, shapeList.size)],
                        horizontalOffset = horizontalOffset,
                        verticalOffset = verticalOffset,
                        color = colorList[Random.nextInt(0, colorList.size)]
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
            color = circle.color,
            center = Offset(circle.horizontalOffset, circle.verticalOffset),
            radius = 450F / 2
        )
    }
}

@Composable
private fun DrawTriangleShape(triangle:Shape) {
    var updatedVisible by remember { mutableStateOf(triangle.visible) }

    val shapeSize by animateFloatAsState(
        targetValue = if (updatedVisible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )

    val size = 450F * shapeSize

    LaunchedEffect(triangle) {
        updatedVisible = true
    }
    val trianglePath = Path().apply {
        // Moves to top center position
        moveTo(size / 2f + triangle.horizontalOffset, triangle.verticalOffset)
        // Add line to bottom right corner
        lineTo(size + triangle.horizontalOffset, size + triangle.verticalOffset)
        // Add line to bottom left corner
        lineTo(triangle.horizontalOffset, size +  triangle.verticalOffset)
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scale(shapeSize)
    ) {
        drawPath(path = trianglePath, color = triangle.color)
    }
}

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
            color = square.color,
            topLeft = Offset(square.horizontalOffset, square.verticalOffset),
            size = Size(450F, 450F)
        )
    }
}

@Composable
private fun DrawPentagonShape(pentagon:Shape) {
    var updatedVisible by remember { mutableStateOf(pentagon.visible) }

    val shapeSize by animateFloatAsState(
        targetValue = if (updatedVisible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )

    val size = 450F * shapeSize / 2
    LaunchedEffect(pentagon) {
        updatedVisible = true
    }
    val pentagonPath = Path().apply {
        // Moves to top center position
        moveTo(
            x = size + pentagon.horizontalOffset + (size * cos(0.0)).toFloat(),
            y = size + pentagon.verticalOffset + (size * sin(0.0)).toFloat()
        )
        for (i in 1..4) {
            val angle = 2.0 * Math.PI / 5
            lineTo(
                x = size + pentagon.horizontalOffset + (size * cos(angle * i)).toFloat(),
                y = size + pentagon.verticalOffset + (size * sin(angle * i)).toFloat()
            )
        }
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scale(shapeSize)
    ) {
        drawPath(path = pentagonPath, color = pentagon.color)
    }
}

@Composable
private fun DrawHexagonShape(hexagon:Shape) {
    var updatedVisible by remember { mutableStateOf(hexagon.visible) }

    val shapeSize by animateFloatAsState(
        targetValue = if (updatedVisible) 0F else 1F, label = "",
        animationSpec = tween(1000)
    )

    val size = 450F * shapeSize / 2
    LaunchedEffect(hexagon) {
        updatedVisible = true
    }
    val hexagonPath = Path().apply {
        // Moves to top center position
        moveTo(
            x = size + hexagon.horizontalOffset + (size * cos(0.0)).toFloat(),
            y = size + hexagon.verticalOffset + (size * sin(0.0)).toFloat()
        )
        for (i in 1..5) {
            val angle = 2.0 * Math.PI / 6
            lineTo(
                x = size + hexagon.horizontalOffset + (size * cos(angle * i)).toFloat(),
                y = size + hexagon.verticalOffset + (size * sin(angle * i)).toFloat()
            )
        }
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scale(shapeSize)
    ) {
        drawPath(path = hexagonPath, color = hexagon.color)
    }
}
data class Shape(
    val type: String,
    var visible: Boolean = false,
    val horizontalOffset: Float = 0F,
    val verticalOffset: Float = 0F,
    val color: Color
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FallingShapesTheme {
        FallingShapes()
    }
}