package com.lothbrok.game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.ashley.entity.EntityFactory;
import com.lothbrok.game.ashley.systems.MapRendererSystem;


public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();

    public static SpriteBatch batch;
    public static Camera camera;
    public static Viewport viewport;

    public static Engine engine;

    @Override
    public void show() {
        super.show();
        setupRendering();
        setupEngine();
    }

    private void setupRendering() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, camera);
    }

    private void setupEngine() {
        engine = new Engine();
        engine.addEntity(EntityFactory.createMap());
        engine.addSystem(new MapRendererSystem());
    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);
        viewport.apply();
        engine.update(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
/*
    //M
    private GameModel gameModel;

    //V
    private GameRenderer gameRenderer;
    //TODO polimorphism instead of if statements
    private MobileInputInterface mobileInputInterface;
    private Box2DDebugRenderer box2DDebugRenderer;

    //C
    private InputProcessor inputProcessor;
    private Controller controller;

    @Override
    public void show() {
        super.show();
        TiledMap map = Assets.instance.getMap(1);
        gameModel = new GameModel(map);

        gameRenderer = new GameRenderer(gameModel);
        box2DDebugRenderer = new Box2DDebugRenderer();

        controller = new Controller(gameRenderer.getExtendedCamera());
        mobileInputInterface = new MobileInputInterface(controller);
        inputProcessor = new PCInput(controller);
        //inputProcessor = mobileInputInterface.getStage();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameRenderer.render(deltaTime);
        mobileInputInterface.render(deltaTime);
        box2DDebugRenderer.render(gameModel.getWorld(), gameRenderer.getExtendedCamera().getCamera().combined);
        super.render(deltaTime);
    }

    public void update(float deltaTime) {
        gameModel.update(deltaTime);
        controller.control(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameRenderer.resize(width, height);
        mobileInputInterface.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        mobileInputInterface.dispose();
        super.dispose();
    }
    */
}
