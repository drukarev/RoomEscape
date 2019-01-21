package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class WorkplaceScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> =
                mutableListOf(Background, PowerSocketItem, PhoneHolderItem, NotebookItem, DeskLockerItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("workplace_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object PowerSocketItem : ScreenItem(
            drawable = Drawable("workplace_screen_power_socket.png"),
            x = 50f,
            y = 550f,
            width = 82f,
            height = 92f,
            event = Event.POWER_SOCKET_ITEM_CLICKED
    )

    object PhoneHolderItem : ScreenItem(
            drawable = Drawable("workplace_screen_phone_holder.png"),
            x = 1000f,
            y = 400f,
            width = 156f,
            height = 183f,
            event = Event.PHONE_HOLDER_CLICKED
    )

    object PhoneHolderWithMobileItem : ScreenItem(
            drawable = Drawable("workplace_screen_connected_phone.png"),
            x = 820f,
            y = 380f,
            width = 313f,
            height = 241f,
            event = Event.CONNECTED_PHONE_CLICKED
    )

    object NotebookItem : ScreenItem(
            drawable = Drawable("workplace_screen_notebook.png"),
            x = 330f,
            y = 370f,
            width = 582f,
            height = 459f,
            event = Event.NOTEBOOK_CLICKED
    )

    object NotebookOnItem : ScreenItem(
            drawable = Drawable("workplace_screen_notebook_on.png"),
            x = 330f,
            y = 450f,
            width = 582f,
            height = 459f,
            event = Event.NOTEBOOK_CLICKED
    )

    object ConnectedChargerItem : ScreenItem(
            drawable = Drawable("workplace_screen_connected_charger.png"),
            x = 30f,
            y = 450f,
            width = 429f,
            height = 230f,
            event = null
    )

    object DeskLockerItem : ScreenItem(
            drawable = Drawable("workplace_screen_locker.png"),
            x = 1190f,
            y = 0f,
            width = 549f,
            height = 354f,
            event = Event.DESK_LOCKER_ITEM_CLICKED
    )

    object DeskLockerShelfItem : ScreenItem(
            drawable = Drawable("workplace_screen_locker_box.png"),
            x = 1300f,
            y = 0f,
            width = 540f,
            height = 312f,
            event = null
    )

    object ChargerItem : ScreenItem(
            drawable = Drawable("workplace_screen_charger.png"),
            x = 1450f,
            y = 130f,
            width = 160f,
            height = 158f,
            event = Event.CHARGER_CLICKED
    )
}
