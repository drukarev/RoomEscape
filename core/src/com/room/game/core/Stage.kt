package com.room.game.core

interface Stage {
    val screens: List<Screen>
    val inventory: Inventory
    val currentScreen: Screen
    val musicOn: Boolean
}
