package com.room.game.stage1

import com.room.game.*
import com.room.game.InventoryItem.Key
import com.room.game.InventoryItem.Phone
import java.util.*

class Stage1 : Stage, EventHandler.Listener {
    override val ui: MutableList<ScreenItem> = mutableListOf(
            LeftArrowItem(),
            RightArrowItem())
    override val floor: Screen? = null
    override val ceiling: Screen? = null
    override val screens: LinkedList<Screen> = LinkedList(listOf(WhiteBoardScreen, LockerScreen, SnowmanScreen, WorkplaceScreen))
    override val backpack: MutableList<InventoryItem> = mutableListOf(Key, Phone)

    override var currentScreen: Screen = screens.first

    private var selectedItem: InventoryItem? = null

    private fun setCurrentScreen(arrowDirection: ArrowDirection) {
        currentScreen = when (arrowDirection) {
            ArrowDirection.LEFT -> currentScreen.leftScreen ?: currentScreen
            ArrowDirection.RIGHT -> currentScreen.rightScreen ?: currentScreen
            ArrowDirection.DOWN -> currentScreen.downScreen ?: currentScreen
        }
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.LOCKER_TRIED_TO_OPEN -> TODO()
            Event.PHONE_FROM_LOCKER_TAKEN -> TODO()
            Event.PHONE_PUT_ON_HOLDER -> TODO()
            Event.KEY_FROM_SNOWMAN_TAKEN -> TODO()
            Event.DESK_LOCKER_OPENED -> TODO()
            Event.CHARGER_TAKEN -> TODO()
            Event.CHARGER_CONNECTED -> TODO()
            Event.FIX_BUG_BUTTON_CLICKED -> TODO()
            Event.SCREWDRIVER_TAKEN -> TODO()
            Event.LEFT_SCREW_UNSCREWED -> TODO()
            Event.RIGHT_SCREW_UNSCREWED -> TODO()
            Event.ROUTER_TURNED_ON -> TODO()
            Event.DEPLOY_BUTTON_CLICKED -> TODO()

            Event.LOCKER_CLICKED -> TODO()
            Event.POWER_SOCKET_ITEM_CLICKED -> TODO()
            Event.PHONE_STAND_ITEM_CLICKED -> TODO()
            Event.NOTEBOOK_CLICKED -> TODO()

            Event.LEFT_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.LEFT)
            Event.RIGHT_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.RIGHT)
            Event.DOWN_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.DOWN)

            Event.INVENTORY_PHONE_CLICKED -> TODO()
            Event.INVENTORY_KEY_CLICKED -> TODO()
            Event.INVENTORY_SCREWDRIVER_CLICKED -> TODO()
            Event.INVENTORY_CHARGER_CLICKED -> TODO()
        }
    }

    class LeftArrowItem : ScreenItem(
            drawable = Drawable("arrow_left.png"),
            x = 50f,
            y = 530f,
            width = 80f,
            height = 80f,
            event = Event.LEFT_ARROW_CLICKED)

    class RightArrowItem : ScreenItem(
            drawable = Drawable("arrow_right.png"),
            x = 1830f,
            y = 530f,
            width = 80f,
            height = 80f,
            event = Event.RIGHT_ARROW_CLICKED
    )

//    class DownArrowItem() : ScreenItem(
//            drawable = Drawable("arrow_down.png"),
//            onClick = { onClickAction() },
//            x = 1830f,
//            y = 530f,
//            width = 80f,
//            height = 80f,
//            event = Event.DOWN_ARROW_CLICKED
//    )
}

enum class ArrowDirection {
    LEFT,
    RIGHT,
    DOWN
}

enum class Event {
    LOCKER_TRIED_TO_OPEN,
    PHONE_FROM_LOCKER_TAKEN,
    PHONE_PUT_ON_HOLDER,
    KEY_FROM_SNOWMAN_TAKEN,
    DESK_LOCKER_OPENED,
    CHARGER_TAKEN,
    CHARGER_CONNECTED,
    FIX_BUG_BUTTON_CLICKED,
    SCREWDRIVER_TAKEN,
    LEFT_SCREW_UNSCREWED,
    RIGHT_SCREW_UNSCREWED,
    ROUTER_TURNED_ON,
    DEPLOY_BUTTON_CLICKED,

    LOCKER_CLICKED,
    POWER_SOCKET_ITEM_CLICKED,
    PHONE_STAND_ITEM_CLICKED,
    NOTEBOOK_CLICKED,

    LEFT_ARROW_CLICKED,
    RIGHT_ARROW_CLICKED,
    DOWN_ARROW_CLICKED,

    INVENTORY_PHONE_CLICKED,
    INVENTORY_KEY_CLICKED,
    INVENTORY_SCREWDRIVER_CLICKED,
    INVENTORY_CHARGER_CLICKED
}

class EventHandler(val listeners: List<Listener>) {

    fun handleEvent(event: Event) {
        listeners.forEach {
            it.onEvent(event)
        }
    }

    interface Listener {
        fun onEvent(event: Event)
    }
}

object WhiteBoardScreen : Screen {
    override val leftScreen: Screen = WorkplaceScreen
    override val rightScreen: Screen = LockerScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background1.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

object LockerScreen : Screen {
    override val leftScreen: Screen = WhiteBoardScreen
    override val rightScreen: Screen = SnowmanScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background2.jpg")
    override val screenObjects: List<ScreenItem> = listOf(PhoneItem(), LockerDoorClosed())

    class PhoneItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.PHONE_FROM_LOCKER_TAKEN
    )

    class LockItem() : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.LOCKER_TRIED_TO_OPEN
    )

    class LockerDoorClosed() : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 0f,
            height = 0f,
            event = Event.LOCKER_CLICKED
    )

    class LockerDoorHalfOpened() : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 0f,
            height = 0f,
            event = null
    )
}

object SnowmanScreen : Screen {
    override val leftScreen: Screen = LockerScreen
    override val rightScreen: Screen = WorkplaceScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background3.jpg")
    override val screenObjects: List<ScreenItem> = listOf(KeyItem())

    class KeyItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.KEY_FROM_SNOWMAN_TAKEN
    )
}

object WorkplaceScreen : Screen {
    override val leftScreen: Screen = SnowmanScreen
    override val rightScreen: Screen = WhiteBoardScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background4.jpg")
    override val screenObjects: List<ScreenItem> = listOf(PowerSocketItem(), PhoneStandItem(), NotebookItem())

    class PowerSocketItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.POWER_SOCKET_ITEM_CLICKED
    )

    class PhoneStandItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.PHONE_STAND_ITEM_CLICKED
    )

    class NotebookItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NOTEBOOK_CLICKED
    )
}
