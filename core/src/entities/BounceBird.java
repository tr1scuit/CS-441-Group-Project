package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BounceBird extends Bird {

    private static float BOUNCEHEIGHT = 170;
    public double runtime = 0f;


    public BounceBird(float x, float y, Sprite birdSprite, Plane plane) {
        super(x, y, birdSprite, plane);

    }

    @Override
    public void update(float delta){
        runtime += delta;

        if(plane.xVel == 0){
            setPosition((x + (xSpeed * delta)), (float) (0-plane.y+staticY + BOUNCEHEIGHT*Math.sin(runtime)));
        }
        else
            setPosition((x + (xSpeed * delta) - (plane.xVel * 5 * delta)), (float) (0-plane.y+staticY+BOUNCEHEIGHT*Math.sin(runtime)));
        Gdx.app.log("Bird","Bird X/Y\t" + x + "\t" + y);
    }

}
