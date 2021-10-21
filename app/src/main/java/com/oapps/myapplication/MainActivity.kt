package com.oapps.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oapps.myapplication.ui.theme.MyApplicationTheme

private const val TAG = "Main"

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    AppContent()
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @Composable
    private fun AppContent() {
        Column {
            var number by remember {
                mutableStateOf("")
            }

            Text(text = number, fontSize = 36.sp, modifier = Modifier.padding(32.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                modifier = Modifier
                    .width(240.dp)
                    .aspectRatio(1f),

                ) {
                items(9) {
                    CalcButton(num = it) {
                        number += it.toString()
                    }
                }
            }

            var firstNumber by remember {
                mutableStateOf<Int?>(null)
            }

            var operation: Char? by remember { mutableStateOf(null) }

            Row {
                IconButton(onClick = {
                    Log.d(TAG, "onCreate: you clicked add")
                    firstNumber = number.toInt()
                    number = ""
                    operation = '+'
                    Log.d(TAG, "operation = $operation, 1st = $firstNumber")
                }, modifier = Modifier.background(Color.Cyan)) {
                    Icon(Icons.Default.Add, "")
                }
                IconButton(onClick = {
                    Log.d(TAG, "onCreate: you clicked minus")
                    firstNumber = number.toInt()
                    number = ""
                    operation = '-'
                    Log.d(TAG, "operation = $operation, 1st = $firstNumber")

                }, modifier = Modifier.background(Color.Cyan)) {
                    Text("-")
                }

                firstNumber?.let { first ->
                    IconButton(onClick = {
                        val secondNumber = number.toInt()

                        val ans = if (operation == '+') {
                            first + secondNumber
                        } else {
                            first - secondNumber
                        }
                        Log.d(
                            TAG,
                            "operation = $operation, 1st = $firstNumber, 2nd = $secondNumber, ans = $ans"
                        )

                        number = ans.toString()
                    }, modifier = Modifier.background(Color.Cyan)) {
                        Text("=")
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CalcButton(num: Int, onClick: () -> Unit) {
    val context = LocalContext.current
    Surface(shape = CircleShape, color = Color.Blue, modifier = Modifier
        .aspectRatio(1f), onClick = {
        onClick()
        Log.d(TAG, "Clicked $num")
        Toast
            .makeText(context, "You clicked $num", Toast.LENGTH_SHORT)
            .show()
    }) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text("$num", color = Color.White)
        }
    }

//    OutlinedButton(onClick = { /*TODO*/ }, shape = CircleShape) {
//        Text("$num")
//    }
}
