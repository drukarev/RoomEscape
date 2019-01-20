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
        screenObjects: MutableList<ScreenItem> = mutableListOf(BlohajItem, SnowmanItem, KeyItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("snowman_screen_background.jpg")

    object KeyItem : ScreenItem(
            drawable = Drawable("snowman_screen_key.png"),
            x = 800f,
            y = 430f,
            width = 72f,
            height = 86f,
            event = Event.KEY_FROM_SNOWMAN_CLICKED
    )

    object BlohajItem : ScreenItem(
            drawable = Drawable("snowman_screen_blohaj.png"),
            x = 110f,
            y = 100f,
            width = 612f,
            height = 636f,
            event = Event.BLOHAJ_CLICKED
    )

    object SnowmanItem : ScreenItem(
            drawable = Drawable("snowman_screen_snowman.png"),
            x = 800f,
            y = 40f,
            width = 1050f,
            height = 828f,
            event = Event.SNOWMAN_CLICKED
    )
}
