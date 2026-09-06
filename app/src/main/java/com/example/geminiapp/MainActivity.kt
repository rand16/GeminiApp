package com.example.geminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.geminiapp.ui.theme.GeminiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiAppTheme {
                val topLevelBackStack = remember { TopLevelBackStack<Any>(OnDeviceAiRoute) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            TOP_LEVEL_ROUTES.forEach { route ->
                                val isSelected = route == topLevelBackStack.topLevelKey
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { topLevelBackStack.addTopLevel(route) },
                                    label = { Text(route.label) },
                                    icon = {
                                        Icon(
                                            imageVector = route.icon,
                                            contentDescription = route.label
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<OnDeviceAiRoute> {
                                OnDeviceAiScreen()
                            }
                            entry<CloudAiRoute> {
                                CloudAiScreen()
                            }
                            entry<AppFunctionsRoute> {
                                AppFunctionsScreen()
                            }
                        }
                    )
                }
            }
        }
    }
}
