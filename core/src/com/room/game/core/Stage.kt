package com.room.game.core

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val screens: List<Screen>
    val backpack: List<InventoryItem>
    val ui: List<ScreenItem>
    val currentScreen: Screen
}
