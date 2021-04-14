package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

// BG color #005a43

public class TitleScreen extends ScreenAdapter {

    GroupGame game;

    public TitleScreen(GroupGame game){
        this.game = game;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("screen touched, switching screens");
                System.out.println("x,y: " + screenX +  ", " + screenY);
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(1, 1, 1, 1);
        game.batch.draw(game.logo, Gdx.graphics.getWidth() * 0.5f - game.logo.getWidth()/2, Gdx.graphics.getHeight() * 0.42f - game.logo.getHeight()/2);
        game.font.draw(game.batch, "Bingaming Game WIP", Gdx.graphics.getWidth() * .33f, Gdx.graphics.getHeight() * 0.89f);
        game.font.draw(game.batch, "Tap screen for next", Gdx.graphics.getWidth() * .33f, Gdx.graphics.getHeight() * 0.79f);
        game.batch.end();
    }


/*
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
*/
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
