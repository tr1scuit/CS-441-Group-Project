package ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class Button {

    private Sprite button;
    private Sprite button_press;
    private String button_text;
    private boolean isPressed;
    private float x, y, width, height;

    public Button(float x, float y, float width, float height, String button_text){
        isPressed = false;
        button = new Sprite(new Texture("button.png"));
        button_press = new Sprite(new Texture("button_press.png"));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.button_text = button_text;
    }

    public void press(){
        isPressed = true;
    }

    public void unPress(){
        isPressed = false;
    }

    public Sprite getSprite(){
        if(isPressed){
            return button_press;
        }
        else{
            return button;
        }
    }


    public Sprite getButton() {
        return button;
    }

    public Sprite getButton_press() {
        return button_press;
    }

    public String getButton_text() {
        return button_text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float textX(){
        return (x+width*0.07f);
    }

    public float textY(){
        return ((y+height*0.82f));
    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean check(float touchX, float touchY, boolean touchStatus) {
        if(touchStatus &&
            touchX > x && touchX < x+width &&
            touchY > y && touchY < y+height){
            press();
            return true;
        }
        else{
            unPress();
            return false;
        }
    }
}
