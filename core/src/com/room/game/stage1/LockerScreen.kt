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
        screenObjects: MutableList<ScreenItem> = mutableListOf(PhoneItem, LockerDoorClosed, TabletItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("locker_screen_background.jpg")

    object PhoneItem : ScreenItem(
            drawable = Drawable("locker_screen_phone.png"),
            x = 1000f,
            y = 200f,
            width = 134f,
            height = 123f,
            event = Event.PHONE_IN_LOCKER_CLICKED
    )

    object TabletItem : ScreenItem(
            drawable = Drawable("locker_screen_lock.png"),
            x = 835f,
            y = 200f,
            width = 202f,
            height = 278f,
            event = Event.TABLET_CLICKED
    )

    object LockerDoorClosed : ScreenItem(
            drawable = Drawable("locker_screen_door.png"),
            x = 360f,
            y = 0f,
            width = 1104f,
            height = 960f,
            event = Event.LOCKER_DOOR_CLICKED
    )
}
