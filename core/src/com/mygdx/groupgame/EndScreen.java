package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EndScreen extends ScreenAdapter {

    RunwayRunners game;
    float score;
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
        this.score = ((game.levelLength / this.time) * 2);
        updateScores(this.score);
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
            game.font.draw(game.batch, "You've made it to the end safely!", game.w * 0.1f, game.h * 0.76f);
            game.font.draw(game.batch, "Run time: " + parseTime(this.time), game.w * 0.1f, game.h * 0.66f);
            game.font.draw(game.batch, "Total Score: " + this.score, game.w * 0.1f, game.h * 0.56f);
            renderScores();
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

    private void updateScores(float score) {
        System.out.println("UPDATiNG SCORES with" + score);
        float s1 = game.prefs.getFloat("topScore1", 0.0f);
        System.out.println(game.prefs.getFloat("topScore1"));
        float s2 = game.prefs.getFloat("topScore2", 0.0f);
        System.out.println(game.prefs.getFloat("topScore2"));
        float s3 = game.prefs.getFloat("topScore3", 0.0f);
        System.out.println(game.prefs.getFloat("topScore3"));

        if(score > s1){
            float temp = s1;
            s1 = score;
            s2 = temp;
        }else if(score > s2){
            float temp = s2;
            s2 = score;
            s3 = temp;
        } else if (score > s3){
            s3 = score;
        } else{
            return;
        }
        game.prefs.putFloat("topScore1", s1);
        game.prefs.putFloat("topScore2", s2);
        game.prefs.putFloat("topScore3", s3);
        game.prefs.flush();
        System.out.println("UPDATED SCORES!!!");
        System.out.println(game.prefs.getFloat("topScore1"));
        System.out.println(game.prefs.getFloat("topScore2"));
        System.out.println(game.prefs.getFloat("topScore3"));
    }


    private void renderScores() {
        game.font.getData().setScale(1.0f, 1.0f);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Top Scores:", game.w*0.1f, game.h*0.47f);
        //game.batch, "Total Score: " + , game.w * 0.1f, game.h * 0.50f
        game.font.draw(game.batch, "1. " + game.prefs.getFloat("topScore1", 0.0f), game.w * 0.1f, game.h * 0.37f);
        game.font.draw(game.batch, "2. " + game.prefs.getFloat("topScore2", 0.0f), game.w * 0.1f, game.h * 0.27f);
        game.font.draw(game.batch, "3. " + game.prefs.getFloat("topScore3", 0.0f), game.w * 0.1f, game.h * 0.17f);
    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public String parseTime(float timing){
        return formatter.format(timing);
    }

}
