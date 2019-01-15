package com.room.game.core

interface StageUiHandler {
    fun addScreenItem(screenItem: ScreenItem)
    fun removeScreenItem(screenItem: ScreenItem)
    fun removeAllScreenItems()
}
