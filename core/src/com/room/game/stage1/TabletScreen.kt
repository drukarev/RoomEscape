package com.room.game.stage1

import com.room.game.core.*

class TabletScreen(
        leftScreen: Screen? = null,
        rightScreen: Screen? = null,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(Background)
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object Number1Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_1_BUTTON_CLICKED
    )

    object Number2Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_2_BUTTON_CLICKED
    )

    object Number3Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_3_BUTTON_CLICKED
    )

    object Number4Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_4_BUTTON_CLICKED
    )

    object Number5Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_5_BUTTON_CLICKED
    )

    object Number6Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_6_BUTTON_CLICKED
    )

    object Number7Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_7_BUTTON_CLICKED
    )

    object Number8Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_8_BUTTON_CLICKED
    )

    object Number9Button : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1000f,
            y = 200f,
            width = 160f,
            height = 160f,
            event = Event.NUMBER_9_BUTTON_CLICKED
    )

    private var passCode = ""

    fun getPasscodeText(newChar: Char) : ScreenText {
        if (passCode.length < 5) {
            passCode = passCode.plus(newChar)
        } else {
            passCode = ""
        }
        return ScreenText(passCode)
    }
}
