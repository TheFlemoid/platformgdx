package com.fdahl.apps.platformgdx;

import com.fdahl.apps.platformgdx.views.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatformGdx extends Game {
	public static PlatformGdx INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;

	public PlatformGdx() {
		INSTANCE = this;
	}

	public static PlatformGdx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlatformGdx();
		}

		return INSTANCE;
	}

	@Override
	public void create() {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		setScreen(new GameScreen(orthographicCamera));
	}
}
