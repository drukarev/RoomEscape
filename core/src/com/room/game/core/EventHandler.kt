package com.room.game.core

import com.room.game.stage1.Event

class EventHandler(val listeners: List<Listener>) {

    fun handleEvent(event: Event) {
        listeners.forEach {
            it.onEvent(event)
        }
    }

    interface Listener {
        fun onEvent(event: Event)
    }
}

