package com.fdahl.apps.platformgdx.helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.fdahl.apps.platformgdx.views.GameScreen;

public class TileMapHelper {
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps/map0.tmx");
        // Get map object entities from Tmx file
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
        parseMapObjects(tiledMap.getLayers().get("background").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    /**
     * Parses the TileMap, searching for TiledMapTileMapObjects in the 'background' layer.
     * NOTE: This must be called after setupMap.
     * @return TiledMapTileMapObject array of background tiles
     */
    public TiledMapTileMapObject[] setupBackground() {
        MapObjects backgroundObjects = tiledMap.getLayers().get("background").getObjects();
        TiledMapTileMapObject[] backgroundTileObjects = new TiledMapTileMapObject[backgroundObjects.getCount()];

        int i=0;
        for(MapObject mapObject : backgroundObjects) {
            if(mapObject instanceof TextureMapObject) {
                backgroundTileObjects[i] = (TiledMapTileMapObject)mapObject;
                i++;
            }
        }
        return backgroundTileObjects;
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for(MapObject mapObject : mapObjects) {
            if(mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i=0; i<vertices.length/2; i++) {
            Vector2 current = new Vector2(vertices[i*2]/Const.PPM, vertices[i*2+1]/Const.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
