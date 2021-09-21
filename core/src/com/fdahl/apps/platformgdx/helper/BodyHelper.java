package com.fdahl.apps.platformgdx.helper;

import com.fdahl.apps.platformgdx.helper.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyHelper {
    public static Body createBody(final float x, final float y, final float width, final float height,
                                  final boolean isStatic, final float density, final World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic == false ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/Const.PPM, y/Const.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Const.PPM, height/2/Const.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public static Body loadBodyFromDef(final float x, final float y, final String name, final String filePath,
                                       final boolean isStatic, final float density, final float scale, final World world) {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(filePath));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic == false ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/Const.PPM, y/Const.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;

        loader.attachFixture(body, name, fixtureDef, scale);
        return body;
    }
}
