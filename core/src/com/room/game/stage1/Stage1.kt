package com.room.game.stage1

import com.room.game.core.*

class Stage1(private val stageUiHandler: StageUiHandler) : Stage, EventHandler.Listener {

    private val titleScreen = TitleScreen(uiHandler = stageUiHandler)
    private val whiteBoardScreen = WhiteBoardScreen(null, null, null, stageUiHandler)
    private val snowmanScreen = SnowmanScreen(null, null, null, stageUiHandler)
    private val lockerScreen = LockerScreen(whiteBoardScreen, snowmanScreen, null, stageUiHandler)
    private val workplaceScreen = WorkplaceScreen(snowmanScreen, whiteBoardScreen, null, stageUiHandler)
    private val tabletScreen = TabletScreen(uiHandler = stageUiHandler, downScreen = lockerScreen)
    private val endScreen = EndScreen(uiHandler = stageUiHandler)

    init {
        whiteBoardScreen.leftScreen = workplaceScreen
        whiteBoardScreen.rightScreen = lockerScreen

        snowmanScreen.leftScreen = lockerScreen
        snowmanScreen.rightScreen = workplaceScreen
    }

    override val inventory: Inventory = Inventory(stageUiHandler)

    override val screens = listOf(whiteBoardScreen, lockerScreen, snowmanScreen, workplaceScreen)

    override var currentScreen: Screen = titleScreen
        set(value) {
            field = value
            prepareForCurrentScreen()
        }

    override var musicOn = true
        set(value) {
            if (value) {
                stageUiHandler.removeScreenItem(SoundOffItem)
                stageUiHandler.addScreenItem(SoundOnItem)
                stageUiHandler.startMusic()
            } else {
                stageUiHandler.removeScreenItem(SoundOnItem)
                stageUiHandler.addScreenItem(SoundOffItem)
                stageUiHandler.stopMusic()
            }
            field = value
        }

    init {
        prepareForCurrentScreen()
    }

    private fun setCurrentScreen(arrowDirection: ArrowDirection) {
        stageUiHandler.playSound(Sound("ui_button_click.mp3"))
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

        currentScreen.downScreen?.also {
            stageUiHandler.addScreenItem(DownArrowItem)
        }
        currentScreen.leftScreen?.also {
            stageUiHandler.addScreenItem(LeftArrowItem)
        }
        currentScreen.rightScreen?.also {
            stageUiHandler.addScreenItem(RightArrowItem)
        }
        stageUiHandler.addScreenItem(if (musicOn) SoundOnItem else SoundOffItem)

        inventory.redraw()
        stageUiHandler.fadeOut()
    }

    override fun onEvent(event: Event) {
        when (event) {

            // Title screen

            Event.START_GAME -> {
                currentScreen = screens.first()
                stageUiHandler.playSound(Sound("ui_button_click.mp3"))
            }

            // Locker screen

            Event.PHONE_IN_LOCKER_CLICKED -> {
                lockerScreen.screenObjects.remove(LockerScreen.PhoneItem)
                inventory.add(InventoryItem.Phone)
                stageUiHandler.playSound(Sound("item_picked.mp3"))
            }

            Event.TABLET_CLICKED -> {
                currentScreen = tabletScreen
                stageUiHandler.playSound(Sound("ui_button_click.mp3"))
            }

            Event.LOCKER_DOOR_CLICKED -> {
                if (lockerScreen.screenObjects.find { it == LockerScreen.LockerDoorClosed } != null) {
                    showTemporaryText("That's a stand for mobile phones. It is locked")
                }
            }

            Event.INNER_TABLET_CLICKED -> {
                showTemporaryText("Someone took all the phones")
            }

            // Tablet screen

            Event.NUMBER_1_BUTTON_CLICKED -> {
                onTabletButtonClicked('1')
            }
            Event.NUMBER_2_BUTTON_CLICKED -> {
                onTabletButtonClicked('2')
            }
            Event.NUMBER_3_BUTTON_CLICKED -> {
                onTabletButtonClicked('3')
            }
            Event.NUMBER_4_BUTTON_CLICKED -> {
                onTabletButtonClicked('4')
            }
            Event.NUMBER_5_BUTTON_CLICKED -> {
                onTabletButtonClicked('5')
            }
            Event.NUMBER_6_BUTTON_CLICKED -> {
                onTabletButtonClicked('6')
            }
            Event.NUMBER_7_BUTTON_CLICKED -> {
                onTabletButtonClicked('7')
            }
            Event.NUMBER_8_BUTTON_CLICKED -> {
                onTabletButtonClicked('8')
            }
            Event.NUMBER_9_BUTTON_CLICKED -> {
                onTabletButtonClicked('9')
            }
            Event.ENTER_BUTTON_CLICKED -> {
                val response = tabletScreen.checkPasscode()

                response.lastScreenText.also {
                    stageUiHandler.removeScreenText(it)
                }
                stageUiHandler.addScreenText(response.newScreenText)

                if (response.success == TabletScreen.PasscodeState.SUCCESS) {
                    currentScreen = lockerScreen
                    lockerScreen.screenObjects.remove(LockerScreen.LockerDoorClosed)
                    lockerScreen.screenObjects.remove(LockerScreen.TabletItem)
                    stageUiHandler.playSound(Sound("correct_password.mp3"))
                    stageUiHandler.playSound(Sound("locker_opened.mp3"))
                } else {
                    stageUiHandler.playSound(Sound("wrong_password.mp3"))
                }
            }

            // Workplace screen

            Event.DESK_LOCKER_ITEM_CLICKED -> {
                if (inventory.selectedItem == InventoryItem.Key) {
                    inventory.remove(InventoryItem.Key)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.DeskLockerShelfItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ChargerItem)
                    stageUiHandler.playSound(Sound("lock_open.mp3"))
                    stageUiHandler.playSound(Sound("desk_drawer_open.mp3"))
                } else {
                    showTemporaryText("This drawer is closed")
                    stageUiHandler.playSound(Sound("desk_drawer_locked.mp3"))
                }
            }

            Event.PHONE_HOLDER_CLICKED -> {
                if (inventory.selectedItem == InventoryItem.Phone) {
                    inventory.remove(InventoryItem.Phone)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.PhoneHolderWithMobileItem)
                    stageUiHandler.playSound(Sound("cable_connected.mp3"))
                } else {
                    showTemporaryText("Looks like a phone holder")
                }
            }

            Event.POWER_SOCKET_ITEM_CLICKED -> {
                if (inventory.selectedItem == InventoryItem.Charger) {
                    inventory.remove(InventoryItem.Charger)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.ConnectedChargerItem)
                    workplaceScreen.screenObjects.add(WorkplaceScreen.NotebookOnItem)
                    stageUiHandler.playSound(Sound("cable_connected.mp3"))
                    stageUiHandler.playSound(Sound("notebook_start_up.mp3"))
                }
            }

            Event.NOTEBOOK_CLICKED -> {
                if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.NotebookWithBugFixedItem } != null) {
                    showTemporaryText("Bug fixed! Well, it's friday night, let's deploy")
                } else if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.ConnectedChargerItem } != null) {
                    if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.PhoneHolderWithMobileItem } != null) {
                        stageUiHandler.playSound(Sound("commit_made.mp3"))
                        showTemporaryText("Bug fixed! Well, it's friday night, let's deploy")
                        workplaceScreen.screenObjects.remove(WorkplaceScreen.NotebookOnItem)
                        workplaceScreen.screenObjects.add(WorkplaceScreen.NotebookWithBugFixedItem)
                    } else {
                        showTemporaryText("Can't fix bug without a phone")
                    }
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
                stageUiHandler.playSound(Sound("item_picked.mp3"))
            }

            Event.DEPLOY_BUTTON_CLICKED -> {
                if (workplaceScreen.screenObjects.find { it == WorkplaceScreen.NotebookWithBugFixedItem } != null) {
                    stageUiHandler.playSound(Sound("deploy_button_hit.mp3"))
                    currentScreen = endScreen
                } else {
                    showTemporaryText("That's a deploy button. It is tempting")
                }
            }

            // Snowman screen

            Event.KEY_FROM_SNOWMAN_CLICKED -> {
                snowmanScreen.screenObjects.remove(SnowmanScreen.KeyItem)
                inventory.add(InventoryItem.Key)
                stageUiHandler.playSound(Sound("key_taken.mp3"))
            }

            Event.BLOHAJ_CLICKED -> {
                if (SnowmanScreen.alreadyTookScrewdriver) {
                    showTemporaryText("Still a very fluffy shark. It had a screwdriver in its jaws")
                } else {
                    showTemporaryText("A very fluffy shark. It has a screwdriver in its jaws")
                    inventory.add(InventoryItem.Screwdriver)
                    stageUiHandler.playSound(Sound("item_picked.mp3"))
                    SnowmanScreen.alreadyTookScrewdriver = true
                }
            }

            Event.SNOWMAN_CLICKED -> {
                showTemporaryText("That is obviously a snowman")
            }

            Event.BALANCEBOARD_CLICKED -> {
                showTemporaryText("Stable as our builds")
            }

            // WhiteBoardScreen

            Event.LEFT_SCREW_CLICKED -> {
                if (inventory.selectedItem == InventoryItem.Screwdriver) {
                    stageUiHandler.playSound(Sound("turn_screw.mp3"))
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.LeftScrewItem)
                    if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.RightScrewItem } == null) {
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvLeftScrewItem)
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvBrokenItem)
                        stageUiHandler.playSound(Sound("tv_crashed.mp3"))
                        inventory.remove(InventoryItem.Screwdriver)
                    } else {
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvRightScrewItem)
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem)
                    }
                }
            }
            Event.RIGHT_SCREW_CLICKED -> {
                if (inventory.selectedItem == InventoryItem.Screwdriver) {
                    stageUiHandler.playSound(Sound("turn_screw.mp3"))
                    whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.RightScrewItem)
                    if (whiteBoardScreen.screenObjects.find { it == WhiteBoardScreen.LeftScrewItem } == null) {
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvRightScrewItem)
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvBrokenItem)
                        stageUiHandler.playSound(Sound("tv_crashed.mp3"))
                        inventory.remove(InventoryItem.Screwdriver)
                    } else {
                        whiteBoardScreen.screenObjects.add(WhiteBoardScreen.TvLeftScrewItem)
                        whiteBoardScreen.screenObjects.remove(WhiteBoardScreen.TvItem)
                    }
                }
            }

            Event.TV_CLICKED -> {
                showTemporaryText("Let's pretend I didn't see that")
            }

            Event.MESSAGE_134_CLICKED -> {
                showTemporaryText("Definitely")
            }

            Event.WHITEBOARD_IMAGE_CLICKED -> {
                showTemporaryText("That's some modern art right there")
            }

            Event.WHITEBOARD_NOTE_CLICKED -> {
                showTemporaryText(""""One bug left before new release". I doubt it""")
                stageUiHandler.playSound(Sound("paper_click.mp3"))
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
            Event.ICON_SOUND_CLICKED -> { musicOn = !musicOn }

            // Inventory items

            Event.INVENTORY_PHONE_CLICKED -> inventory.selectedItem = InventoryItem.Phone
            Event.INVENTORY_KEY_CLICKED -> inventory.selectedItem = InventoryItem.Key
            Event.INVENTORY_SCREWDRIVER_CLICKED -> inventory.selectedItem = InventoryItem.Screwdriver
            Event.INVENTORY_CHARGER_CLICKED -> inventory.selectedItem = InventoryItem.Charger
        }
        if (event != Event.INVENTORY_CHARGER_CLICKED && event != Event.INVENTORY_SCREWDRIVER_CLICKED &&
                event != Event.INVENTORY_KEY_CLICKED && event != Event.INVENTORY_PHONE_CLICKED) {
            inventory.selectedItem = null
        }
    }

    private fun onTabletButtonClicked(value: Char) {
        stageUiHandler.playSound(Sound("digital_button_pressed.mp3"))
        tabletScreen.addDigit(value).apply {
            lastScreenText?.also {
                stageUiHandler.removeScreenText(it)
            }
            newScreenText?.also {
                stageUiHandler.addScreenText(it)
            }
        }
    }

    private fun showTemporaryText(text: String) {
        stageUiHandler.addTemporaryScreenText(ScreenText(text))
    }

    private fun showText(screenText: ScreenText) {
        stageUiHandler.addScreenText(screenText)
    }

    private fun showScreenItem(screenItem: ScreenItem) {
        stageUiHandler.addScreenItem(screenItem)
    }

    object LeftArrowItem : ScreenItem(
            drawable = Drawable("arrow_left.png"),
            x = 30f,
            y = 320f,
            width = 80f,
            height = 80f,
            event = Event.LEFT_ARROW_CLICKED)

    object RightArrowItem : ScreenItem(
            drawable = Drawable("arrow_right.png"),
            x = 1810f,
            y = 320f,
            width = 80f,
            height = 80f,
            event = Event.RIGHT_ARROW_CLICKED
    )

    object DownArrowItem : ScreenItem(
            drawable = Drawable("arrow_down.png"),
            x = 920f,
            y = 20f,
            width = 80f,
            height = 80f,
            event = Event.DOWN_ARROW_CLICKED
    )

    object SoundOnItem : ScreenItem(
            drawable = Drawable("sound_on.jpg"),
            x = 50f,
            y = 930f,
            width = 100f,
            height = 100f,
            event = Event.ICON_SOUND_CLICKED
    )

    object SoundOffItem : ScreenItem(
            drawable = Drawable("sound_off.jpg"),
            x = 50f,
            y = 930f,
            width = 100f,
            height = 100f,
            event = Event.ICON_SOUND_CLICKED
    )
}

enum class ArrowDirection {
    LEFT,
    RIGHT,
    DOWN
}
