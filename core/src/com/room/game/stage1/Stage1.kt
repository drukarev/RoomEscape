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

        // Locker screen

            Event.PHONE_FROM_LOCKER_TAKEN -> {
                LockerScreen.screenObjects.remove(LockerScreen.PhoneItem)
                backpack.add(InventoryItem.Phone)
            }

            Event.LOCKER_TRIED_TO_OPEN -> {
                if (selectedItem == InventoryItem.Key) { //TODO
                    LockerScreen.screenObjects.remove(LockerScreen.LockerDoorClosed)
                } else {
                    showText("Wrong password.")
                }
            }

            Event.LOCKER_CLICKED -> {
                if (LockerScreen.screenObjects.find { it == LockerScreen.LockerDoorClosed } != null) {
                    showText("That's a stand with mobile phones. It is locked.")
                }
            }

        // Workplace screen

            Event.DESK_LOCKER_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Key) {
                    backpack.remove(InventoryItem.Key)
                    WorkplaceScreen.screenObjects.add(WorkplaceScreen.DeskLockerShelfItem)
                    WorkplaceScreen.screenObjects.add(WorkplaceScreen.ChargerItem)
                } else {
                    showText("This locker is closed.")
                }
            }

            Event.PHONE_HOLDER_CLICKED -> {
                if (selectedItem == InventoryItem.Phone) {
                    backpack.remove(InventoryItem.Phone)
                    WorkplaceScreen.screenObjects.remove(WorkplaceScreen.PhoneHolderItem)
                    WorkplaceScreen.screenObjects.add(WorkplaceScreen.PhoneHolderWithMobileItem)
                } else {
                    showText("Looks like a phone holder.")
                }
            }

            Event.POWER_SOCKET_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Charger) {
                    backpack.remove(InventoryItem.Charger)
                    WorkplaceScreen.screenObjects.add(WorkplaceScreen.ConnectedChargerItem)
                }
            }

            Event.NOTEBOOK_CLICKED -> {
                if (WorkplaceScreen.screenObjects.find { it == WorkplaceScreen.ConnectedChargerItem } != null) {
                    //TODO: open notebook
                }
            }

            Event.CHARGER_TAKEN -> {
                WorkplaceScreen.screenObjects.remove(WorkplaceScreen.ChargerItem)
                backpack.add(InventoryItem.Charger)
            }

            Event.FIX_BUG_BUTTON_CLICKED -> {
                //TODO
            }

        // Snowman screen

            Event.KEY_FROM_SNOWMAN_TAKEN -> {
                SnowmanScreen.screenObjects.remove(SnowmanScreen.KeyItem)
                backpack.add(InventoryItem.Key)
            }

            Event.SCREWDRIVER_TAKEN -> {
                WorkplaceScreen.screenObjects.remove(SnowmanScreen.ScrewdriverItem)
                backpack.add(InventoryItem.Screwdriver)
            }

        // WhiteBoardScreen

            Event.MARKERS_CLICKED -> {
                showText("Hmmmm...")
            }

            Event.LEFT_SCREW_UNSCREWED -> {
                WhiteBoardScreen.screenObjects.remove(WhiteBoardScreen.LeftScrewItem)
                if (WhiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RightScrewItem } == null) {
                    WhiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem) //TODO: add broken TV
                }
            }
            Event.RIGHT_SCREW_UNSCREWED -> {
                WhiteBoardScreen.screenObjects.remove(WhiteBoardScreen.RightScrewItem)
                if (WhiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.LeftScrewItem } == null) {
                    WhiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem) //TODO: add broken TV
                }
            }

            Event.ROUTER_CLICKED -> {
                WhiteBoardScreen.screenObjects.add(WhiteBoardScreen.RouterLightBulbItem)
                showText("Turned wi-fi on.")
            }
            Event.DEPLOY_BUTTON_CLICKED -> {
                if (WhiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RouterLightBulbItem } != null) {
                    showText("Congratulations!")
                }
            }

        // UI buttons

            Event.LEFT_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.LEFT)
            Event.RIGHT_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.RIGHT)
            Event.DOWN_ARROW_CLICKED -> setCurrentScreen(ArrowDirection.DOWN)

        // Inventory items

            Event.INVENTORY_PHONE_CLICKED -> selectedItem = InventoryItem.Phone
            Event.INVENTORY_KEY_CLICKED -> selectedItem = InventoryItem.Key
            Event.INVENTORY_SCREWDRIVER_CLICKED -> selectedItem = InventoryItem.Screwdriver
            Event.INVENTORY_CHARGER_CLICKED -> selectedItem = InventoryItem.Charger
        }
        selectedItem = null
    }

    fun showText(text: String) {

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

    class DownArrowItem() : ScreenItem(
            drawable = Drawable("arrow_down.png"),
            x = 1830f,
            y = 530f,
            width = 80f,
            height = 80f,
            event = Event.DOWN_ARROW_CLICKED
    )
}

enum class ArrowDirection {
    LEFT,
    RIGHT,
    DOWN
}

enum class Event {
    SCREWDRIVER_TAKEN,
    KEY_FROM_SNOWMAN_TAKEN,
    PHONE_FROM_LOCKER_TAKEN,
    CHARGER_TAKEN,

    LOCKER_TRIED_TO_OPEN,
    PHONE_HOLDER_CLICKED,
    FIX_BUG_BUTTON_CLICKED,
    LEFT_SCREW_UNSCREWED,
    RIGHT_SCREW_UNSCREWED,
    ROUTER_CLICKED,
    DEPLOY_BUTTON_CLICKED,

    LOCKER_CLICKED,
    POWER_SOCKET_ITEM_CLICKED,
    NOTEBOOK_CLICKED,
    DESK_LOCKER_ITEM_CLICKED,
    MARKERS_CLICKED,

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
    override val screenObjects: MutableList<ScreenItem> = mutableListOf(TvItem, LeftScrewItem, RightScrewItem, RouterItem)

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

object LockerScreen : Screen {
    override val leftScreen: Screen = WhiteBoardScreen
    override val rightScreen: Screen = SnowmanScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background2.jpg")
    override val screenObjects: MutableList<ScreenItem> = mutableListOf(PhoneItem, LockerDoorClosed)

    object PhoneItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"),
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.PHONE_FROM_LOCKER_TAKEN
    )

    object LockItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.LOCKER_TRIED_TO_OPEN
    )

    object LockerDoorClosed : ScreenItem(
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
    override val screenObjects: MutableList<ScreenItem> = mutableListOf(KeyItem)

    object KeyItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.KEY_FROM_SNOWMAN_TAKEN
    )

    object ScrewdriverItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.SCREWDRIVER_TAKEN
    )
}

object WorkplaceScreen : Screen {
    override val leftScreen: Screen = SnowmanScreen
    override val rightScreen: Screen = WhiteBoardScreen
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background4.jpg")
    override val screenObjects: MutableList<ScreenItem> = mutableListOf(PowerSocketItem, PhoneHolderItem, NotebookItem)

    object PowerSocketItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.POWER_SOCKET_ITEM_CLICKED
    )

    object PhoneHolderItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.PHONE_HOLDER_CLICKED
    )

    object PhoneHolderWithMobileItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = null
    )

    object NotebookItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NOTEBOOK_CLICKED
    )

    object ConnectedChargerItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = null
    )

    object DeskLockerItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.DESK_LOCKER_ITEM_CLICKED
    )

    object DeskLockerShelfItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = null
    )

    object ChargerItem : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.CHARGER_TAKEN
    )
}
