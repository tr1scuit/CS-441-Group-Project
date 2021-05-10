package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;

public class Wind {

    private static final float BOUND_RADIUS = 100f;
    private static final float SIZE = 2*BOUND_RADIUS;

    private static Sprite windSprite;
    private float x,y,staticY;

    private float xSpeed = 30f;

    private Circle boundWind;
    private Plane plane;

    public Circle getBoundingCircle(){
        return boundWind;
    }


    public Wind(float x, float y, Sprite windSprite, Plane plane){
        boundWind = new Circle(x,y,BOUND_RADIUS);
        this.x = x;
        this.staticY = y;
        this.windSprite = windSprite;
        this.plane = plane;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void update(float delta){
        if(plane.xVel == 0){
            setPosition((x + (xSpeed * delta)),0-plane.y+staticY);
        }
        else
            setPosition((x + (xSpeed * delta) - (plane.xVel * 5 * delta)),0-plane.y+staticY);
        Gdx.app.log("Wind","Wind X/Y\t" + x + "\t" + y);
    }

    // returns the absolute x, y coordinates of the bird
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    // return the render-offset x, y coordinates of the bird
    public float getRenderX(){
        return (this.getBoundingCircle().x - 100);
    }
    public float getRenderY(){
        return (this.getBoundingCircle().y - 100);
    }

    private void updateBounds(){
        boundWind.setPosition(x,y);
    }

    public float getWidth(){return SIZE;}


}