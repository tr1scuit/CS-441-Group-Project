package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import entities.Bird;
import entities.Plane;
import helpers.InputHandler;


public class GameScreen extends ScreenAdapter {

    GroupGame game;

    // variables for local gamestate
    // gamePhase = 0 for takeoff, 1 for inflight, 2 for landing
    private int gamePhase = 0;
    private float levelLength;
    private Plane plane;

    public static final float OBSTACLE_SPAWN_TIME = 0.25f;  //obstacle spawn time
    private Array<Bird> birds = new Array<Bird>(); // bird obstacle
    private float obstacleTimer;    // timer for obstacles



    public GameScreen(GroupGame game, float levelLength){
        this.game = game;
        this.levelLength = levelLength;
        this.plane = game.airplane;
        Gdx.input.setInputProcessor(new InputHandler(game));
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(88/255f, 88/255f, 128/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(1, 1, 1, 1);
        game.font.getData().setScale(1.0f, 1.0f);
        game.font.draw(game.batch, "Plane Sim WIP1", game.w * .1f, game.h * 0.89f);
        game.font.draw(game.batch, "Tap left corners for accel, tap right corners for rotation", game.w * .1f, game.h * 0.79f);

        //draw world
        if(gamePhase == 0){
            //game.runway.flip(1, 1);
            game.batch.draw(game.runway.getTexture(), 0 - plane.x, 0 - plane.y + 200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), false, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - plane.x + game.runway.getWidth(), 0 - plane.y + 200);
        }
        if(gamePhase == 1){
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000, 0-plane.y+200);
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000 + game.ground_loop.getWidth(), 0-plane.y+200);
        }
        if(gamePhase == 2){
            game.batch.draw(game.runway.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), true, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200);
        }
/*
        //draw obstacles
            //draw bird
        for (Bird obstacle : birds){
            
        }

*/
        // draw plane
        game.batch.draw(game.plane, 100, 200, (int)(100+game.plane.getWidth()/2), (int)(200-game.plane.getHeight()/2 - 44),
                game.plane.getWidth(), game.plane.getHeight(), 1, 1, plane.rot);

        //SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);

        // draw ui

        // draw debug values
        game.font.getData().setScale(0.7f, 0.7f);
        game.font.draw(game.batch, "vel x,y: " + plane.xVel + " " + plane.yVel, game.w * .05f, game.h * 0.7f);
        game.font.draw(game.batch, "acc x,y: " + plane.xAcc + " " + plane.yAcc, game.w * .05f, game.h * 0.65f);
        game.font.draw(game.batch, "rotation: " + plane.rot + " " + "", game.w * 0.5f, game.h * 0.6f);

        game.batch.end();
    }
/*          // if draw in render(), no need for this block
    public void renderObs(float delta){

        update(delta);

        //clear screen
        // GdxUtils.clearScreen();

        renderDebugObs();

    }

    public void renderDebugObs(){

        renderer.setProjectMatrix

    }
*/
    public void update(float delta){
        plane.update();
        updateObstacles(delta);

        // update gamestate flags
        // in-the-air
        if(plane.x > game.runway.getWidth()){
            gamePhase = 1;
        }
        // touch-down
        if(plane.x > game.runway.getWidth() + levelLength){
            gamePhase = 2;
        }
        // reached the end
        if(plane.x > game.runway.getWidth()*2 + levelLength){
            gamePhase = 0;
            game.setScreen(new EndScreen(game));
        }

    }

    private void updateObstacles(float delta) {

        for(Bird obstacle: birds){
            obstacle.update();
        }

        createNewObstacle(delta);
    }

    private void createNewObstacle(float delta) {

        obstacleTimer += delta;      //delta

        if(obstacleTimer >= OBSTACLE_SPAWN_TIME){

            float min = 0f;
            float max = 12534f; //instead of the number it should be world width
            float obstacleX = MathUtils.random(min,max);
            float obstacleY = 152351f; // instead of number, it should be world height or swap X and Y

            Bird obstacle = new Bird();
           obstacle.setPosition(obstacleX,obstacleY);

            birds.add (obstacle);
            obstacleTimer = 0f;
        }

    }

}
