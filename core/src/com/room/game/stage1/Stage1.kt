package com.room.game.stage1

import com.room.game.*
import java.util.*

class Stage1 : Stage {
    override val ui: MutableList<ScreenItem> = mutableListOf(
            ScreenItem(
                    drawable = Drawable("arrow_left.png"),
                    onClick = { setCurrentScreen(ArrowDirection.LEFT) },
                    x = 20f,
                    y = 40f
            ),
            ScreenItem(
                    drawable = Drawable("arrow_right.png"),
                    onClick = { setCurrentScreen(ArrowDirection.RIGHT) },
                    x = 196f,
                    y = 40f
            ),
            ScreenItem(  //TODO: make down button invisible when down action is not allowed
                    drawable = Drawable("arrow_down.png"),
                    onClick = { setCurrentScreen(ArrowDirection.DOWN) },
                    x = 108f,
                    y = 40f
            ))
    override val floor: Screen? = null
    override val ceiling: Screen? = null
    override val screens: LinkedList<Screen> = LinkedList(listOf(Screen1, Screen2, Screen3, Screen4))
    override val backpack: MutableList<BackpackItem> = mutableListOf()

    override var currentScreen: Screen = screens.first

    private fun setCurrentScreen(arrowDirection: ArrowDirection) {
        currentScreen = when (arrowDirection) {
            ArrowDirection.LEFT -> currentScreen.leftScreen ?: currentScreen
            ArrowDirection.RIGHT -> currentScreen.rightScreen ?: currentScreen
            ArrowDirection.DOWN -> currentScreen.downScreen ?: currentScreen
        }
    }
}

enum class ArrowDirection {
    LEFT,
    RIGHT,
    DOWN
}

object Screen1 : Screen {
    override val leftScreen: Screen = Screen4
    override val rightScreen: Screen = Screen2
    override val downScreen: Screen? = Screen11
    override val background: Drawable = Drawable("background1.jpg")
    override val screenObjects: List<ScreenItem> = listOf(
            ScreenItem(
                    drawable = Drawable("background2.jpg"),
                    onClick = { },
                    x = 5f,
                    y = 5f
            ),
            ScreenItem(
                    drawable = Drawable("background3.jpg"),
                    onClick = { },
                    x = 10f,
                    y = 10f
            )
    )
}

object Screen11 : Screen {
    override val leftScreen: Screen? = null
    override val rightScreen: Screen? = null
    override val downScreen: Screen? = Screen1
    override val background: Drawable = Drawable("background11.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

object Screen2 : Screen {
    override val leftScreen: Screen = Screen1
    override val rightScreen: Screen = Screen3
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background2.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

object Screen3 : Screen {
    override val leftScreen: Screen = Screen2
    override val rightScreen: Screen = Screen4
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background3.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

object Screen4 : Screen {
    override val leftScreen: Screen = Screen3
    override val rightScreen: Screen = Screen1
    override val downScreen: Screen? = null
    override val background: Drawable = Drawable("background4.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}
