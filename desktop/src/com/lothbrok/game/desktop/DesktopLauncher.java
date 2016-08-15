package com.lothbrok.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lothbrok.game.Lothbrok;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
        //TODO enable vsync, or maybe add a setting
        config.vSyncEnabled = false;
        //TODO unlimied fps (0) -> animation doesnt play, fix it
        config.foregroundFPS = 120; // Setting to 0 disables foreground fps throttling
        config.backgroundFPS = 120; // Setting to 0 disables background fps throttling
		new LwjglApplication(new Lothbrok(), config);
	}
}
