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
        screenObjects: MutableList<ScreenItem> = mutableListOf(
                Background, PhoneItem, InnerTabletItem, LockerDoorClosed, TabletItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("locker_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object PhoneItem : ScreenItem(
            drawable = Drawable("locker_screen_phone.png"),
            x = 1000f,
            y = 208f,
            width = 134f,
            height = 123f,
            event = Event.PHONE_IN_LOCKER_CLICKED
    )

    object TabletItem : ScreenItem(
            drawable = Drawable("locker_screen_lock.png"),
            x = 760f,
            y = 404f,
            width = 309f,
            height = 244f,
            event = Event.TABLET_CLICKED
    )

    object InnerTabletItem : ScreenItem(
            drawable = Drawable("locker_screen_inner_tablet.png"),
            x = 755f,
            y = 393f,
            width = 336f,
            height = 234f,
            event = Event.INNER_TABLET_CLICKED
    )

    object LockerDoorClosed : ScreenItem(
            drawable = Drawable("locker_screen_door.png"),
            x = 384f,
            y = 0f,
            width = 1058f,
            height = 948f,
            event = Event.LOCKER_DOOR_CLICKED
    )
}
