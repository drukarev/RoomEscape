package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class NotebookScreen(
        leftScreen: Screen? = null,
        rightScreen: Screen? = null,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(Background)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("1.jpg"), //TODO: replace
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object FixBugButton : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.FIX_BUG_BUTTON_CLICKED
    )

    object DeployButton : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.DEPLOY_BUTTON_CLICKED
    )
}
