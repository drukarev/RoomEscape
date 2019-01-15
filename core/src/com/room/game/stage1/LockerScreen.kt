package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class LockerScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(PhoneItem, LockerDoorClosed)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("background2.jpg")

    object PhoneItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),
            x = 1000f,
            y = 200f,
            width = 500f,
            height = 500f,
            event = Event.PHONE_FROM_LOCKER_TAKEN
    )

    object LockItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.LOCKER_LOCK_CLICKED
    )

    object LockerDoorClosed : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 0f,
            height = 0f,
            event = Event.LOCKER_CLICKED
    )

    object LockerDoorHalfOpened : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 0f,
            height = 0f,
            event = null
    )
}
