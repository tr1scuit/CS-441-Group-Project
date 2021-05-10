package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import helpers.InputHandler;
import ui.Button;

// BG color #005a43

public class TitleScreen extends ScreenAdapter {

    RunwayRunners game;
    Button start;
    Button help;
    Button settings;

    public TitleScreen(RunwayRunners game){
        this.game = game;
    }

    @Override
    public void show(){
        game.finger.touched = false;
        game.finger.touchX = 0;
        game.finger.touchY = 0;
        // Initialize Menu Buttons
        start = new Button(game.w * 0.75f, game.h * 0.35f, 400, 100, "Start");
        help = new Button(game.w * 0.75f, game.h * 0.22f, 400, 100,"Help");
        settings = new Button(game.w * 0.75f, game.h * 0.09f, 400, 100,"Settings");

        Gdx.input.setInputProcessor(new InputHandler(game));
    }

    @Override
    public void render(float delta) {
        updateUI();
        Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.getData().setScale(1.0f, 1.0f);
        game.font.setColor(0, 0, 0, 1);
        game.batch.draw(game.menu1, 0, 0);
        renderUI();

//        game.batch.draw(game.logo, game.w * 0.5f - game.logo.getWidth()/2, game.h * 0.42f - game.logo.getHeight()/2);
//        game.font.draw(game.batch, "Bingaming Game WIP", game.w * .33f, game.h * 0.89f);
//        game.font.draw(game.batch, "Tap screen for next", game.w * .33f, game.h * 0.79f);
        game.batch.end();
    }


    public void renderUI(){
        game.batch.draw(start.getSprite(), start.getX(), start.getY(), start.getWidth(), start.getHeight());
        game.batch.draw(help.getSprite(), help.getX(), help.getY(), help.getWidth(), help.getHeight());
        game.batch.draw(settings.getSprite(), settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
        game.font.draw(game.batch, start.getButton_text(), start.textX(), start.textY());
        game.font.draw(game.batch, help.getButton_text(), help.textX(), help.textY());
        game.font.draw(game.batch, settings.getButton_text(), settings.textX(), settings.textY());
    }

    public void updateUI(){

        start.check(game.finger.touchX, game.finger.touchY, game.finger.touched);
        if(!game.finger.touched && start.check(game.finger.touchX, game.finger.touchY, false)){
            game.setScreen(new GameScreen(game, 90000));
        }

        help.check(game.finger.touchX, game.finger.touchY, game.finger.touched);
        if(!game.finger.touched && help.check(game.finger.touchX, game.finger.touchY, false)){
            game.setScreen(new HelpScreen(game));
        }

        settings.check(game.finger.touchX, game.finger.touchY, game.finger.touched);
        if(!game.finger.touched && settings.check(game.finger.touchX, game.finger.touchY, false)){
            game.setScreen(new GameScreen(game, 20000));
        }
    }


/*
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
*/
//    @Override
//    public void hide() {
//        Gdx.input.setInputProcessor(null);
//    }


}

