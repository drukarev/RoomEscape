package com.room.game.core

interface Stage {
    val screens: List<Screen>
    val inventory: List<InventoryItem>
    val ui: List<ScreenItem>
    val currentScreen: Screen
}
