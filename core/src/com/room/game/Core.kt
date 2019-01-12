package com.room.game

import java.util.*

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val screens: LinkedList<Screen>
    val backpack: List<InventoryItem>
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
        val y: Float,
        val width: Float,
        val height: Float
)

sealed class InventoryItem(val drawable: Drawable) {
    object Key1 : InventoryItem(Drawable("arrow_down.png"))
    object Phone : InventoryItem(Drawable("icon_phone.jpg"))
    object Key3 : InventoryItem(Drawable("arrow_right.png"))
}

data class Drawable(val resourceId: String)
