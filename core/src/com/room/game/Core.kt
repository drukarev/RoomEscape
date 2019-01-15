package com.room.game

import com.room.game.stage1.ArrayObservableList
import com.room.game.stage1.Event
import javafx.collections.ListChangeListener

interface Stage {
    val floor: Screen?
    val ceiling: Screen?
    val screens: List<Screen>
    val backpack: List<InventoryItem>
    val ui: List<ScreenItem>
    val currentScreen: Screen
}

interface StageUiHandler {
    fun addScreenItem(screenItem: ScreenItem)
    fun removeScreenItem(screenItem: ScreenItem)
    fun removeAllScreenItems()
}

abstract class Screen(
        var leftScreen: Screen?,
        var rightScreen: Screen?,
        var downScreen: Screen?,
        screenObjectsList: MutableList<ScreenItem>,
        uiHandler: StageUiHandler
) {
    abstract val background: Drawable
    val screenObjects: ArrayObservableList<ScreenItem> = ArrayObservableList<ScreenItem>().apply {
        addAll(screenObjectsList)
        addListener(ListChangeListener {
            it.next()
            if (it.wasRemoved()) {
                it.removed.forEach {
                    uiHandler.removeScreenItem(it)
                }
            } else if (it.wasAdded()) {
                it.addedSubList.forEach {
                    uiHandler.addScreenItem(it)
                }
            }
        })
    }
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
