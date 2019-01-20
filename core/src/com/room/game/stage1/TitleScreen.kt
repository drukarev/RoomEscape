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
        screenObjects: MutableList<ScreenItem> = mutableListOf(StartGameItem, MusicItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("title_screen_background.jpg")

    object StartGameItem : ScreenItem(
            drawable = Drawable("title_screen_start.jpg"),
            x = 1300f,
            y = 50f,
            width = 439f,
            height = 148f,
            event = Event.START_GAME
    )

    object MusicItem : ScreenItem(
            drawable = Drawable("title_screen_sound.jpg"),
            x = 200f,
            y = 50f,
            width = 162f,
            height = 144f,
            event = Event.ICON_MUSIC_CLICKED
    )
}
