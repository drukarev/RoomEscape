package com.room.game.core

import javafx.collections.ListChangeListener

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
