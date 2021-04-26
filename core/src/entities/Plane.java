package entities;

import com.mygdx.groupgame.GroupGame;

public class Plane {
    public float x = 0, y = 200;
    public float xVel = 0, yVel = 0;
    public float xAcc = 0, yAcc = 0;
    public float lift = 0;
    public float gravity = -2;
    public float rot = 0;

    private GroupGame game;
    public Plane(GroupGame game){
        this.game = game;
    }

    public void update(){
        // Horizontal Movement
        if(game.finger.touched && game.finger.touchY > game.h/2 && game.finger.touchX < game.w / 2) {
            xAcc += 0.15;
        }
        if(game.finger.touched && game.finger.touchY < game.h/2 && game.finger.touchX < game.w / 2 ){
            xAcc -= 0.15;
        }
        // Rotation
        if(game.finger.touched && game.finger.touchY > game.h/2 && game.finger.touchX > game.w / 2) {
            yAcc += 0.15;
        }
        if(game.finger.touched && game.finger.touchY < game.h/2 && game.finger.touchX > game.w / 2 ){
            yAcc -= 0.15;
        }

        // update plane xy physics ( acceleration limit )
        if(xAcc > 1f){
            xAcc = 1f;
        }
        if(xAcc < -1f){
            xAcc = -1f;
        }
        if(yAcc > 1f){
            yAcc = 1f;
        }
        if(yAcc < -1f){
            yAcc = -1f;
        }

        // nose down
        if( rot%360 < (0) && rot%360 > (-50)){
        }
        // nose up
        if( rot%360 >= (0) && rot%360 < (50)){

        }


        xVel += xAcc;
        yVel += yAcc;
        if(xVel > 70f){
            xVel = 70f;
            //yAcc += 0.5;
        }
        if(xVel < 68f){
            //yAcc -= 0.5;
        }
        if(xVel < 0f){
            xVel = 0;
        }
        if(yVel > 10f){
            yVel = 10f;
        }
        if(yVel < -30f) {
            yVel = -30f;
        }

        x += xVel;
        y += yVel;
        if(x < 0){ x = 0;}
        if(y < 200) { y = 200; yVel = 0; }

        // debug line
        System.out.println("plane x, y:" + x + ", " + y);
    }
}
