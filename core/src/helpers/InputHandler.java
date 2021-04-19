package helpers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.groupgame.GroupGame;

public class InputHandler extends InputAdapter {
    private GroupGame game;

    public InputHandler(GroupGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("x,y: " + screenX +  ", " + screenY);
        int renderY = game.h - screenY;
        game.finger.touchX = screenX;
        game.finger.touchY = renderY;
        game.finger.touched = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("x,y: " + screenX +  ", " + screenY);
        int renderY = game.h - screenY;
        game.finger.touched = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("x,y: " + screenX +  ", " + screenY);
        int renderY = game.h - screenY;
        game.finger.touchX = screenX;
        game.finger.touchY = renderY;
        game.finger.touched = true;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
