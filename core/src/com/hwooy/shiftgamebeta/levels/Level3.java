package com.hwooy.shiftgamebeta.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hwooy.shiftgamebeta.models.Block;

/**
 * Created by jason on 11/20/14.
 * Level 3
 */
public class Level3 extends Level {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    float tileSize;

    public Level3() {
        super();

        portal.position.set(5, 20);

        tiledMap = new TmxMapLoader().load("android/assets/levels/EarlyLevel.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        TiledMapTileLayer layer1 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
        TiledMapTileLayer layer2 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 2");
        TiledMapTileLayer layer3 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 3");
        tileSize = layer1.getTileWidth();

        for (int row = 0; row < layer1.getWidth(); row++) {
            for (int col = 0; col < layer1.getHeight(); col++) {
                TiledMapTileLayer.Cell cell = layer1.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }

        for (int row = 0; row < layer2.getWidth(); row++) {
            for (int col = 0; col < layer2.getHeight(); col++) {
                TiledMapTileLayer.Cell cell = layer2.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }

        for (int row = 0; row < layer3.getWidth(); row++) {
            for (int col = 0; col < layer3.getHeight(); col++) {
                TiledMapTileLayer.Cell cell = layer3.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }
    }
}
