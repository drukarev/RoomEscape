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

    override val background: Drawable = Drawable("background_title_screen.jpg")

    object StartGameItem : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.START_GAME
    )

    object MusicItem : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 100f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.ICON_MUSIC_CLICKED
    )
}
