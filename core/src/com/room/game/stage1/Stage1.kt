package com.room.game.stage1

import com.room.game.*
import java.util.*

class Stage1 : Stage {
    override var currentScreen: Int = 0
    override val floor: Screen? = null
    override val ceiling: Screen? = null
    override val walls: LinkedList<Screen> = LinkedList(listOf(Screen1(), Screen2(), Screen3(), Screen4()))
    override val backpack: MutableList<BackpackItem> = mutableListOf()
}

class Screen1 : Screen {
    override val background: Drawable = Drawable("background1.jpg")
    override val screenObjects: List<ScreenItem> = listOf()
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
