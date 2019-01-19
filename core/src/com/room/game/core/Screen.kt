package com.room.game.core

abstract class Screen(
        var leftScreen: Screen?,
        var rightScreen: Screen?,
        var downScreen: Screen?,
        screenObjectsList: MutableList<ScreenItem>,
        uiHandler: StageUiHandler
) {
    abstract val background: Drawable
    val screenObjects: ObservableArrayList<ScreenItem> = ObservableArrayList<ScreenItem>().apply {
        addAll(screenObjectsList)
        addListener(object : ObservableArrayList.Listener<ScreenItem> {

            override fun onElementAdded(element: ScreenItem) {
                uiHandler.addScreenItem(element)
            }

            override fun onElementRemoved(element: ScreenItem) {
                uiHandler.removeScreenItem(element)
            }
        })
    }
}
