package entities;


import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.groupgame.GroupGame;

public class Plane {
    public float x = 0, y = 200;
    public float xVel = 0, yVel = 0;
    public float xAcc = 0, yAcc = 0;
    public float lift = 0;
    public float gravity = -2;
    public float rot = 0;
    private Polygon boundingRect;
    private float width, height;

    private GroupGame game;
    public Plane(GroupGame game){
        this.game = game;
        this.width = game.plane.getWidth();
        this.height = game.plane.getHeight();
        boundingRect = new Polygon(new float[]{0,0, width,0, width,height, 0,height});
        boundingRect.setPosition(100,200);
        // rotation point for bounding rectangle is lower (because of plane center of gravity)
        boundingRect.setOrigin((int)(100+game.plane.getWidth()/2), (int)(200-game.plane.getHeight()/2 - 44));
    }

    public Polygon getBoundingRect(){
        return boundingRect;
    }
    public void update(){
        // Horizontal Movement

        if(game.finger.touched && game.finger.touchY > game.h/2 && game.finger.touchX < game.w / 2) {
            xAcc = 1f;
        }
        else if(game.finger.touched && game.finger.touchY < game.h/2 && game.finger.touchX < game.w / 2 ){
            xAcc = -1f;
        }
        else {
            xAcc = 0f;
        }


        // Rotation
        if(game.finger.touched && game.finger.touchY > game.h/2 && game.finger.touchX > game.w / 2) {
            rot += 1;
        }
        if(game.finger.touched && game.finger.touchY < game.h/2 && game.finger.touchX > game.w / 2 ){
            rot -= 1;
        }

        // only apply lift if a certain speed
//        if(xVel > 60 && rot > 0){
        if(rot > 0){
            yAcc = rot / 90;
        }
        if(rot < 0){
            yAcc = rot / 90;
        }


        // update plane xy physics ( acceleration limit )
//        if(xAcc > 1){
//            xAcc = 1;
//        }
//        if(xAcc < -1){
//            xAcc = -1;
//        }
        if(yAcc > 1){
            yAcc = 1;
        }
        if(yAcc < -1){
            yAcc = -1;
        }

        // nose down
        if( rot%360 < (0) && rot%360 > (-50)){
        }
        // nose up
        if( rot%360 >= (0) && rot%360 < (50)){

        }


        xVel += xAcc;
        yVel += yAcc;
        if(xVel > 70){
            xVel = 70;
//            yAcc += 0.5;
        }
        if(xVel < 68){
            yAcc -= 0.5;
        }
        if(xVel < 0){
            xVel = 0;
        }
        if(yVel > 10){
            yVel = 10;
        }
        if(yVel < -10) {
            yVel = -10;
        }

        x += xVel;
        y += yVel;
        if(x < 0){ x = 0;}
        if(y < 200) { y = 200; yVel = 0; }

        boundingRect.setRotation(rot);
        // debug line
//        System.out.println("plane x, y:" + x + ", " + y);
    }
}