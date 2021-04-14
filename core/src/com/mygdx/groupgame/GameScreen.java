package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends ScreenAdapter {

    GroupGame game;

    public GameScreen(GroupGame game){
        this.game = game;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("x,y: " + screenX +  ", " + screenY);
                game.setScreen(new EndScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(10/255f, 22/255f, 88/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Bingaming Game Main Loop", Gdx.graphics.getWidth() * .33f, Gdx.graphics.getHeight() * 0.89f);
        game.font.draw(game.batch, "Tap screen for next", Gdx.graphics.getWidth() * .33f, Gdx.graphics.getHeight() * 0.79f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
