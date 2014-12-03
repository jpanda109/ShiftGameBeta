package com.hwooy.shiftgamebeta;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hwooy.shiftgamebeta.screens.ScreenManager;
import com.hwooy.shiftgamebeta.utils.Settings;

public class ShiftGameBeta extends Game {
	Settings settings;
	ScreenManager screenManager;
	
	@Override
	public void create () {
		settings = Settings.getInstance();
		screenManager = new ScreenManager(this);
	}

	@Override
	public void dispose() {
		settings.dispose();
	}

}
