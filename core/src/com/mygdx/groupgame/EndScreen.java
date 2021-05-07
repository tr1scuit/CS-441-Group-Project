package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class EndScreen extends ScreenAdapter {

    RunwayRunners game;

    public EndScreen(RunwayRunners game){
        this.game = game;
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
        Gdx.gl.glClearColor(88/255f, 22/255f, 22/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(1, 1, 1, 1);
        game.batch.draw(game.menu2, 0, 0);
        //game.font.draw(game.batch, "Bingaming End screen", game.w * .33f, game.h * 0.89f);
        //game.font.draw(game.batch, "", game.w * .33f, game.h * 0.79f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
