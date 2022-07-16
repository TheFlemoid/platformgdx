package com.fdahl.apps.platformgdx.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input;

import com.fdahl.apps.platformgdx.components.PositionComponent;
import com.fdahl.apps.platformgdx.components.VelocityComponent;

public class ControlledMovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velocityMapper = ComponentMapper.getFor(VelocityComponent.class);

    /**
     * ashley callback, called whenever an entity is added to the engine.  This modifies the entities ImmuntableArray
     * to include all components that have position and velocity components.
     *
     * @param engine Games single Engine object
     */
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for(int i=0; i<entities.size(); i++) {
            Entity entity = entities.get(i);
            PositionComponent position = positionMapper.get(entity);
            VelocityComponent velocity = velocityMapper.get(entity);

            if(Gdx.input.isKeyPressed(Input.Keys.H)) {
                position.x += velocity.velocity * deltaTime;
            } else if(Gdx.input.isKeyPressed(Input.Keys.G)) {
                position.x -= velocity.velocity * deltaTime;
            }
        }
    }
}