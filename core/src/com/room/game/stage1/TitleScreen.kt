package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class TitleScreen(
        leftScreen: Screen? = null,
        rightScreen: Screen? = null,
        downScreen: Screen? = null,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(Background, StartGameItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("title_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object StartGameItem : ScreenItem(
            drawable = Drawable("title_screen_start.jpg"),
            x = 1300f,
            y = 50f,
            width = 439f,
            height = 148f,
            event = Event.START_GAME
    )
}
