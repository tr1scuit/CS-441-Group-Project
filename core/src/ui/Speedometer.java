package ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import entities.Plane;

public class Speedometer {

    private Sprite speedometer;
    private Sprite speedometer_needle;
    private float x, y, width, height, rotation;
    private float nx, ny, nw, nh, n_rotation;
    private Plane plane;

    public Speedometer(float x, float y, float width, float height, Plane vehicle){
        speedometer = new Sprite(new Texture("speedometer.png"));
        speedometer_needle = new Sprite(new Texture("speedometer_needle.png"));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.plane = vehicle;
        rotation = 120;
        n_rotation = 0;
    }


    public void update(){
        rotation = 120f - 150f * ((plane.xVel)/70);
    }

    public float getRot() {
        return rotation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public Texture getMeter() {
        return speedometer.getTexture();
    }

    public Texture getNeedle() {
        return speedometer_needle.getTexture();
    }

    public void reset() {
        this.rotation = 120;
    }
}
