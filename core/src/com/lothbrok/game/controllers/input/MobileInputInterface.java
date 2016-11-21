package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.utils.ScreensConstants;

public class MobileInputInterface implements Disposable{

    private static final String TAG = MobileInputInterface.class.getSimpleName();

    private Stage stage;
    private Skin skin;

    private Touchpad touchPad;
    private ImageButton btnJump;
    private ImageButton btnAttack;

    private InputToControllerProcessor inputProcessor;

    public MobileInputInterface(InputToControllerProcessor inputProcessor) {
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH,
                                          ScreensConstants.VIEWPORT_MENU_HEIGHT));
        skin = Assets.instance.getMobileControlsSkin();
        this.inputProcessor = inputProcessor;
        rebuildStage();
    }

    //TODO a padding erteket kiszervezni
    private void rebuildStage() {
        stage.clear();
        stage.setDebugAll(true);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        buildTouchPad();
        buildJumpButton();
        buildAttackButton();

        Container<Touchpad> touchpadContainer = buildTouchPadLayer();
        rootTable.add(touchpadContainer).expand().bottom().left().padBottom(50f).padLeft(50f);

        HorizontalGroup btnGroup = buildButtonsLayer();
        rootTable.add(btnGroup).expand().bottom().right().padBottom(50f).padRight(50f);
    }

    private void buildTouchPad() {
        Touchpad.TouchpadStyle style = skin.get("default", Touchpad.TouchpadStyle.class);
        touchPad = new Touchpad(10f, style);
    }

    private void buildJumpButton() {
        ImageButton.ImageButtonStyle style = skin.get("jump", ImageButton.ImageButtonStyle.class);
        btnJump = new ImageButton(style);
        btnJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputProcessor.jump(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inputProcessor.jump(false);
            }
        });
    }

    private void buildAttackButton() {
        ImageButton.ImageButtonStyle style = skin.get("attack", ImageButton.ImageButtonStyle.class);
        btnAttack = new ImageButton(style);
        btnAttack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputProcessor.attack(true);
                return true;
            }
        });
    }

    private Container<Touchpad> buildTouchPadLayer() {
        Container<Touchpad> result = new Container<>();
        result.setActor(touchPad);
        return result;
    }

    private HorizontalGroup buildButtonsLayer() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(50f);
        result.addActor(btnJump);
        result.addActor(btnAttack);
        return result;
    }

    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
