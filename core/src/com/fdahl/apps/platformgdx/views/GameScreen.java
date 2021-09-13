package com.fdahl.apps.platformgdx.views;

import com.badlogic.gdx.Input;
import com.fdahl.apps.platformgdx.helper.Const;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.*;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.world = new World(new Vector2(0, 0), false);
        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        // Move camera origin to match character origin here, temp set to 0, 0, 0
        camera.position.set(new Vector3(0, 0, 0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //render things

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
    }
}
