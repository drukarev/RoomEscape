package com.room.game.stage1

import com.room.game.*
import java.util.*

class Stage1 : Stage {
    override val ui: MutableList<ScreenItem> = mutableListOf(
            ScreenItem(
                    drawable = Drawable("arrow_left.png"),
                    onClick = { currentScreenInt = if (currentScreenInt < 1) screens.size - 1 else --currentScreenInt },
                    x = 20f,
                    y = 40f
            ),
            ScreenItem(
                    drawable = Drawable("arrow_right.png"),
                    onClick = { currentScreenInt = if (currentScreenInt > screens.size - 2) 0 else ++currentScreenInt },
                    x = 196f,
                    y = 40f
            ))
    override val floor: Screen? = null
    override val ceiling: Screen? = null
    override val screens: LinkedList<Screen> = LinkedList(listOf(Screen1(), Screen2(), Screen3(), Screen4()))
    override val backpack: MutableList<BackpackItem> = mutableListOf()

    private var currentScreenInt: Int = 0

    override fun getCurrentScreen() = screens[currentScreenInt]
}

class Screen1 : Screen {
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

class Screen2 : Screen {
    override val background: Drawable = Drawable("background2.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

class Screen3 : Screen {
    override val background: Drawable = Drawable("background3.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}

class Screen4 : Screen {
    override val background: Drawable = Drawable("background4.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
}
