package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import helpers.InputHandler;
import ui.Button;
import ui.Slider;

// BG color #005a43

public class SettingsScreen extends ScreenAdapter {

    RunwayRunners game;
    Slider slider;
    Button help;
    Button confirm;
    Button reset;
    int[] range = {45000, 90000};

    public SettingsScreen(RunwayRunners game){
        this.game = game;
    }

    @Override
    public void show(){
        game.finger.touched = false;
        game.finger.touchX = 0;
        game.finger.touchY = 0;
        // Initialize Menu Buttons
        slider = new Slider(game.w * 0.1f, game.h*0.5f, 1600, 30, "Level Length", range);
        //help = new Button(game.w * 0.75f, game.h * 0.22f, 400, 100,"Back");
        confirm = new Button(game.w * 0.75f, game.h * 0.09f, 400, 100,"Confirm");
        reset = new Button(game.w * 0.3f, game.h * 0.09f, 575, 100,"RESET SCORES");

        Gdx.input.setInputProcessor(new InputHandler(game));
    }

    @Override
    public void render(float delta) {
        updateUI();
        Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.getData().setScale(1.0f, 1.0f);
        game.font.setColor(1, 1, 1, 1);
        game.batch.draw(game.menuSetting, 0, 0);
        renderUI();

//        game.batch.draw(game.logo, game.w * 0.5f - game.logo.getWidth()/2, game.h * 0.42f - game.logo.getHeight()/2);
//        game.font.draw(game.batch, "Bingaming Game WIP", game.w * .33f, game.h * 0.89f);
//        game.font.draw(game.batch, "Tap screen for next", game.w * .33f, game.h * 0.79f);
        game.batch.end();
    }


    public void renderUI(){
        game.batch.draw(slider.getSprite(), slider.getX(), slider.getY(), slider.getWidth(), slider.getHeight());
        game.batch.draw(slider.getSprite(), slider.getSlideX(), slider.getSlideY(), 50, 100);
        game.batch.draw(confirm.getSprite(), confirm.getX(), confirm.getY(), confirm.getWidth(), confirm.getHeight());
        game.batch.draw(reset.getSprite(), reset.getX(), reset.getY(), reset.getWidth(), reset.getHeight());
        game.font.draw(game.batch, slider.get_text(), slider.textX(), slider.textY());
        game.font.draw(game.batch, confirm.getButton_text(), confirm.textX(), confirm.textY());
        game.font.draw(game.batch, reset.getButton_text(), reset.textX(), reset.textY());
    }

    public void updateUI(){

        if(slider.check(game.finger.touchX, game.finger.touchY, game.finger.touched)){
            slider.slide(game.finger.touchX, game.finger.touchY, game.finger.touched);
        }

        game.levelLength = (int)slider.getSliderValue();


        confirm.check(game.finger.touchX, game.finger.touchY, game.finger.touched);
        if(!game.finger.touched && confirm.check(game.finger.touchX, game.finger.touchY, false)){
            game.setScreen(new TitleScreen(game));
        }

        reset.check(game.finger.touchX, game.finger.touchY, game.finger.touched);
        if(!game.finger.touched && reset.check(game.finger.touchX, game.finger.touchY, false)){
            game.prefs.clear();
            game.prefs.flush();
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

