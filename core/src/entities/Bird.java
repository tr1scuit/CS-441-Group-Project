package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

public class Bird {

    private static final float BOUND_RADIUS = 100f;
    private static final float SIZE = 2*BOUND_RADIUS;

    private float x,y;

    private float xSpeed = 100f;

    private Circle bounds;

    public Circle getBoundingCircle(){
        return bounds;
    }

    public Bird(float x, float y){
        bounds = new Circle(x,y, BOUND_RADIUS);
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void update(float delta){
        setPosition((x - xSpeed * delta),y);
        Gdx.app.log("Bird","Bird X/Y\t" + x + "\t" + y);
    }

    public float getX(){return x;}

    public float getY(){return y;}

    private void updateBounds(){
        bounds.setPosition(x,y);
    }

    public float getWidth(){return SIZE;}


}
