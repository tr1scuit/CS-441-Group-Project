package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends ScreenAdapter {

    GroupGame game;

    // variables for local gamestate
    // gamePhase = 0 for takeoff, 1 for inflight, 2 for landing
    private int gamePhase = 0;
    private float levelLength;
    // The Plane's World Coordinates and physics
    private float x = 0, y = 200;
    private float xVel = 0, yVel = 0;
    private float xAcc = 0, yAcc = 0;
    private float rot = 0;
    //


    public GameScreen(GroupGame game, float levelLength){
        this.game = game;
        this.levelLength = levelLength;
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {

        update();

        Gdx.gl.glClearColor(88/255f, 88/255f, 128/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Plane Sim WIP1", game.w * .33f, game.h * 0.89f);
        game.font.draw(game.batch, "Tap high for accel, tap low for decel", game.w * .33f, game.h * 0.79f);

        //draw world
        if(gamePhase == 0){
            //game.runway.flip(1, 1);
            game.batch.draw(game.runway.getTexture(), 0 - x, 0 - y + 200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), false, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - x + game.runway.getWidth(), 0 - y + 200);
        }
        if(gamePhase == 1){
            game.batch.draw(game.ground_loop.getTexture(), (0 - x + game.runway.getWidth()) % 3000, 0-y+200);
            game.batch.draw(game.ground_loop.getTexture(), (0 - x + game.runway.getWidth()) % 3000 + game.ground_loop.getWidth(), 0-y+200);
        }
        if(gamePhase == 2){
            game.batch.draw(game.runway.getTexture(), 0 - (x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), true, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - (x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0);
        }



        // draw plane
        game.batch.draw(game.plane.getTexture(), 100, 200);

        // draw ui

        game.batch.end();
    }

    public void update(){

        // read inputs
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("x,y: " + screenX +  ", " + screenY);
                int renderY = game.h - screenY;
                if(renderY > 540){
                    xAcc += 0.5;
                    if(xAcc > 2){
                        xAcc = 2;
                    }
                } else if(renderY < 540){
                    xAcc -= 0.5;
                    if(xAcc < -1){
                        xAcc = -1;
                    }
                }
                return true;
            }
        });

        // update physics
        xVel += xAcc;
        yVel += yAcc;
        if(xVel > 70){
            xVel = 70;
            yAcc = 1;
        }
        if(xVel < 0){
            xVel = 0;
        }

        x += xVel;
        y += yVel;
        if(x < 0){ x = 0;}


        // update gamestate flags
        // in-the-air
        if(x > game.runway.getWidth()){
            gamePhase = 1;
        }
        // touch-down
        if(x > game.runway.getWidth() + levelLength){
            gamePhase = 2;
        }
        // reached the end
        if(x > game.runway.getWidth()*2 + levelLength){
            gamePhase = 0;
            game.setScreen(new EndScreen(game));
        }
        System.out.println("plane x, y:" + x + ", " + y);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}

