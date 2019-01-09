package com.room.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.room.game.stage1.Stage1

class RoomEscape : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var currentStage: Stage

    override fun create() {
        batch = SpriteBatch()
        currentStage = Stage1()
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
    }

    override fun dispose() {
        batch.dispose()
        //TODO: dispose of all textures
    }
}
