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
            y = 550f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_1_BUTTON_CLICKED
    )

    object Number2Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 800f,
            y = 550f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_2_BUTTON_CLICKED
    )

    object Number3Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 1150f,
            y = 550f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_3_BUTTON_CLICKED
    )

    object Number4Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 450f,
            y = 300f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_4_BUTTON_CLICKED
    )

    object Number5Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 800f,
            y = 300f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_5_BUTTON_CLICKED
    )

    object Number6Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 1150f,
            y = 300f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_6_BUTTON_CLICKED
    )

    object Number7Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 450f,
            y = 50f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_7_BUTTON_CLICKED
    )

    object Number8Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 800f,
            y = 50f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_8_BUTTON_CLICKED
    )

    object Number9Button : ScreenItem(
            drawable = Drawable("tablet_screen_button1.jpg"), //TODO: replace
            x = 1150f,
            y = 50f,
            width = 302f,
            height = 200f,
            event = Event.NUMBER_9_BUTTON_CLICKED
    )

    object EnterButton : ScreenItem(
            drawable = Drawable("phone.png"), //TODO: replace
            x = 1200f,
            y = 750f,
            width = 302f,
            height = 200f,
            event = Event.ENTER_BUTTON_CLICKED
    )

    private var passCode = ""
    private var lastScreenText: ScreenText = ScreenText("", 800f, 830f)

    fun addDigit(newChar: Char): AddDigitResponse {
        if (passCode.length < 6) {
            passCode += newChar
        }
        val newScreenText = ScreenText(passCode, 830f, 830f)
        val response = AddDigitResponse(lastScreenText, newScreenText)
        lastScreenText = newScreenText
        return response
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
            val lastScreenText: ScreenText,
            val newScreenText: ScreenText
    )

    enum class PasscodeState {
        SUCCESS,
        FAILED
    }
}
