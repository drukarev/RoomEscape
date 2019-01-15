package com.room.game.core

import com.room.game.stage1.Event

sealed class InventoryItem(val drawable: Drawable, val event: Event) {
    object Key : InventoryItem(Drawable("arrow_down.png"), Event.INVENTORY_KEY_CLICKED)
    object Phone : InventoryItem(Drawable("icon_phone.jpg"), Event.INVENTORY_PHONE_CLICKED)
    object Charger : InventoryItem(Drawable("arrow_right.png"), Event.INVENTORY_CHARGER_CLICKED)
    object Screwdriver : InventoryItem(Drawable("arrow_right.png"), Event.INVENTORY_SCREWDRIVER_CLICKED)
}
