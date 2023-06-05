package com.example.hallo.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hallo.models.ButtonComposable
import com.example.hallo.ui.composables.ButtonComponent
import com.example.hallo.ui.theme.BackgroundDark


@Composable
fun TestScreen(
) {
    Column(
        modifier = Modifier
            .background(BackgroundDark)
            .fillMaxSize()
    ) {
        fun test() {
            Log.d("TESTING", "Hey man")
        }
        ButtonComponent(
            button = ButtonComposable(
                onClick = {test()}
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TestScreen()
}