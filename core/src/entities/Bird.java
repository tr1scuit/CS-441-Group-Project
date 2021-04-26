package entities;

import com.badlogic.gdx.math.Circle;

public class Bird {

    private static final float BOUND_RADIUS = 0.3f;
    private static final float SIZE = 2*BOUND_RADIUS;

    private float x,y;

    private float xSpeed = 0.1f;

    private Circle bounds;

    public Bird(){
        bounds = new Circle(x,y, BOUND_RADIUS);

    }

    public void setPosition(float x, float y){

        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void update(){
        setPosition(x-xSpeed,y);

    }

    public float getX(){return x;}

    public float getY(){return y;}

    private void updateBounds(){
        bounds.setPosition(x,y);
    }

    public float getWidth(){return SIZE;}


}
