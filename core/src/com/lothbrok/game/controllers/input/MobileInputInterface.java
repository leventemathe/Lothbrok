package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.MobileInterfaceConstants;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.controllers.PauseController;
import com.lothbrok.game.controllers.PlayerController;

public class MobileInputInterface implements Disposable{

    private static final String TAG = MobileInputInterface.class.getSimpleName();

    private Assets assets;

    private Stage stage;
    private Skin skin;

    private Touchpad touchPad;
    private ImageButton btnJump;
    private ImageButton btnAttack;
    private ImageButton btnPause;

    private PlayerController playerController;
    private PauseController pauseController;

    public MobileInputInterface(PlayerController playerController, PauseController pauseController, SpriteBatch batch, Assets assets) {
        this.assets = assets;
        stage = new Stage(new ExtendViewport(Resolution.instance.getMenuWidth(),
                                          Resolution.instance.getMenuHeight()), batch);
        skin = assets.getMobileControlsSkin();
        this.playerController = playerController;
        this.pauseController = pauseController;
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        buildTouchPad();
        buildJumpButton();
        buildAttackButton();
        buildPauseButton();

        float padding = Resolution.instance.getPaddingMedium();

        rootTable.add(touchPad).expand().bottom().left().padBottom(padding).padLeft(padding);

        rootTable.add(btnPause).expand().center().bottom().padBottom(padding);

        HorizontalGroup btnGroup = buildButtonsLayer();
        rootTable.add(btnGroup).expand().bottom().right().padBottom(padding).padRight(padding);
    }

    private void buildTouchPad() {
        Touchpad.TouchpadStyle style = skin.get(MobileInterfaceConstants.SKIN_TOUCHPAD, Touchpad.TouchpadStyle.class);
        touchPad = new Touchpad(10f, style);
        touchPad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerController.moveRight(false);
                playerController.moveLeft(false);
                if(touchPad.getKnobPercentX() > 0f) {
                    playerController.moveRight(true);
                } else if(touchPad.getKnobPercentX() < 0f) {
                    playerController.moveLeft(true);
                }
            }
        });
    }

    private void buildJumpButton() {
        ImageButton.ImageButtonStyle style = skin.get(MobileInterfaceConstants.SKIN_BTN_JUMP, ImageButton.ImageButtonStyle.class);
        btnJump = new ImageButton(style);
        btnJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerController.jump(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playerController.jump(false);
            }
        });
    }

    private void buildAttackButton() {
        ImageButton.ImageButtonStyle style = skin.get(MobileInterfaceConstants.SKIN_BTN_ATTACK, ImageButton.ImageButtonStyle.class);
        btnAttack = new ImageButton(style);
        btnAttack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerController.attack(true);
                return true;
            }
        });
    }

    private void buildPauseButton() {
        ImageButton.ImageButtonStyle style = skin.get(MobileInterfaceConstants.SKIN_BTN_PAUSE, ImageButton.ImageButtonStyle.class);
        btnPause = new ImageButton(style);
        btnPause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pauseController.setPaused(true);
                return true;
            }
        });
    }

    private HorizontalGroup buildButtonsLayer() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(Resolution.instance.getPaddingMedium());
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
