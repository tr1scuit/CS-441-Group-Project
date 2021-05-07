package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class HelpScreen extends ScreenAdapter {

    RunwayRunners game;
    int panel = 0;
    boolean wasTouched = false;

    public HelpScreen(RunwayRunners game) {
        this.game = game;
    }


    // Start How-To
    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        update();
        if(panel == 0){
            Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.begin();
            game.font.getData().setScale(1.0f, 1.0f);
            game.font.setColor(0, 0, 0, 1);
            game.batch.draw(game.menuHelp1, 0, 0);
            game.batch.end();
        }
        if(panel == 1){
            Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.begin();
            game.font.getData().setScale(1.0f, 1.0f);
            game.font.setColor(0, 0, 0, 1);
            game.batch.draw(game.menuHelp2, 0, 0);
            game.batch.end();
        }
        if(panel == 2){
            Gdx.gl.glClearColor(0, 97/255f, 67/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.begin();
            game.font.getData().setScale(1.0f, 1.0f);
            game.font.setColor(0, 0, 0, 1);
            game.batch.draw(game.menuHelp1, 0, 0);
            game.batch.end();
        }
        if(panel == 3){
            game.setScreen(new TitleScreen(game));
        }

    }

    public void update(){
        if(game.finger.touched && !wasTouched){
            wasTouched = true;
        }
        if(!game.finger.touched && wasTouched){
            wasTouched = false;
            panel++;
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
