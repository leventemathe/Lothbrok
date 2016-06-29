package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.lothbrok.game.spriter.LibGdxDrawer;
import com.lothbrok.game.spriter.LibGdxLoader;

public class GameScreen extends AbstractScreen {

    private SCMLReader reader;
    private Data data;
    private Player yourPlayer;
    private Loader loader;
    private Drawer drawer;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public GameScreen (Game game) {
        super(game);
    }

    @Override
    public void show() {
        FileHandle handle = Gdx.files.internal("anim/circleAnim.scml");
        this.reader = new SCMLReader(handle.read());

        this.data = reader.getData();
        this.yourPlayer = new Player(data.getEntity(0));
        this.yourPlayer.setPosition(100,0);

        this.loader = new LibGdxLoader(data);
        this.loader.load(handle.file()); //Load all sprites

        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.drawer = new LibGdxDrawer(loader, batch, shapeRenderer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update the player
        yourPlayer.update();
        //and draw it:
        batch.begin();
        drawer.draw(yourPlayer);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //TODO
    }
}
