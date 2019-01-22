package com.room.game.core

interface Stage {
    val screens: List<Screen>
    val inventory: ObservableArrayList<InventoryItem>
    val currentScreen: Screen
}
