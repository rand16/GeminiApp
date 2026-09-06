package com.example.geminiapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface TopLevelRoute : NavKey {
    val icon: ImageVector
    val label: String
}

@Serializable
data object OnDeviceAiRoute : TopLevelRoute {
    override val icon = Icons.Default.AutoAwesome
    override val label = "On-Device AI"
}

@Serializable
data object CloudAiRoute : TopLevelRoute {
    override val icon = Icons.Default.Cloud
    override val label = "Cloud AI"
}

@Serializable
data object AppFunctionsRoute : TopLevelRoute {
    override val icon = Icons.Default.SettingsSuggest
    override val label = "AppFunctions"
}

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(OnDeviceAiRoute, CloudAiRoute, AppFunctionsRoute)

/**
 * Multiple backstacks manager for Navigation 3.
 */
class TopLevelBackStack<T : Any>(startKey: T) {
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() {
        backStack.clear()
        backStack.addAll(topLevelStacks.flatMap { it.value })
    }

    fun addTopLevel(key: T) {
        if (topLevelStacks[key] == null) {
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            topLevelStacks.remove(key)?.let {
                topLevelStacks[key] = it
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val stack = topLevelStacks[topLevelKey]
        if (stack != null && stack.size > 1) {
            stack.removeAt(stack.size - 1)
        } else {
            // If it's the last element in the current top level stack, 
            // we might want to switch back to the start route or exit.
            if (topLevelStacks.size > 1) {
                topLevelStacks.remove(topLevelKey)
                topLevelKey = topLevelStacks.keys.last()
            }
        }
        updateBackStack()
    }
}
