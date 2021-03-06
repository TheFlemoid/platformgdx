package com.fdahl.apps.platformgdx.views;

import com.fdahl.apps.platformgdx.helper.Const;
import com.fdahl.apps.platformgdx.helper.TileMapHelper;
import com.fdahl.apps.platformgdx.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private TiledMapTileMapObject[] backgroundObjects;

    // Test code
    private Player testPlayer;

    private int mapXCenter;
    private int mapYCenter;

    public GameScreen(OrthographicCamera camera) {
        World.setVelocityThreshold(0f);

        this.camera = camera;
        this.viewport = new ExtendViewport(900, 700, camera);
        this.world = new World(new Vector2(0, -20.0f), false);
        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        this.backgroundObjects = tileMapHelper.setupBackground();
        testPlayer = new Player(200, 300, this);

        mapXCenter=500;
        mapYCenter=100;
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        testPlayer.update();

        // Center camera on player
        mapXCenter = (int)testPlayer.getPosition().x;
        mapYCenter = (int)testPlayer.getPosition().y;

        // If centering camera on player causes screen to go below world, then set the camera to world height
        if(mapYCenter < (viewport.getScreenHeight()/2)) {
            mapYCenter = viewport.getScreenHeight()/2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom-=0.02;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom+=0.02;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void cameraUpdate() {
        // Move camera origin to match character origin here, temp set to 0, 0, 0
        camera.position.set(new Vector3(mapXCenter, mapYCenter, 0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the background before anything else, and disable blending for the background
        batch.disableBlending();
        batch.begin();

        for(TiledMapTileMapObject backgroundObject : backgroundObjects) {
            TextureRegion textureRegion = backgroundObject.getTile().getTextureRegion();
            float scaleX = backgroundObject.getScaleX();
            float scaleY = backgroundObject.getScaleY();
            float xPos = backgroundObject.getX();
            float yPos = backgroundObject.getY();

            batch.draw(textureRegion, xPos, yPos, backgroundObject.getOriginX() * scaleX,
                    backgroundObject.getOriginY() * scaleY, textureRegion.getRegionWidth() * scaleX,
                    textureRegion.getRegionHeight() * scaleY, 1f, 1f, 0f);
        }

        batch.end();
        batch.enableBlending();

        // Render the map before we render the sprite batch
        orthogonalTiledMapRenderer.render();

        batch.begin();
        //render things
        testPlayer.render(batch);

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public World getWorld() {
        return world;
    }
}