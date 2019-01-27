package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class EndScreen(
        leftScreen: Screen? = null,
        rightScreen: Screen? = null,
        downScreen: Screen? = null,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(Background)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("end_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )
}
