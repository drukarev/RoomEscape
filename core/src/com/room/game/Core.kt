package com.room.game

import java.util.*

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val walls: LinkedList<Screen>
    val backpack: MutableList<BackpackItem>

    val currentScreen: Int
}

interface Screen {
    val background: Drawable
    val screenObjects: List<ScreenItem>
}

interface ScreenItem {
    val drawable: Drawable
    fun onClick()
}

interface BackpackItem {
    val drawable: Drawable
}

class Drawable(val resourceId: String)
