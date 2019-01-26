package com.room.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.room.game.stage1.Stage1
import com.badlogic.gdx.scenes.scene2d.Stage as LibgdxStage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Timer
import com.room.game.core.*

class RoomEscape : ApplicationAdapter(), StageUiHandler {

    private lateinit var batch: SpriteBatch
    private lateinit var currentStage: Stage
    private lateinit var libgdxStage: LibgdxStage
    private lateinit var camera: Camera
    private lateinit var viewport: FitViewport
    private lateinit var font: BitmapFont
    private lateinit var music: Music
    private lateinit var assetManager: AssetManager

    private lateinit var eventHandler: EventHandler

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = FitViewport(1920f, 1080f, camera)
        libgdxStage = LibgdxStage(viewport)
        font = BitmapFont()
        assetManager = AssetManager()

        currentStage = Stage1(this)
        eventHandler = EventHandler(listOf(currentStage as EventHandler.Listener))

        Gdx.input.inputProcessor = libgdxStage

        setMusic()
    }

    private fun setMusic() {
        assetManager.apply {
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f) //TODO: change to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        libgdxStage.act()
        libgdxStage.draw()
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
        assetManager.dispose()
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
        if (!screenItems.containsKey(screenItem)) {
            return
        }
        val actor = screenItems.getValue(screenItem)
        val action = Actions.fadeOut(0.1f)
        actor.first.addAction(action)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(actor.first, true)
                screenItems.remove(screenItem)
                actor.first.clear()
                actor.second.dispose()
            }
        }, 0.1f)
    }

    override fun addTemporaryScreenText(screenText: ScreenText) {
        val style = Label.LabelStyle()
        style.font = font
        val label = Label(screenText.text, style)
        label.setPosition(screenText.x, screenText.y)
        label.setFontScale(5f, 5f)
        label.setColor(0f, 0f, 0f, 1f)
        label.addAction(Actions.fadeIn(0.2f))

        val bgtexture = Texture("background_white.jpg")
        val bgTextureRegion = TextureRegion(bgtexture)
        val bgTextureRegionDrawable = TextureRegionDrawable(bgTextureRegion)
        val background = Image(bgTextureRegionDrawable).apply {
            setPosition(0f,0f)
            setSize(1920f, 200f)
        }

        val bordertexture = Texture("background_black.jpg")
        val borderTextureRegion = TextureRegion(bordertexture)
        val borderTextureRegionDrawable = TextureRegionDrawable(borderTextureRegion)
        val border = Image(borderTextureRegionDrawable).apply {
            setPosition(0f,200f)
            setSize(1920f, 8f)
        }

        libgdxStage.addActor(background)
        libgdxStage.addActor(label)
        libgdxStage.addActor(border)

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                label.addAction(Actions.fadeOut(0.2f))
                background.addAction(Actions.fadeOut(0.2f))
                border.addAction(Actions.fadeOut(0.2f))
            }
        }, 2f)

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(label, false)
                libgdxStage.actors.removeValue(background, false)
                libgdxStage.actors.removeValue(border, false)
                label.clear()
                background.clear()
                border.clear()
                bgtexture.dispose()
                bordertexture.dispose()
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
        if (!screenTexts.containsKey(screenText)) {
            return
        }
        val actor = screenTexts.getValue(screenText)
        actor.addAction(Actions.fadeOut(0.1f))
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(actor, false)
                actor.clear()
                screenTexts.remove(screenText)
            }

        }, 0.1f)
    }

    override fun removeAllScreenElements() {
        libgdxStage.actors.removeAll { true }
        screenItems.forEach {
            it.value.first.clear()
            it.value.second.dispose()
        }
        screenTexts.forEach {
            it.value.clear()
        }
        screenItems.clear()
        screenTexts.clear()
    }

    override fun fadeOut() {
        val texture = Texture("blackout.jpg")
        val textureRegion = TextureRegion(texture)
        val textureRegionDrawable = TextureRegionDrawable(textureRegion)
        val image = Image(textureRegionDrawable)
        image.addAction(Actions.sequence(Actions.fadeOut(0.4f)))
        image.zIndex = 1000001
        libgdxStage.addActor(image)

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                libgdxStage.actors.removeValue(image, false)
                image.clear()
                texture.dispose()
            }
        }, 0.41f)
    }

    override fun startMusic() {
        music.play()
    }

    override fun stopMusic() {
        music.pause()
    }

    override fun playSound(sound: Sound) {
        if (!currentStage.musicOn) {
            return
        }

        assetManager.apply {
            val musicName = sound.resourceId
            load(musicName, Music::class.java)
            finishLoading()
            get<Music>(musicName).play()
            Timer.schedule(object : Timer.Task() {
                override fun run() {
                    get<Music>(musicName).dispose()
                }
            }, 7f)
        }
    }
}
