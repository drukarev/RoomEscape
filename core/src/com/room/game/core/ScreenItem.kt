package com.room.game.core

import com.room.game.stage1.Event

open class ScreenItem(
        val drawable: Drawable,
        val x: Float,
        val y: Float,
        val width: Float,
        val height: Float,
        val event: Event?
)
