package com.room.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.room.game.stage1.Stage1
import com.badlogic.gdx.scenes.scene2d.Stage as LibgdxStage
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Timer
import com.room.game.core.*

class RoomEscape : ApplicationAdapter(), StageUiHandler {

    private lateinit var batch: SpriteBatch
    private lateinit var currentStage: Stage
    private lateinit var libgdxStage: LibgdxStage
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var camera: Camera
    private lateinit var viewport: FitViewport
    private lateinit var font: BitmapFont
    private lateinit var music: Music

    private lateinit var eventHandler: EventHandler

    private lateinit var clickAnimation: Animation<TextureRegion>
    private var clickAnimationStateTime = 100f
    private var clickAnimationCoordinates: Vector3 = Vector3()

    override fun create() {
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        camera = OrthographicCamera()
        viewport = FitViewport(1920f, 1080f, camera)
        libgdxStage = LibgdxStage(viewport)
        font = BitmapFont()

        currentStage = Stage1(this)
        eventHandler = EventHandler(listOf(currentStage as EventHandler.Listener))

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(GestureDetector(GestureListener()))
        inputMultiplexer.addProcessor(libgdxStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val frames = Array(4) {
            TextureRegion(Texture(when (it) {
                0 -> "arrow_down.png"
                1 -> "arrow_left.png"
                2 -> "arrow_right.png"
                else -> "arrow_down.png"
            }))
        }
        clickAnimation = Animation(0.1f, *frames)

        setMusic()
    }

    private fun setMusic() {
        AssetManager().apply {
            val musicName = "hard_boiled.mp3"
            load(musicName, Music::class.java)
            finishLoading()
            update()
            music = get(musicName)
            music.play()
            music.isLooping = true
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        clickAnimationStateTime += Gdx.graphics.deltaTime

        batch.apply {
            projectionMatrix = camera.combined
            begin()

            if (clickAnimationStateTime < 0.4f) {
                val currentFrame = clickAnimation.getKeyFrame(clickAnimationStateTime, false)
                draw(currentFrame, clickAnimationCoordinates.x, clickAnimationCoordinates.y, 40f, 40f)
            }

            end()
        }

        drawInventory()

        libgdxStage.act()
        libgdxStage.draw()
    }

    private fun drawInventory() {
        shapeRenderer.apply {
            begin(ShapeRenderer.ShapeType.Filled)
            setColor(240f, 240f, 240f, 20f)
            rect(viewport.worldWidth - 60f, 0f, 60f, viewport.worldHeight)
            end()
        }

        var margin = 0f
        currentStage.inventory.map {
            margin += 200f
            ScreenItem(it.drawable, viewport.worldWidth - 200f, viewport.worldHeight - margin, 160f, 160f, it.event)
        }.forEach {
            addScreenItem(it)
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        libgdxStage.viewport.update(width, height, true)
        viewport.update(width, height)
    }

    override fun dispose() {
        batch.dispose()
        libgdxStage.dispose()
        removeAllScreenElements()
        font.dispose()
        music.dispose()
        shapeRenderer.dispose()
    }

    private val screenItems: MutableMap<ScreenItem, Pair<ImageButton, Texture>> = mutableMapOf()

    override fun addScreenItem(screenItem: ScreenItem) {
        val texture = Texture(screenItem.drawable.resourceId)
        val textureRegion = TextureRegion(texture)
        val textureRegionDrawable = TextureRegionDrawable(textureRegion)
        val button = ImageButton(textureRegionDrawable)
        button.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screenItem.event?.also {
                    eventHandler.handleEvent(it)
                }
                return false
            }
        })
        button.setPosition(screenItem.x, screenItem.y)
        button.setSize(screenItem.width, screenItem.height)
        libgdxStage.addActor(button)
        screenItems[screenItem] = Pair(button, texture)
    }

    override fun removeScreenItem(screenItem: ScreenItem) {
        val actor = screenItems.getValue(screenItem)
        actor.first.addAction(Actions.fadeOut(0.1f))
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(actor.first, true)
                screenItems.remove(screenItem)
                actor.second.dispose()
            }
        }, 0.1f)
    }

    override fun addTemporaryScreenText(screenText: ScreenText) {
        val style = Label.LabelStyle()
        style.font = BitmapFont()
        val label = Label(screenText.text, style)
        label.setPosition(screenText.x, screenText.y)
        label.setFontScale(5f, 5f)
        label.setColor(0f, 0f, 0f, 1f)
        label.addAction(Actions.fadeIn(0.2f))
        libgdxStage.addActor(label)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                label.addAction(Actions.fadeOut(0.2f))
            }
        }, 2f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(label, true)
            }
        }, 2.3f)
    }

    private val screenTexts: MutableMap<ScreenText, Label> = mutableMapOf()

    override fun addScreenText(screenText: ScreenText) {
        val style = Label.LabelStyle()
        style.font = font
        val label = Label(screenText.text, style)
        label.setPosition(screenText.x, screenText.y)
        label.setFontScale(5f, 5f)
        label.setColor(0f, 0f, 0f, 1f)
        label.addAction(Actions.fadeIn(0.2f))
        libgdxStage.addActor(label)
        screenTexts[screenText] = label
    }

    override fun removeScreenText(screenText: ScreenText) {
        val actor = screenTexts.getValue(screenText)
        actor.addAction(Actions.fadeOut(0.1f))
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(actor, true)
                screenTexts.remove(screenText)
            }

        }, 0.1f)
    }

    override fun removeAllScreenElements() {
        libgdxStage.actors.removeAll { true }
        screenItems.clear()
        screenTexts.clear()
    }

    override fun startMusic() {
        music.play()
    }

    override fun stopMusic() {
        music.pause()
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
            val coordinates = Vector3(x, y, 0f)
            camera.unproject(coordinates)
            clickAnimationCoordinates = coordinates
            return false
        }
    }
}
