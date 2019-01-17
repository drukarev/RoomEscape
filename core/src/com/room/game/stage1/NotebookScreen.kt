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
        screenObjects: MutableList<ScreenItem> = mutableListOf()
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("background3.jpg")

    object FixBugButton : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.FIX_BUG_BUTTON_CLICKED
    )

    object DeployButton : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.DEPLOY_BUTTON_CLICKED
    )

    object NoInternetItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = null
    )

    object RefreshButton : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.REFRESH_BUTTON_CLICKED
    )
}
