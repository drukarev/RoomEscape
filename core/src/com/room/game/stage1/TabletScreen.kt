package com.room.game.stage1

import com.room.game.core.*

class TabletScreen(
        leftScreen: Screen? = null,
        rightScreen: Screen? = null,
        downScreen: Screen?,
        uiHandler: StageUiHandler,
        screenObjects: MutableList<ScreenItem> = mutableListOf(
                Background,
                Number1Button,
                Number2Button,
                Number3Button,
                Number4Button,
                Number5Button,
                Number6Button,
                Number7Button,
                Number8Button,
                Number9Button,
                EnterButton
        )
) : Screen(leftScreen, rightScreen, downScreen, screenObjects, uiHandler) {

    object Background : ScreenItem(
            drawable = Drawable("tablet_screen_background.jpg"),
            x = 0f,
            y = 0f,
            width = 1920f,
            height = 1080f,
            event = null
    )

    object Number1Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"),
            x = 450f,
            y = 600f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_1_BUTTON_CLICKED
    )

    object Number2Button : ScreenItem(
            drawable = Drawable("tablet_screen_button2.jpg"),
            x = 796f,
            y = 600f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_2_BUTTON_CLICKED
    )

    object Number3Button : ScreenItem(
            drawable = Drawable("tablet_screen_button3.jpg"),
            x = 1140f,
            y = 600f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_3_BUTTON_CLICKED
    )

    object Number4Button : ScreenItem(
            drawable = Drawable("tablet_screen_button4.jpg"),
            x = 450f,
            y = 400f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_4_BUTTON_CLICKED
    )

    object Number5Button : ScreenItem(
            drawable = Drawable("tablet_screen_button5.jpg"),
            x = 796f,
            y = 400f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_5_BUTTON_CLICKED
    )

    object Number6Button : ScreenItem(
            drawable = Drawable("tablet_screen_button6.jpg"),
            x = 1140f,
            y = 400f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_6_BUTTON_CLICKED
    )

    object Number7Button : ScreenItem(
            drawable = Drawable("tablet_screen_button7.jpg"),
            x = 450f,
            y = 200f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_7_BUTTON_CLICKED
    )

    object Number8Button : ScreenItem(
            drawable = Drawable("tablet_screen_button8.jpg"),
            x = 796f,
            y = 200f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_8_BUTTON_CLICKED
    )

    object Number9Button : ScreenItem(
            drawable = Drawable("tablet_screen_button9.jpg"),
            x = 1140f,
            y = 200f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_9_BUTTON_CLICKED
    )

    object EnterButton : ScreenItem(
            drawable = Drawable("tablet_screen_ok_button.jpg"),
            x = 1160f,
            y = 822f,
            width = 125f,
            height = 124f,
            event = Event.ENTER_BUTTON_CLICKED
    )

    private var passCode = ""
    private var lastScreenText: ScreenText = ScreenText("", 800f, 830f)

    fun addDigit(newChar: Char): AddDigitResponse {
        if (passCode.length < 6) {
            passCode += newChar
            val newScreenText = ScreenText(passCode, 746f, 876f)
            val response = AddDigitResponse(lastScreenText, newScreenText)
            lastScreenText = newScreenText
            return response
        } else {
            return AddDigitResponse(null, null)
        }
    }

    fun checkPasscode(): PasscodeCheckResponse {
        val state = if (passCode == "134") PasscodeState.SUCCESS else PasscodeState.FAILED
        passCode = ""
        val newScreenText = ScreenText(passCode, 800f, 1000f)
        val response = PasscodeCheckResponse(state, lastScreenText, newScreenText)
        lastScreenText = newScreenText
        return response
    }

    data class PasscodeCheckResponse(
            val success: PasscodeState,
            val lastScreenText: ScreenText,
            val newScreenText: ScreenText
    )

    data class AddDigitResponse(
            val lastScreenText: ScreenText?,
            val newScreenText: ScreenText?
    )

    enum class PasscodeState {
        SUCCESS,
        FAILED
    }
}
