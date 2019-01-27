package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class SnowmanScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(Background, BlohajItem, BalanceboardItem, SnowmanItem, KeyItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("snowman_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object KeyItem : ScreenItem(
            drawable = Drawable("snowman_screen_key.png"),
            x = 792f,
            y = 500f,
            width = 72f,
            height = 86f,
            event = Event.KEY_FROM_SNOWMAN_CLICKED
    )

    object BlohajItem : ScreenItem(
            drawable = Drawable("snowman_screen_blohaj.png"),
            x = 175f,
            y = 390f,
            width = 594f,
            height = 618f,
            event = Event.BLOHAJ_CLICKED
    )

    object SnowmanItem : ScreenItem(
            drawable = Drawable("snowman_screen_snowman.png"),
            x = 800f,
            y = 100f,
            width = 1003f,
            height = 800f,
            event = Event.SNOWMAN_CLICKED
    )

    object BalanceboardItem : ScreenItem(
            drawable = Drawable("snowman_screen_balanceboard.png"),
            x = 180f,
            y = 60f,
            width = 719f,
            height = 347f,
            event = Event.BALANCEBOARD_CLICKED
    )

    companion object {
        var alreadyTookScrewdriver = false
    }
}
