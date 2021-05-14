package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EndScreen extends ScreenAdapter {

    RunwayRunners game;
    float time;
    int condition;

    private NumberFormat formatter = new DecimalFormat("#0.0#");

    // conditions
    // 0 = win
    // 1 = fail health
    // 2 = fail crash

    public EndScreen(RunwayRunners game){
        this.game = game;
    }
    public EndScreen(RunwayRunners game, float time, int condition){
        this.game = game;
        this.time = time;
        this.condition = condition;
    }


    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                game.setScreen(new TitleScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        if(condition == 0) {
            Gdx.gl.glClearColor(88 / 255f, 22 / 255f, 22 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menu2, 0, 0);
            game.font.draw(game.batch, "You've made it to the end safely!", game.w * 0.1f, game.h * 0.70f);
            game.font.draw(game.batch, "Run time: " + parseTime(this.time), game.w * 0.1f, game.h * 0.60f);
            game.font.draw(game.batch, "Total Score: " + ((game.levelLength / this.time) * 2), game.w * 0.1f, game.h * 0.50f);
            game.batch.end();
        } else if(condition == 1){
            Gdx.gl.glClearColor(88/255f, 22/255f, 22/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menu2, 0, 0);
            game.font.draw(game.batch, "You've hit too many birds!", game.w * 0.1f, game.h * 0.70f);
            game.batch.end();
        } else if(condition == 2){
            Gdx.gl.glClearColor(88/255f, 22/255f, 22/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menu2, 0, 0);
            game.font.draw(game.batch, "You've crashed!", game.w * 0.1f, game.h * 0.70f);
            game.batch.end();
        } else if(condition == 3){
            Gdx.gl.glClearColor(88/255f, 22/255f, 22/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menu2, 0, 0);
            game.font.draw(game.batch, "You flew over the runway!!!", game.w * 0.1f, game.h * 0.70f);
            game.batch.end();
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public String parseTime(float timing){
        return formatter.format(timing);
    }

}
