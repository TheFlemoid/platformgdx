package com.fdahl.apps.platformgdx.objects;

import com.badlogic.gdx.Gdx;
import com.fdahl.apps.platformgdx.views.GameScreen;
import com.fdahl.apps.platformgdx.helper.BodyHelper;
import com.fdahl.apps.platformgdx.helper.Const;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Body body;
    private TextureAtlas characterAtlas;
    private Animation<TextureRegion> walkAnimation;
    private Sprite testPlayer;
    private float x;
    private float y;
    private float stateTime;

    public Player(float x, float y, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        characterAtlas = new TextureAtlas("sprites/green_alien_resized.atlas");
        testPlayer = characterAtlas.createSprite("alienGreen_front");
        walkAnimation = new Animation<TextureRegion>(1f/5f,
                characterAtlas.findRegions("alienGreen_walk"), PlayMode.LOOP);
        System.out.println("Regions found: " + characterAtlas.findRegions("alienGreen_walk").size);

        this.body = BodyHelper.createBody(x, y, testPlayer.getWidth(), testPlayer.getHeight(), false, 10000,
                gameScreen.getWorld());
        stateTime = 0f;
    }

    public void update() {
        x = (body.getPosition().x * Const.PPM) - (testPlayer.getWidth()/2);
        y = (body.getPosition().y * Const.PPM) - (testPlayer.getHeight()/2);
        testPlayer.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        testPlayer.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 100, 200);
    }

    public void dispose() {
        characterAtlas.dispose();
    }
}
