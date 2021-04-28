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

import java.awt.Shape;

import entities.Plane;

public class GroupGame extends Game {

	public Preferences prefs;

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	public int w, h;
	public Plane airplane;

	public class TouchInfo{
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
	}

	public TouchInfo finger;

	public Texture logo;
	public Sprite runway;
	public Sprite plane;
	public Sprite ground_loop;
	//Sprite sky_loop;
	//Sprite cloud;
	public Sprite bird;

	@Override
	public void create () {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		prefs = Gdx.app.getPreferences("GamePrefs");

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("Roboto-100.fnt"));
		font.setColor(Color.WHITE);
		finger = new TouchInfo();
		logo = new Texture("Bingaming500.png");
		runway = new Sprite(new Texture("runway_f.png"));
		plane = new Sprite(new Texture("airplane.png"));
		ground_loop = new Sprite(new Texture("grass_loop_f.png"));
		airplane = new Plane(this);
		bird = new Sprite(new Texture("bird.png"));
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
