package entities;


import com.badlogic.gdx.math.Polygon;
import com.mygdx.groupgame.RunwayRunners;

public class Plane {
    public float x = 0, y = 200;
    public float xVel = 0, yVel = 0;
    public float xAcc = 0, yAcc = 0;
    public float lift = 0;
    public float gravity = -2;
    public float rot = 0;
    private Polygon boundingRect;
    private float width, height;

    private RunwayRunners game;
    public Plane(RunwayRunners game){
        this.game = game;
        this.width = game.plane.getWidth();
        this.height = game.plane.getHeight();
        //new hitbox
        boundingRect = new Polygon(new float[]{104,36, 27,159, 49,164, 115,96, 178,73, 548,72, 600,42, 544,18, 175,22});
        //old hitbox
        //boundingRect = new Polygon(new float[]{0,0, width,0, width,height, 0,height});
        boundingRect.setPosition(100,this.y);
        // rotation point for bounding rectangle is lower (because of plane center of gravity)
        boundingRect.setOrigin((int)(100+game.plane.getWidth()/2), (int)(this.y-(game.plane.getHeight()/2)*0.3f));
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
            rot += 0.5;
            if(rot > 75){
                rot = 75;
            }
        }
        if(game.finger.touched && game.finger.touchY < game.h/2 && game.finger.touchX > game.w / 2 ){
            rot -= 0.5;
            if(rot < -75){
                rot = -75;
            }
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

        // update bounding box
        if(this.y < 450){
            boundingRect.setPosition(100,this.y);
            boundingRect.setOrigin((int)(100+game.plane.getWidth()/2), (int)(game.plane.getHeight())-44);
        }
        if(this.y >= 450){
            boundingRect.setPosition(100,450);
            boundingRect.setOrigin((int)(100+game.plane.getWidth()/2), (int)(game.plane.getHeight())-44);
        }
        boundingRect.setRotation(rot);
        // debug line
//        System.out.println("plane x, y:" + x + ", " + y);
    }
}