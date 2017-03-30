package com.lothbrok.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lothbrok.game.Lothbrok;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
        config.vSyncEnabled = true;
		config.foregroundFPS = 30;
		config.addIcon("icon/icon_128.png", FileType.Internal);
		config.addIcon("icon/icon_32.png", FileType.Internal);
		config.addIcon("icon/icon_16.png", FileType.Internal);
		new LwjglApplication(new Lothbrok(), config);
	}
}
