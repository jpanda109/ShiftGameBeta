package com.hwooy.shiftgamebeta.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hwooy.shiftgamebeta.ShiftGameBeta;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 320;
		config.addIcon("player/player_front.png", Files.FileType.Internal);
		new LwjglApplication(new ShiftGameBeta(), config);
	}
}
