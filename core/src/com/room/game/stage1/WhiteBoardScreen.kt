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
        screenObjects: MutableList<ScreenItem> = mutableListOf(
                Background, Message134Item, TvItem, WhiteBoardImageItem,
                WhiteBoardNoteItem, LeftScrewItem, RightScrewItem
        )
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("whiteboard_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object LeftScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_screw_left.png"),
            x = 1158f,
            y = 840f,
            width = 71f,
            height = 91f,
            event = Event.LEFT_SCREW_CLICKED
    )

    object RightScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_screw_right.png"),
            x = 1582f,
            y = 850f,
            width = 68f,
            height = 81f,
            event = Event.RIGHT_SCREW_CLICKED
    )

    object TvItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv.jpg"),
            x = 1070f,
            y = 519f,
            width = 649f,
            height = 345f,
            event = Event.TV_CLICKED
    )

    object Message134Item : ScreenItem(
            drawable = Drawable("whiteboard_screen_134.jpg"),
            x = 1160f,
            y = 590f,
            width = 498f,
            height = 243f,
            event = Event.MESSAGE_134_CLICKED
    )

    object WhiteBoardImageItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_image.jpg"),
            x = 265f,
            y = 535f,
            width = 552f,
            height = 413f,
            event = Event.WHITEBOARD_IMAGE_CLICKED
    )

    object WhiteBoardNoteItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_note.jpg"),
            x = 827f,
            y = 324f,
            width = 146f,
            height = 900f,
            event = Event.WHITEBOARD_NOTE_CLICKED
    )

    object TvBrokenItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_broken.jpg"),
            x = 1110f,
            y = -30f,
            width = 604f,
            height = 263f,
            event = Event.BROKEN_TV_CLICKED
    )

    object TvLeftScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_left_screw.png"),
            x = 1080f,
            y = 460f,
            width = 652f,
            height = 402f,
            event = Event.TV_ON_ONE_SCREW_CLICKED
    )

    object TvRightScrewItem : ScreenItem(
            drawable = Drawable("whiteboard_screen_tv_right_screw.png"),
            x = 1100f,
            y = 440f,
            width = 603f,
            height = 430f,
            event = Event.TV_ON_ONE_SCREW_CLICKED
    )
}
