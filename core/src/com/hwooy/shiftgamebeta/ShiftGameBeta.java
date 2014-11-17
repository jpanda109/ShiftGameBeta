package com.hwooy.shiftgamebeta;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hwooy.shiftgamebeta.screens.ScreenManager;
import com.hwooy.shiftgamebeta.screens.StartScreen;

public class ShiftGameBeta extends Game {
	SpriteBatch batch;
	Texture img;
	ScreenManager screenManager;
	
	@Override
	public void create () {
		screenManager = new ScreenManager(this);
		//screenManager.setScreen(ScreenManager.Screens.START);
		//setScreen(new StartScreen(this));
	}
}
