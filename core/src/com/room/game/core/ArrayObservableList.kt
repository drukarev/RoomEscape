package com.room.game.core

import javafx.collections.ModifiableObservableListBase
import java.util.ArrayList

class ArrayObservableList<E> : ModifiableObservableListBase<E>() {

    val delegate = ArrayList<E>()

    override val size: Int
        get() = delegate.size

    override fun doRemove(index: Int): E {
        return delegate.removeAt(index)
    }

    override fun doSet(index: Int, element: E): E {
        return delegate.set(index, element)
    }

    override fun doAdd(index: Int, element: E) {
        delegate.add(index, element)
    }

    override fun get(index: Int): E {
        return delegate[index]
    }
}
