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
                mutableListOf(Background, PowerSocketItem, PhoneHolderItem, NotebookItem, DeskLockerItem, DeployButtonItem)
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
            drawable = Drawable("workplace_screen_power_socket.jpg"),
            x = 90f,
            y = 560f,
            width = 69f,
            height = 64f,
            event = Event.POWER_SOCKET_ITEM_CLICKED
    )

    object PhoneHolderItem : ScreenItem(
            drawable = Drawable("workplace_screen_phone_holder.jpg"),
            x = 1044f,
            y = 458f,
            width = 134f,
            height = 154f,
            event = Event.PHONE_HOLDER_CLICKED
    )

    object PhoneHolderWithMobileItem : ScreenItem(
            drawable = Drawable("workplace_screen_connected_phone.png"),
            x = 873f,
            y = 440f,
            width = 290f,
            height = 204f,
            event = Event.CONNECTED_PHONE_CLICKED
    )

    object NotebookItem : ScreenItem(
            drawable = Drawable("workplace_screen_notebook.png"),
            x = 405f,
            y = 380f,
            width = 514f,
            height = 430f,
            event = Event.NOTEBOOK_CLICKED
    )

    object NotebookOnItem : ScreenItem(
            drawable = Drawable("workplace_screen_notebook_on.png"),
            x = 332f,
            y = 542f,
            width = 682f,
            height = 278f,
            event = Event.NOTEBOOK_CLICKED
    )

    object NotebookWithBugFixedItem : ScreenItem(
            drawable = Drawable("workplace_screen_commit_made.png"),
            x = 332f,
            y = 542f,
            width = 682f,
            height = 278f,
            event = Event.NOTEBOOK_CLICKED
    )

    object ConnectedChargerItem : ScreenItem(
            drawable = Drawable("workplace_screen_connected_charger.png"),
            x = 94f,
            y = 474f,
            width = 373f,
            height = 172f,
            event = null
    )

    object DeskLockerItem : ScreenItem(
            drawable = Drawable("workplace_screen_locker.png"),
            x = 1199f,
            y = 0f,
            width = 537f,
            height = 342f,
            event = Event.DESK_LOCKER_ITEM_CLICKED
    )

    object DeskLockerShelfItem : ScreenItem(
            drawable = Drawable("workplace_screen_locker_box.png"),
            x = 1310f,
            y = 15f,
            width = 494f,
            height = 262f,
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

    object DeployButtonItem : ScreenItem(
            drawable = Drawable("workplace_screen_deploy_button.png"),
            x = 1250f,
            y = 436f,
            width = 274f,
            height = 250f,
            event = Event.DEPLOY_BUTTON_CLICKED
    )
}
