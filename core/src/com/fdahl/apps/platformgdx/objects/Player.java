package com.fdahl.apps.platformgdx.objects;

import com.fdahl.apps.platformgdx.views.GameScreen;
import com.fdahl.apps.platformgdx.helper.BodyHelper;
import com.fdahl.apps.platformgdx.helper.Const;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
    private float velX;
    private float velY;
    private float stateTime;
    private Vector2 oldLinearVelocity;
    private MovementState movementState = MovementState.STANDING;
    private Facing facingDirection = Facing.RIGHT;
    private TextureRegion currentFrame;
    private Sound jumpSound;
    private static final float ANIM_FPS = 1f/7f;
    private static final int SPEED = 6;

    public Player(float x, float y, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump4.ogg"));
        characterAtlas = new TextureAtlas("sprites/green_alien_resized.atlas");
        testPlayer = characterAtlas.createSprite("alienGreen_front");
        walkAnimation = new Animation<TextureRegion>(ANIM_FPS,
                characterAtlas.findRegions("alienGreen_walk"), PlayMode.LOOP);
        body = BodyHelper.loadBodyFromDef(x, y, "GreenAlien", "bodies/GreenAlien.json", false,
                10000, 2f, gameScreen.getWorld());
        stateTime = 0f;
    }

    public void update() {
        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movementState = MovementState.STANDING;
            velX = 0;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movementState = MovementState.WALKING;
            velX = 0;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movementState = MovementState.WALKING;
            facingDirection = Facing.RIGHT;
            velX = 1;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movementState = MovementState.WALKING;
            facingDirection = Facing.LEFT;
            velX = -1;
        }

        oldLinearVelocity = body.getLinearVelocity();
        body.setLinearVelocity(velX * SPEED, oldLinearVelocity.y);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            jumpSound.play();
            velY = 2;
            oldLinearVelocity = body.getLinearVelocity();
            body.setLinearVelocity(oldLinearVelocity.x, velY * SPEED);
        }

        x = (body.getPosition().x * Const.PPM);
        y = (body.getPosition().y * Const.PPM);
        testPlayer.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        //testPlayer.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();

        if(movementState == MovementState.WALKING) {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else if(movementState == MovementState.STANDING) {
            currentFrame = characterAtlas.findRegion("alienGreen_stand");
        }

        if(facingDirection == Facing.LEFT) {
            batch.draw(currentFrame, x + currentFrame.getRegionWidth(), y,
                    currentFrame.getRegionWidth() * -1, currentFrame.getRegionHeight());
        } else {
            batch.draw(currentFrame, x, y);
        }
    }

    public void dispose() {
        characterAtlas.dispose();
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    enum MovementState {
        WALKING,
        STANDING
    }

    enum Facing {
        RIGHT,
        LEFT
    }
}
