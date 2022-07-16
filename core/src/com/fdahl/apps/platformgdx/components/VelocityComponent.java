package com.fdahl.apps.platformgdx.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float velocity = 0.0f;

    public VelocityComponent(float velocity) {
        this.velocity = velocity;
    }
}
