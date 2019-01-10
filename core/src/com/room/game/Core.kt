package com.room.game

import java.util.*

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val screens: LinkedList<Screen>
    val backpack: List<BackpackItem>
    val ui: List<ScreenItem>
    val currentScreen: Screen
}

interface Screen {
    val background: Drawable
    val screenObjects: List<ScreenItem>
    val leftScreen: Screen?
    val rightScreen: Screen?
    val downScreen: Screen?
}

data class ScreenItem(
        val drawable: Drawable,
        val onClick: () -> Unit,
        val x: Float,
        val y: Float
)

interface BackpackItem {
    val drawable: Drawable
}

data class Drawable(val resourceId: String)
