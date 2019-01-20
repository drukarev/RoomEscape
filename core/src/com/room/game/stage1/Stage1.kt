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
    override val inventory: MutableList<InventoryItem> = mutableListOf()

    override var currentScreen: Screen = titleScreen
        set(value) {
            field = value
            prepareForCurrentScreen()
        }

    private var selectedItem: InventoryItem? = null

    private var musicOn = true
        set(value) {
            if (value) {
                stageUiHandler.startMusic()
            } else {
                stageUiHandler.stopMusic()
            }
            field = value
        }

    init {
        prepareForCurrentScreen()
    }

    private fun setCurrentScreen(arrowDirection: ArrowDirection) {
        currentScreen = when (arrowDirection) {
            ArrowDirection.LEFT -> currentScreen.leftScreen ?: currentScreen
            ArrowDirection.RIGHT -> currentScreen.rightScreen ?: currentScreen
            ArrowDirection.DOWN -> currentScreen.downScreen ?: currentScreen
        }
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
                musicOn = !musicOn
            }

            // Locker screen

            Event.PHONE_IN_LOCKER_CLICKED -> {
                lockerScreen.screenObjects.remove(LockerScreen.PhoneItem)
                inventory.add(InventoryItem.Phone)
            }

            Event.TABLET_CLICKED -> {
//                currentScreen = tabletScreen
                lockerScreen.screenObjects.remove(LockerScreen.LockerDoorClosed)
                lockerScreen.screenObjects.remove(LockerScreen.TabletItem)
            }

            Event.LOCKER_DOOR_CLICKED -> {
                if (lockerScreen.screenObjects.find { it == LockerScreen.LockerDoorClosed } != null) {
                    showTemporaryText("That's a stand with mobile phones. It is locked.")
                }
            }

            // Tablet screen

            Event.NUMBER_1_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('1'))
            }
            Event.NUMBER_2_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('2'))
            }
            Event.NUMBER_3_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('3'))
            }
            Event.NUMBER_4_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('4'))
            }
            Event.NUMBER_5_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('5'))
            }
            Event.NUMBER_6_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('6'))
            }
            Event.NUMBER_7_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('7'))
            }
            Event.NUMBER_8_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('8'))
            }
            Event.NUMBER_9_BUTTON_CLICKED -> {
                showText(tabletScreen.getPasscodeText('9'))
            }

            // TODO: add action for successful code verification

            // Workplace screen

            Event.DESK_LOCKER_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Key) {
//                    inventory.remove(InventoryItem.Key)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.DeskLockerShelfItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ChargerItem)
                } else {
                    showTemporaryText("This locker is closed.")
                }
            }

            Event.PHONE_HOLDER_CLICKED -> {
                if (selectedItem == InventoryItem.Phone) {
//                    inventory.remove(InventoryItem.Phone)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.PhoneHolderWithMobileItem)
                } else {
                    showTemporaryText("Looks like a phone holder.")
                }
            }

            Event.POWER_SOCKET_ITEM_CLICKED -> {
                if (selectedItem == InventoryItem.Charger) {
//                    inventory.remove(InventoryItem.Charger)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ConnectedChargerItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.NotebookOnItem)
                }
            }

            Event.NOTEBOOK_CLICKED -> {
                if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.ConnectedChargerItem } != null) {
                    currentScreen = notebookScreen
                } else {
                    showTemporaryText("This notebook is out of charge")
                }
            }

            Event.CONNECTED_PHONE_CLICKED -> {
                showTemporaryText("Ready for debug")
            }

            Event.CHARGER_CLICKED -> {
                workplaceScreen.screenObjects.remove(WorkplaceScreen.ChargerItem)
                inventory.add(InventoryItem.Charger)
            }

            // Notebook screen

            Event.FIX_BUG_BUTTON_CLICKED -> {
                notebookScreen.screenObjects.add(NotebookScreen.DeployButton)
                notebookScreen.screenObjects.remove(NotebookScreen.FixBugButton)

            }

            Event.DEPLOY_BUTTON_CLICKED -> {
                showTemporaryText("Congratulations!")
            }

            // Snowman screen

            Event.KEY_FROM_SNOWMAN_CLICKED -> {
                snowmanScreen.screenObjects.remove(SnowmanScreen.KeyItem)
                inventory.add(InventoryItem.Key)
            }

            Event.BLOHAJ_CLICKED -> {
                val alreadyHasScrewdriver = inventory.find { it == InventoryItem.Screwdriver } != null
                if (alreadyHasScrewdriver) {
                    showTemporaryText("Still a very fluffy shark")
                } else {
                    showTemporaryText("A very fluffy shark. It has a screwdriver in its jaws")
                    inventory.add(InventoryItem.Screwdriver)
                }
            }

            Event.SNOWMAN_CLICKED -> {
                showTemporaryText("That is obviously a snowman")
            }

            // WhiteBoardScreen

            Event.LEFT_SCREW_CLICKED -> {
                if (inventory.find { it == InventoryItem.Screwdriver } != null) {
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.LeftScrewItem)
                    if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RightScrewItem } == null) {
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvLeftScrewItem)
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvBrokenItem)
                    } else {
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvRightScrewItem)
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem)
                    }
                }
            }
            Event.RIGHT_SCREW_CLICKED -> {
                if (inventory.find { it == InventoryItem.Screwdriver } != null) {
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.RightScrewItem)
                    if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.LeftScrewItem } == null) {
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvRightScrewItem)
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvBrokenItem)
                    } else {
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvLeftScrewItem)
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem)
                    }
                }
            }

            Event.TV_CLICKED -> {
                showTemporaryText("I don't want to look at this")
            }

            Event.MESSAGE_134_CLICKED -> {
                showTemporaryText("Definitely")
            }

            Event.WHITEBOARD_IMAGE_CLICKED -> {
                showTemporaryText("What?")
            }

            Event.WHITEBOARD_NOTE_CLICKED -> {
                showTemporaryText("""It says "One bug remaining till new release"""")
            }

            Event.BROKEN_TV_CLICKED -> {
                showTemporaryText("At least it works on mocks")
            }

            Event.TV_ON_ONE_SCREW_CLICKED -> {
                showTemporaryText("Looking better already")
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
        if (event != Event.INVENTORY_CHARGER_CLICKED && event != Event.INVENTORY_SCREWDRIVER_CLICKED &&
                event != Event.INVENTORY_KEY_CLICKED && event != Event.INVENTORY_PHONE_CLICKED) {
            selectedItem = null
        }
    }

    private fun showTemporaryText(text: String) {
        stageUiHandler.addTemporaryScreenText(ScreenText(text))
    }

    private fun showText(screenText: ScreenText) { //TODO this screenText should remain on screen without fade out
        stageUiHandler.addScreenText(screenText)
    }

    private fun showScreenItem(screenItem: ScreenItem) {
        stageUiHandler.addScreenItem(screenItem)
    }

    class LeftArrowItem : ScreenItem(
            drawable = Drawable("arrow_left.png"),
            x = 50f,
            y = 230f,
            width = 80f,
            height = 80f,
            event = Event.LEFT_ARROW_CLICKED)

    class RightArrowItem : ScreenItem(
            drawable = Drawable("arrow_right.png"),
            x = 1830f,
            y = 230f,
            width = 80f,
            height = 80f,
            event = Event.RIGHT_ARROW_CLICKED
    )

    class DownArrowItem() : ScreenItem(
            drawable = Drawable("arrow_down.png"),
            x = 1830f,
            y = 30f,
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
