package com.fdahl.apps.platformgdx.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdahl.apps.platformgdx.components.PositionComponent;
import com.fdahl.apps.platformgdx.components.RenderableComponent;
import com.fdahl.apps.platformgdx.components.SpriteComponent;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private SpriteBatch batch;

    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class,
                                                    SpriteComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for(int i=0; i<entities.size(); i++) {
            Entity entity = entities.get(i);

            SpriteComponent scom = entity.getComponent(SpriteComponent.class);
            PositionComponent pcom = entity.getComponent(PositionComponent.class);
            batch.draw(scom.sprite.getTexture(), pcom.x, pcom.y);
        }
    }
}
