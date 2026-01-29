package com.freedom.quilterandroidtechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.freedom.book_list.BookListScreen
import com.freedom.designsystem.theme.QuilterAndroidTechTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuilterAndroidTechTestTheme {
                val backStack = rememberNavBackStack(AppDestination.BookList)
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<AppDestination.BookList> {
                            BookListScreen()
                        }
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuilterAndroidTechTestTheme {

    }
}