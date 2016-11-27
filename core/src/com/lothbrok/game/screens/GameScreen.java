package com.lothbrok.game.screens;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();

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
