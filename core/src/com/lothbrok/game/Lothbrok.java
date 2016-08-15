package com.lothbrok.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.loadingscreens.EssentialsLoadingScreen;

public class Lothbrok extends Game {

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new EssentialsLoadingScreen());
	}
}
