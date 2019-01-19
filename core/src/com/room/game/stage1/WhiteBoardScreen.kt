package com.room.game.stage1

import com.room.game.core.Drawable
import com.room.game.core.Screen
import com.room.game.core.ScreenItem
import com.room.game.core.StageUiHandler

class WhiteBoardScreen(
        leftScreen: Screen?,
        rightScreen: Screen?,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> =
                mutableListOf(Message134Item, TvItem, WhiteBoardImageItem, WhiteBoardNoteItem, LeftScrewItem, RightScrewItem)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    override val background: Drawable = Drawable("whiteboard_screen_background.jpg")

    object LeftScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_screw_left.png"),
            x = 1246f,
            y = 850f,
            width = 71f,
            height = 91f,
            event = Event.LEFT_SCREW_CLICKED
    )

    object RightScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_screw_right.png"),
            x = 1697f,
            y = 850f,
            width = 68f,
            height = 81f,
            event = Event.RIGHT_SCREW_CLICKED
    )

    object TvItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv.jpg"),
            x = 1157f,
            y = 519f,
            width = 649f,
            height = 345f,
            event = Event.TV_CLICKED
    )

    object Message134Item : ScreenItem(
            drawable = Drawable("whiteboard_screen_134.jpg"),
            x = 1267f,
            y = 626f,
            width = 498f,
            height = 243f,
            event = Event.MESSAGE_134_CLICKED
    )

    object WhiteBoardImageItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_image.jpg"),
            x = 251f,
            y = 525f,
            width = 552f,
            height = 413f,
            event = Event.WHITEBOARD_IMAGE_CLICKED
    )

    object WhiteBoardNoteItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_note.jpg"),
            x = 830f,
            y = 304f,
            width = 146f,
            height = 900f,
            event = Event.WHITEBOARD_NOTE_CLICKED
    )

    object TvBrokenItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_broken.png"),
            x = 1130f,
            y = 0f,
            width = 801f,
            height = 346f,
            event = Event.BROKEN_TV_CLICKED
    )

    object TvLeftScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_left_screw.jpg"),
            x = 1100f,
            y = 560f,
            width = 801f,
            height = 346f,
            event = Event.TV_ON_ONE_SCREW_CLICKED
    )

    object TvRightScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_right_screw.jpg"),
            x = 1176f,
            y = 400f,
            width = 705f,
            height = 441f,
            event = Event.TV_ON_ONE_SCREW_CLICKED
    )
}
