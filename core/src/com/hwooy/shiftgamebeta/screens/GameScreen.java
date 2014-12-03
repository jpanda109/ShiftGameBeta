package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;
import com.hwooy.shiftgamebeta.object_classes.Player;
import com.hwooy.shiftgamebeta.object_classes.Portal;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */

public class GameScreen extends ScreenAdapter {

    public World gameWorld;
    public ArrayList<ShiftObject> gameObjects;

    public Player gamePlayer;
    public Portal gamePortal;

    public GameScreen(int level_num)
    {

    }
}
