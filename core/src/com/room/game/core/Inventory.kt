package com.room.game.core

class Inventory(private val uiHandler: StageUiHandler) {

    private var margin = 0f
    private val screenItems = mutableListOf<Pair<InventoryItem, ScreenItem>>()
    private var selectedScreenItem: SelectedScreenItem? = null

    var selectedItem: InventoryItem? = null
        set(value) {
            field = value
            selectedScreenItem?.also {
                uiHandler.removeScreenItem(it)
                selectedScreenItem = null
            }
            screenItems.find { it.first == value }?.also {
                uiHandler.playSound(Sound("ui_button_click.mp3"))
                val selectedScreenItem = SelectedScreenItem(it.second.x, it.second.y)
                uiHandler.addScreenItem(selectedScreenItem)
                this.selectedScreenItem = selectedScreenItem
            }
        }

    fun add(inventoryItem: InventoryItem) {
        addScreenItem(inventoryItem)
    }

    fun remove(inventoryItem: InventoryItem) {
        screenItems.forEach { uiHandler.removeScreenItem(it.second) }
        val itemToRemove = screenItems.find { it.first == inventoryItem }
        screenItems.remove(itemToRemove)

        margin = 0f
        val inventoryItems = screenItems.map { it.first }
        screenItems.clear()
        inventoryItems.forEach { addScreenItem(it) }
    }

    fun hasItem(inventoryItem: InventoryItem): Boolean {
        return screenItems.find { it.first == inventoryItem } != null
    }

    fun redraw() {
        screenItems.forEach {
            uiHandler.addScreenItem(it.second)
        }
        selectedScreenItem?.also {
            uiHandler.addScreenItem(it)
        }
    }

    private fun addScreenItem(element: InventoryItem) {
        margin += 200f
        val screenItem = ScreenItem(element.drawable, 1720f, 1080 - margin, 160f, 160f, element.event)
        screenItems.add(Pair(element, screenItem))
        uiHandler.addScreenItem(screenItem)
    }

    data class SelectedScreenItem(val itemX: Float, val itemY: Float) : ScreenItem(
            drawable = Drawable("item_selected.png"),
            x = itemX - 20,
            y = itemY - 20,
            width = 200f,
            height = 200f,
            event = null
    )
}
