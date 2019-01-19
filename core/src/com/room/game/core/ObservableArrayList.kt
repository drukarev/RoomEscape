package com.room.game.core

import java.util.ArrayList

class ObservableArrayList<E> {

    private val delegate = ArrayList<E>()

    private val listeners = mutableListOf<Listener<E>>()

    val size: Int
        get() = delegate.size

    fun remove(element: E) {
        listeners.forEach {
            it.onElementRemoved(element)
        }
        delegate.remove(element)
    }

    fun add(element: E) {
        delegate.add(element)
        listeners.forEach {
            it.onElementAdded(element)
        }
    }

    fun addAll(elements: Collection<E>) {
        delegate.addAll(elements)
        listeners.forEach { listener ->
            elements.forEach { element ->
                listener.onElementAdded(element)
            }
        }
    }

    fun find(predicate: (E) -> Boolean): E? {
        return delegate.find(predicate)
    }

    fun forEach(action: (E) -> Unit) {
        delegate.forEach(action)
    }

    fun addListener(listener: Listener<E>) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener<E>) {
        listeners.remove(listener)
    }

    fun removeAllListeners() {
        listeners.removeAll { true }
    }

    interface Listener<E> {
        fun onElementAdded(element: E)
        fun onElementRemoved(element: E)
    }
}
