package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class WhiteBoardScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(TvItem, LeftScrewItem, RightScrewItem, RouterItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("background1.jpg")

    object LeftScrewItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.LEFT_SCREW_UNSCREWED
    )

    object RightScrewItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.RIGHT_SCREW_UNSCREWED
    )

    object TvItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.PHONE_FROM_LOCKER_TAKEN
    )

    object RouterItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.ROUTER_CLICKED
    )

    object RouterLightBulbItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = null
    )

    object Markers : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),  //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.MARKERS_CLICKED
    )
}
