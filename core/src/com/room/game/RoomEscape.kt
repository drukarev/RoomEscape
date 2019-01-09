package com.room.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
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

    override fun create() {
        batch = SpriteBatch()
        currentStage = Stage1()
        libgdxStage = LibgdxStage(ScreenViewport())
        Gdx.input.inputProcessor = libgdxStage
        currentStage.ui.forEach { addUiElement(it) }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        //TODO: move Texture creation out of render()
        val background = Texture(
                currentStage.walls[currentStage.currentScreen]
                        .background
                        .resourceId
        )

        batch.draw(background, 0f, 0f)
        batch.end()

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
}
