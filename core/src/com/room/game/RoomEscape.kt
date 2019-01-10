package com.room.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.room.game.stage1.Stage1
import com.badlogic.gdx.scenes.scene2d.Stage as LibgdxStage

class RoomEscape : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var currentStage: Stage
    private lateinit var libgdxStage: LibgdxStage

    private lateinit var clickAnimation: Animation<TextureRegion>
    private var clickAnimationStateTime = 100f
    private var clickAnimationCoordinates = Pair(0f, 0f)

    override fun create() {
        batch = SpriteBatch()
        currentStage = Stage1()
        libgdxStage = LibgdxStage(ScreenViewport())

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(GestureDetector(GestureListener()))
        inputMultiplexer.addProcessor(libgdxStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val frames = Array(4) {
            TextureRegion(Texture(when (it) {
                0 -> "background1.jpg"
                1 -> "background2.jpg"
                2 -> "background3.jpg"
                else -> "background4.jpg"
            }))
        }
        clickAnimation = Animation(0.1f, *frames)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        clickAnimationStateTime += Gdx.graphics.deltaTime

        batch.apply {
            begin()
            //TODO: move Texture creation out of render()
            val background = Texture(currentStage.currentScreen.background.resourceId)
            draw(background, 0f, 0f)

            if (clickAnimationStateTime < 0.4f) {
                val currentFrame = clickAnimation.getKeyFrame(clickAnimationStateTime, false)
                draw(currentFrame, clickAnimationCoordinates.first, Gdx.graphics.height - clickAnimationCoordinates.second, 40f, 40f)
            }

            end()
        }

        libgdxStage.actors.removeAll { true }

        //TODO: don't add objects that are already here
        currentStage.currentScreen.screenObjects.forEach {
            addUiElement(it)
        }
        currentStage.ui.forEach { addUiElement(it) }


        libgdxStage.act()
        libgdxStage.draw()
    }

    override fun dispose() {
        batch.dispose()
        //TODO: dispose of all textures
    }

    private fun addUiElement(screenItem: ScreenItem): ImageButton {
        val textureRegion = TextureRegion(Texture(screenItem.drawable.resourceId))
        val textureRegionDrawable = TextureRegionDrawable(textureRegion)
        val button = ImageButton(textureRegionDrawable)
        button.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screenItem.onClick()
                return false
            }
        })
        button.setPosition(screenItem.x, screenItem.y)
        button.setSize(40f, 40f)
        libgdxStage.addActor(button)
        return button
    }

    private inner class GestureListener : GestureDetector.GestureListener {
        override fun fling(velocityX: Float, velocityY: Float, button: Int) = false

        override fun zoom(initialDistance: Float, distance: Float) = false

        override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) = false

        override fun pinchStop() {}

        override fun tap(x: Float, y: Float, count: Int, button: Int) = false

        override fun panStop(x: Float, y: Float, pointer: Int, button: Int) = false

        override fun longPress(x: Float, y: Float) = false

        override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?) = false

        override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
            clickAnimationStateTime = 0f
            clickAnimationCoordinates = Pair(x - 20f, y + 20f)
            return false
        }
    }
}
