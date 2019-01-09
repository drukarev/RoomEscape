package com.room.game

import java.util.*

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val walls: LinkedList<Screen>
    val backpack: List<BackpackItem>
    val ui: List<ScreenItem>
    val currentScreen: Int
}

interface Screen {
    val background: Drawable
    val screenObjects: List<ScreenItem>
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
