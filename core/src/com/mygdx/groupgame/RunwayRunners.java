package com.mygdx.groupgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import java.awt.Shape;

import entities.Plane;

public class RunwayRunners extends Game {

	//initialize variables
	public Preferences prefs;

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	public BitmapFont smallFont;
	public int w, h;
	public Plane airplane;
	public float time;

	public class TouchInfo{
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
	}

	public TouchInfo finger;

	public Texture logo;
	public Texture menu1;
	public Texture menu2;
	public Sprite runway;
	public Sprite plane;
	public Sprite ground_loop;
	public Sprite miniPlane;
	public Sprite altMark;
	public Sprite bird;
	public static TextureAtlas atlas;
	public TextureRegion birdAtlas;
	public Animation<TextureRegion> birdAnimation;

	// Initialize the Game
	@Override
	public void create () {
		// initialize game variables
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		prefs = Gdx.app.getPreferences("GamePrefs");
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		finger = new TouchInfo();

		// Game Sprites
		bird = new Sprite(new Texture("bird.png"));
		runway = new Sprite(new Texture("runway_f.png"));
		plane = new Sprite(new Texture("airplane.png"));
		ground_loop = new Sprite(new Texture("grass_loop_f.png"));
		airplane = new Plane(this);
		atlas = new TextureAtlas(Gdx.files.internal("birdAnimation/bird.atlas"));
		TextureRegion birdFrames[] = {atlas.findRegion("bird1"),
				atlas.findRegion("bird2"),
				atlas.findRegion("bird3"),
				atlas.findRegion("bird4"),
				atlas.findRegion("bird5"),
				atlas.findRegion("bird6"),
				atlas.findRegion("bird7"),
				atlas.findRegion("bird8"),
				atlas.findRegion("bird9"),
		};
		birdAnimation = new Animation((float) 0.1, birdFrames);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// UI Sprites
		font = new BitmapFont(Gdx.files.internal("Roboto-100.fnt"));
		font.setColor(Color.WHITE);
		smallFont = new BitmapFont(Gdx.files.internal("Roboto-80.fnt"));
		smallFont.setColor(Color.WHITE);
		logo = new Texture("Bingaming500.png");
		menu1 = new Texture("MenuScreen1.png");
		menu2 = new Texture("MenuScreen2.png");

		miniPlane = new Sprite(new Texture("MiniPlane.png"));
		altMark = new Sprite(new Texture("altitudeMarker.png"));
		//spedometer = new Sprite(new Texture("spedometer.png"));
		//spedoNeedle = new Sprite("spedoNeedle.png");

		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		logo.dispose();
		font.dispose();
		smallFont.dispose();
	}
}
