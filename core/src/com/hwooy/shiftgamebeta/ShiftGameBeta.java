package com.hwooy.shiftgamebeta;

import com.badlogic.gdx.Game;
import com.hwooy.shiftgamebeta.screens.ScreenManager;
import com.hwooy.shiftgamebeta.utils.God;

public class ShiftGameBeta extends Game {
	God god;
	ScreenManager screenManager;
	
	@Override
	public void create () {
		god = God.getInstance();
		screenManager = new ScreenManager(this);
	}

	@Override
	public void dispose() {
		god.dispose();
		super.dispose();
	}

}
