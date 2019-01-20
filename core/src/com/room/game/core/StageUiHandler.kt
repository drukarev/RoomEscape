package com.room.game.core

interface StageUiHandler {

    fun addScreenItem(screenItem: ScreenItem)
    fun removeScreenItem(screenItem: ScreenItem)

    fun addTemporaryScreenText(screenText: ScreenText)
    fun addScreenText(screenText: ScreenText)
    fun removeScreenText(screenText: ScreenText)

    fun removeAllScreenElements()

    fun startMusic()
    fun stopMusic()
}
