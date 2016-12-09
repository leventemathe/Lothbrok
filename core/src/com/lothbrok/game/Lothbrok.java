package com.lothbrok.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.loadingscreens.EssentialsLoadingScreen;

public class Lothbrok extends Game {

	Assets assets;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assets = new Assets();
		setScreen(new EssentialsLoadingScreen(assets));
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		screen.dispose();
	}
}
