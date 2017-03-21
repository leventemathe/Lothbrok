package com.lothbrok.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lothbrok.game.Lothbrok;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
        //config.vSyncEnabled = true;
		config.foregroundFPS = 60;
		new LwjglApplication(new Lothbrok(), config);
	}
}
