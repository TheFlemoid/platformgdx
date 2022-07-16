package com.fdahl.apps.platformgdx.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.fdahl.apps.platformgdx.helper.Const;
import com.fdahl.apps.platformgdx.components.RenderableComponent;
import com.fdahl.apps.platformgdx.components.PositionComponent;
import com.fdahl.apps.platformgdx.components.SpriteComponent;
import com.fdahl.apps.platformgdx.components.VelocityComponent;
import com.fdahl.apps.platformgdx.systems.ControlledMovementSystem;
import com.fdahl.apps.platformgdx.systems.RenderSystem;

public class EntityManager {
    private Engine engine;
    private Entity testEntity;

    public EntityManager(Engine engine, SpriteBatch batch) {
        this.engine = engine;

        ControlledMovementSystem cms = new ControlledMovementSystem();
        RenderSystem rs = new RenderSystem(batch);

        engine.addSystem(cms);
        engine.addSystem(rs);

        // Test entity
        testEntity = new Entity();
        testEntity.add(new PositionComponent(200, 300))
                  .add(new VelocityComponent(150f))
                  .add(new SpriteComponent(new Texture(Gdx.files.internal("sprites/alienGreen_stand.png"))))
                  .add(new RenderableComponent());
        engine.addEntity(testEntity);
    }

    public void update(float delta) {
        engine.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            testEntity.remove(VelocityComponent.class);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            testEntity.remove(RenderableComponent.class);
        }
    }
}
