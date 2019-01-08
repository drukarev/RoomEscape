package core

import java.util.*

interface Stage {
    val floor: Screen
    val ceiling: Screen
    val walls: LinkedList<Screen>
    val backpack: MutableList<BackpackItem>
}

interface Screen {
    val background: Drawable
    val screenObjects: ScreenItem
}

interface ScreenItem {
    val drawable: Drawable
    fun onClick()
}
interface BackpackItem {
    val drawable: Drawable
}

interface Drawable
