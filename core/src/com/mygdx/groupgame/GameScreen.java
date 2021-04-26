package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import entities.Plane;
import helpers.InputHandler;

public class GameScreen extends ScreenAdapter {

    GroupGame game;

    // variables for local gamestate
    // gamePhase = 0 for takeoff, 1 for inflight, 2 for landing
    private int gamePhase = 0;
    private float levelLength;
    private Plane plane;


    public GameScreen(GroupGame game, float levelLength){
        this.game = game;
        this.levelLength = levelLength;
        this.plane = game.airplane;
        Gdx.input.setInputProcessor(new InputHandler(game));
    }

    @Override
    public void show(){
        // reset all gamestate variables
        // plane position
        // bird status
        // management trackers
        plane.x = 0;
        plane.y = 200;
        plane.xVel = 0;
        plane.yVel = 0;
        plane.xAcc = 0;
        plane.yAcc = 0;
        plane.lift = 0;
        plane.gravity = -1;
        plane.rot = 0;
    }

    @Override
    public void render(float delta) {

        update();

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
            // grass loop
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000, 0-plane.y+200);
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000 + game.ground_loop.getWidth(), 0-plane.y+200);
        }
        if(gamePhase == 2){
            //reversed runway texture
            game.batch.draw(game.runway.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), true, false);
            // grass loop
            game.batch.draw(game.ground_loop.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200);
            game.batch.draw(game.ground_loop.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth()*2 -  game.plane.getWidth() - 100), 0-plane.y+200);
            game.batch.draw(game.ground_loop.getTexture(), plane.x - (plane.x - levelLength - game.plane.getWidth() - 100), 0-plane.y+200);
        }


        // draw plane
        // SPECIFIC Coordinates for Hit Detection

        int planeHitX1 = 100;
        int planeHitY1 = (int)plane.y;
        int planeHitX2 = (int)(100 + game.plane.getWidth());
        int planeHitY2 = (int)(plane.y+game.plane.getHeight());

        // specific phase for under 500y
        if(plane.y < 420){
            game.batch.draw(game.plane, 100, plane.y, (int)(100+game.plane.getWidth()/2), (int)(200-game.plane.getHeight()/2 - 44),
                    game.plane.getWidth(), game.plane.getHeight(), 1, 1, plane.rot);
        }
        if(plane.y >= 420){
            game.batch.draw(game.plane, 100, 420, (int)(100+game.plane.getWidth()/2), (int)(200-game.plane.getHeight()/2 - 44),
                    game.plane.getWidth(), game.plane.getHeight(), 1, 1, plane.rot);
        }


        //SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);

        // draw ui

        // draw debug values
        game.font.getData().setScale(0.7f, 0.7f);
        game.font.draw(game.batch, "x,y: " + plane.x + " " + plane.y, game.w * .05f, game.h * 0.6f);
        game.font.draw(game.batch, "vel x,y: " + plane.xVel + " " + plane.yVel, game.w * .05f, game.h * 0.7f);
        game.font.draw(game.batch, "acc x,y: " + plane.xAcc + " " + plane.yAcc, game.w * .05f, game.h * 0.65f);
        game.font.draw(game.batch, "rotation: " + plane.rot + " " + "", game.w * 0.5f, game.h * 0.6f);

        game.batch.end();
    }

    public void update(){
        plane.update();

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

}
