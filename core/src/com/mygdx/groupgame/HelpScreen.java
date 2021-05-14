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
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menuHelp1, 0, 0);
            game.font.draw(game.batch, "Make it to the other runway as fast as possible", game.w*0.1f, game.h*0.65f);
            game.font.draw(game.batch, "without running into too many obstacles", game.w*0.1f, game.h*0.55f);
            game.font.draw(game.batch, "or crashing on the ground!", game.w*0.1f, game.h*0.45f);
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
            game.font.setColor(1, 1, 1, 1);
            game.batch.draw(game.menuHelp1, 0, 0);
            game.font.draw(game.batch, "- Hitting birds damages your plane", game.w*0.1f, game.h*0.70f);
            game.font.draw(game.batch, "- Hitting wind slows down your plane", game.w*0.1f, game.h*0.60f);
            game.font.draw(game.batch, "- Higher altitudes = more speed!", game.w*0.1f, game.h*0.50f);
            game.font.setColor(0.8f, 1, 0.8f, 1);
            game.font.draw(game.batch, "- Faster clear time with longer level = higher score", game.w*0.1f, game.h*0.40f);
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
