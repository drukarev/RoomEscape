package com.room.game

import com.room.game.stage1.Event
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

open class ScreenItem(
        val drawable: Drawable,
        val x: Float,
        val y: Float,
        val width: Float,
        val height: Float,
        val event: Event?
)

sealed class InventoryItem(val drawable: Drawable, val event: Event) {
    object Key : InventoryItem(Drawable("arrow_down.png"), Event.INVENTORY_KEY_CLICKED)
    object Phone : InventoryItem(Drawable("icon_phone.jpg"), Event.INVENTORY_PHONE_CLICKED)
    object Charger : InventoryItem(Drawable("arrow_right.png"), Event.INVENTORY_CHARGER_CLICKED)
    object Screwdriver : InventoryItem(Drawable("arrow_right.png"), Event.INVENTORY_SCREWDRIVER_CLICKED)
}

data class Drawable(val resourceId: String)
