package com.hwooy.shiftgamebeta.viewer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.models.GameObject;
import com.hwooy.shiftgamebeta.models.TerrainBlock;

import java.util.ArrayList;

/**
 * Created by jason on 11/14/14.
 * THis is a renderer for levels
 */
public class LevelRenderer {

    Level level;
    OrthographicCamera cam;

    ShapeRenderer debugRenderer;

    /**
     * constructor that renders the given level
     * @param level to be rendered
     */
    public LevelRenderer(Level level) {
        this.level = level;
        this.cam = new OrthographicCamera(Level.LEVEL_WIDTH, Level.LEVEL_HEIGHT);
        this.cam.position.set(Level.LEVEL_WIDTH / 2f, level.LEVEL_HEIGHT / 2f, 0);
        this.cam.update();
        this.debugRenderer = new ShapeRenderer();
    }

    /**
     * renders everything in this level
     */
    public void render() {
        renderObjects();
    }

    /**
     * renders objects in this level
     */
    private void renderObjects() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        renderTerrain();
        debugRenderer.end();
    }

    /**
     * renders the terrain in this level
     */
    private void renderTerrain() {
        ArrayList<TerrainBlock> objects = level.hellTerrainObjects;
        int size = objects.size();
        for (int i = 0; i < size; i++) {
            GameObject object = objects.get(i);
            drawDebug(object);
        }
    }

    /**
     * debugging method
     * @param object object to be drawn
     */
    private void drawDebug(GameObject object) {
        debugRenderer.setColor(new Color(Color.RED));
        debugRenderer.rect(object.bounds.getX(), object.bounds.getY(), object.bounds.getWidth(), object.bounds.getHeight());
    }
}
