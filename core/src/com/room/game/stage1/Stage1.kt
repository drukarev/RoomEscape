package com.room.game.stage1

import com.room.game.*
import javafx.collections.ModifiableObservableListBase
import java.util.*

class Stage1(val stageUiHandler: StageUiHandler) : Stage, EventHandler.Listener {
    override val ui: MutableList<ScreenItem> = mutableListOf(
            LeftArrowItem(),
            RightArrowItem())
    override val floor: Screen? = null
    override val ceiling: Screen? = null

    private val whiteBoardScreen = WhiteBoardScreen(null, null, null, stageUiHandler)
    private val snowmanScreen = SnowmanScreen(null, null, null, stageUiHandler)
    private val lockerScreen = LockerScreen(whiteBoardScreen, snowmanScreen, null, stageUiHandler)
    private val workplaceScreen = WorkplaceScreen(snowmanScreen, whiteBoardScreen, null, stageUiHandler)

    init {
        whiteBoardScreen.leftScreen = workplaceScreen
        whiteBoardScreen.rightScreen = lockerScreen

        snowmanScreen.leftScreen = lockerScreen
        snowmanScreen.rightScreen = workplaceScreen
    }

    override val screens = listOf(whiteBoardScreen, lockerScreen, snowmanScreen, workplaceScreen)
    override val backpack: MutableList<InventoryItem> = mutableListOf()

    override var currentScreen: Screen = screens.first()

    private var selectedItem: InventoryItem? = null

    init {
        prepareForCurrentScreen()
    }

    private fun setCurrentScreen(arrowDirection: ArrowDirection) {
        currentScreen = when (arrowDirection) {
            ArrowDirection.LEFT -> currentScreen.leftScreen ?: currentScreen
            ArrowDirection.RIGHT -> currentScreen.rightScreen ?: currentScreen
            ArrowDirection.DOWN -> currentScreen.downScreen ?: currentScreen
        }
        prepareForCurrentScreen()
    }

    private fun prepareForCurrentScreen() {
        stageUiHandler.removeAllScreenItems()
        currentScreen.screenObjects.forEach {
            stageUiHandler.addScreenItem(it)
        }
        ui.forEach { stageUiHandler.addScreenItem(it) }
    }

    override fun onEvent(event: Event) {
        when (event) {

        // Locker screen

            Event.PHONE_FROM_LOCKER_TAKEN -> {
                lockerScreen.screenObjects.remove(LockerScreen.PhoneItem)
                backpack.add(InventoryItem.Phone)
            }

            Event.LOCKER_LOCK_CLICKED -> {
                if (selectedItem == InventoryItem.Key) { //TODO
                    lockerScreen.screenObjects.remove(LockerScreen.LockerDoorClosed)
                } else {
                    showText("Wrong password.")
                }
            }

            Event.LOCKER_CLICKED -> {
                if (lockerScreen.screenObjects.find { it == LockerScreen.LockerDoorClosed } != null) {
                    showText("That's a stand with mobile phones. It is locked.")
                }
            }

        // Workplace screen

            Event.DESK_LOCKER_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Key) {
                    backpack.remove(InventoryItem.Key)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.DeskLockerShelfItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ChargerItem)
                } else {
                    showText("This locker is closed.")
                }
            }

            Event.PHONE_HOLDER_CLICKED -> {
                if (selectedItem == InventoryItem.Phone) {
                    backpack.remove(InventoryItem.Phone)
                    workplaceScreen.screenObjects.remove(WorkplaceScreen.PhoneHolderItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.PhoneHolderWithMobileItem)
                } else {
                    showText("Looks like a phone holder.")
                }
            }

            Event.POWER_SOCKET_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Charger) {
                    backpack.remove(InventoryItem.Charger)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ConnectedChargerItem)
                }
            }

            Event.NOTEBOOK_CLICKED -> {
                if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.ConnectedChargerItem } != null) {
                    //TODO: open notebook
                }
            }

            Event.CHARGER_TAKEN -> {
                workplaceScreen.screenObjects.remove(WorkplaceScreen.ChargerItem)
                backpack.add(InventoryItem.Charger)
            }

            Event.FIX_BUG_BUTTON_CLICKED -> {
                //TODO
            }

        // Snowman screen

            Event.KEY_FROM_SNOWMAN_TAKEN -> {
                snowmanScreen.screenObjects.remove(SnowmanScreen.KeyItem)
                backpack.add(InventoryItem.Key)
            }

            Event.SCREWDRIVER_TAKEN -> {
                snowmanScreen.screenObjects.remove(SnowmanScreen.ScrewdriverItem)
                backpack.add(InventoryItem.Screwdriver)
            }

        // WhiteBoardScreen

            Event.MARKERS_CLICKED -> {
                showText("Hmmmm...")
            }

            Event.LEFT_SCREW_UNSCREWED -> {
                whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.LeftScrewItem)
                if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RightScrewItem } == null) {
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem) //TODO: add broken TV
                }
            }
            Event.RIGHT_SCREW_UNSCREWED -> {
                whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.RightScrewItem)
                if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.LeftScrewItem } == null) {
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem) //TODO: add broken TV
                }
            }

            Event.ROUTER_CLICKED -> {
                whiteBoardScreen.screenObjects.add(WhiteBoardScreen.RouterLightBulbItem)
                showText("Turned wi-fi on.")
            }
            Event.DEPLOY_BUTTON_CLICKED -> {
                if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RouterLightBulbItem } != null) {
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

    LOCKER_LOCK_CLICKED,
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

    class LockerDoorHalfOpened() : ScreenItem(
            drawable = Drawable("icon_phone.jpg"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 0f,
            height = 0f,
            event = null
    )
}

class SnowmanScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(KeyItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("background3.jpg")

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

class WorkplaceScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(PowerSocketItem, PhoneHolderItem, NotebookItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("background4.jpg")

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

class ArrayObservableList<E> : ModifiableObservableListBase<E>() {

    val delegate = ArrayList<E>()

    override val size: Int
        get() = delegate.size

    override fun doRemove(index: Int): E {
        return delegate.removeAt(index)
    }

    override fun doSet(index: Int, element: E): E {
        return delegate.set(index, element)
    }

    override fun doAdd(index: Int, element: E) {
        delegate.add(index, element)
    }

    override fun get(index: Int): E {
        return delegate[index]
    }
}
