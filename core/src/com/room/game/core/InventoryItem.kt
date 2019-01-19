package com.room.game.core

import com.room.game.stage1.Event

sealed class InventoryItem(val drawable: Drawable, val event: Event) {
    object Key : InventoryItem(Drawable("key.png"), Event.INVENTORY_KEY_CLICKED)
    object Phone : InventoryItem(Drawable("phone.png"), Event.INVENTORY_PHONE_CLICKED)
    object Charger : InventoryItem(Drawable("charger.png"), Event.INVENTORY_CHARGER_CLICKED)
    object Screwdriver : InventoryItem(Drawable("screwdriver.png"), Event.INVENTORY_SCREWDRIVER_CLICKED)
}
