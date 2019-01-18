package com.room.game.stage1

import com.room.game.core.*

class Stage1(val stageUiHandler: StageUiHandler) : Stage, EventHandler.Listener {
    override val ui: MutableList<ScreenItem> = mutableListOf(
            LeftArrowItem(),
            RightArrowItem())
    override val floor: Screen? = null
    override val ceiling: Screen? = null

    private val titleScreen = TitleScreen(uiHandler = stageUiHandler)
    private val whiteBoardScreen = WhiteBoardScreen(null, null, null, stageUiHandler)
    private val snowmanScreen = SnowmanScreen(null, null, null, stageUiHandler)
    private val lockerScreen = LockerScreen(whiteBoardScreen, snowmanScreen, null, stageUiHandler)
    private val workplaceScreen = WorkplaceScreen(snowmanScreen, whiteBoardScreen, null, stageUiHandler)
    private val tabletScreen = TabletScreen(uiHandler = stageUiHandler, downScreen = lockerScreen)
    private val notebookScreen = NotebookScreen(uiHandler = stageUiHandler, downScreen = workplaceScreen)

    init {
        whiteBoardScreen.leftScreen = workplaceScreen
        whiteBoardScreen.rightScreen = lockerScreen

        snowmanScreen.leftScreen = lockerScreen
        snowmanScreen.rightScreen = workplaceScreen
    }

    override val screens = listOf(whiteBoardScreen, lockerScreen, snowmanScreen, workplaceScreen)
    override val backpack: MutableList<InventoryItem> = mutableListOf()

    override var currentScreen: Screen = titleScreen

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
        stageUiHandler.removeAllScreenElements()
        currentScreen.screenObjects.forEach {
            stageUiHandler.addScreenItem(it)
        }
        ui.forEach { stageUiHandler.addScreenItem(it) }
    }

    override fun onEvent(event: Event) {
        when (event) {

            // Title screen

            Event.START_GAME -> {
                currentScreen = screens.first()
            }
            Event.ICON_MUSIC_CLICKED -> {
                //TODO
            }
            Event.ICON_SOUND_CLICKED -> {
                //TODO
            }

            // Locker screen

            Event.PHONE_FROM_LOCKER_TAKEN -> {
                lockerScreen.screenObjects.remove(LockerScreen.PhoneItem)
                backpack.add(InventoryItem.Phone)
            }

            Event.TABLET_CLICKED -> {
                currentScreen = tabletScreen
            }

            Event.LOCKER_CLICKED -> {
                if (lockerScreen.screenObjects.find { it == LockerScreen.LockerDoorClosed } != null) {
                    showTemporaryText("That's a stand with mobile phones. It is locked.")
                }
            }

            // Tablet screen

            Event.NUMBER_1_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('1'))
            }
            Event.NUMBER_2_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('2'))
            }
            Event.NUMBER_3_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('3'))
            }
            Event.NUMBER_4_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('4'))
            }
            Event.NUMBER_5_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('5'))
            }
            Event.NUMBER_6_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('6'))
            }
            Event.NUMBER_7_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('7'))
            }
            Event.NUMBER_8_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('8'))
            }
            Event.NUMBER_9_BUTTON_CLICKED -> {
                showTemporaryText(tabletScreen.getPasscodeText('9'))
            }

            // TODO: add action for successful code verification

            // Workplace screen

            Event.DESK_LOCKER_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Key) {
                    backpack.remove(InventoryItem.Key)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.DeskLockerShelfItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ChargerItem)
                } else {
                    showTemporaryText("This locker is closed.")
                }
            }

            Event.PHONE_HOLDER_CLICKED -> {
                if (selectedItem == InventoryItem.Phone) {
                    backpack.remove(InventoryItem.Phone)
                    workplaceScreen.screenObjects.remove(WorkplaceScreen.PhoneHolderItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.PhoneHolderWithMobileItem)
                } else {
                    showTemporaryText("Looks like a phone holder.")
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
                    currentScreen = notebookScreen
                } else {
                    showTemporaryText("This notebook is out of charge")
                }
            }

            Event.CHARGER_TAKEN -> {
                workplaceScreen.screenObjects.remove(WorkplaceScreen.ChargerItem)
                backpack.add(InventoryItem.Charger)
            }

            // Notebook screen

            Event.FIX_BUG_BUTTON_CLICKED -> {
                if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RouterLightBulbItem } != null) {
                    notebookScreen.screenObjects.add(NotebookScreen.DeployButton)
                } else {
                    notebookScreen.screenObjects.add(NotebookScreen.RefreshButton)
                    notebookScreen.screenObjects.add(NotebookScreen.NoInternetItem)
                }
                notebookScreen.screenObjects.remove(NotebookScreen.FixBugButton)

            }

            Event.REFRESH_BUTTON_CLICKED -> {
                if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RouterLightBulbItem } != null) {
                    notebookScreen.screenObjects.remove(NotebookScreen.NoInternetItem)
                    notebookScreen.screenObjects.remove(NotebookScreen.RefreshButton)
                    notebookScreen.screenObjects.add(NotebookScreen.DeployButton)
                }
            }

            Event.DEPLOY_BUTTON_CLICKED -> {
                showTemporaryText("Congratulations!")
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
                showTemporaryText("Hmmmm...")
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
                showTemporaryText("Turned wi-fi on.")
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

    private fun showTemporaryText(text: String) {
        stageUiHandler.addTemporaryScreenText(ScreenText(text))
    }

    private fun showTemporaryText(screenText: ScreenText) { //TODO this screenText should remain on screen without fade out
        stageUiHandler.addTemporaryScreenText(screenText)
    }

    private fun showScreenItem(screenItem: ScreenItem) {
        stageUiHandler.addScreenItem(screenItem)
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
