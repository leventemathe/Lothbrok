package com.lothbrok.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.screens.GameScreen;

public class Lothbrok extends Game {

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Assets.instance.init(new AssetManager());
		setScreen(new GameScreen(this));
	}

}
