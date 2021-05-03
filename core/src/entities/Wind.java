package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public class Wind {

    private static final float BOUND_RADIUS = 100f;
    private static final float SIZE = 2*BOUND_RADIUS;


    private static Sprite windSprite;
    private float x,y;

    private float xSpeed = 100f;

    private Circle bounds;

    public Circle getBoundingCircle(){
        return bounds;
    }

    public Wind(float x, float y, Sprite birdSprite){
        bounds = new Circle(x,y, BOUND_RADIUS);
        this.x = x;
        this.y = y;
        this.windSprite = windSprite;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void update(float delta){
        setPosition((x - xSpeed * delta),y);
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
        return (this.getBoundingCircle().x - this.windSprite.getWidth()/2);
    }
    public float getRenderY(){
        return (this.getBoundingCircle().y - this.windSprite.getHeight()/2);
    }

    private void updateBounds(){
        bounds.setPosition(x,y);
    }

    public float getWidth(){return SIZE;}


}
