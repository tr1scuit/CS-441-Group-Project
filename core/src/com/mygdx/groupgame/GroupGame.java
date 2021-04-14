package com.mygdx.groupgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GroupGame extends Game {

	Preferences prefs;

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	int w, h;

	class TouchInfo{
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
	}
	TouchInfo finger;

	Texture logo;

	@Override
	public void create () {

		prefs = Gdx.app.getPreferences("GamePrefs");

		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("Roboto-100.fnt"));
		font.setColor(Color.WHITE);
		finger = new TouchInfo();
		logo = new Texture("Bingaming500.png");
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		logo.dispose();
		font.dispose();
	}
}
