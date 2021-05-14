package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Slider {

    private Sprite button;
    private Sprite button_press;
    private String text;
    private boolean isPressed;
    private float x, y, width, height;
    private float slidex, slidey;
    private float sliderValue;
    private int[] range;


    private NumberFormat formatter = new DecimalFormat("#0.00");

    public Slider(float x, float y, float width, float height, String text, int[] range){
        isPressed = false;
        button = new Sprite(new Texture("button.png"));
        button_press = new Sprite(new Texture("button_press.png"));
        this.x = x;
        sliderValue = (range[0] + ((float)x/(x+width))*(range[1]-range[0]));
        this.y = y;
        this.slidex = x;
        this.slidey = y - 25;
        this.width = width;
        this.height = height;
        this.text = text;
        this.range = range;
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

    public String get_text() {
        return (text + ": " + parse(sliderValue));
    }

    public void slide(float prog, float whatever, boolean beingDragged){
        if(beingDragged){
            slidex = (prog);
            sliderValue = range[0] + ((float)prog/(x+width))*(range[1]-range[0]);
        }
    }

    public float getX() {
        return x;
    }

    public float getSlideX(){
        return slidex;
    }
    public float getSlideY(){
        return slidey;
    }

    public float getSliderValue(){
        return (int)sliderValue;
    }

    public float getY() {
        return y;
    }

    public float textX(){
        return (x);
    }

    public float textY(){
        return ((y+150));
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
        else if(!touchStatus &&
                touchX > x && touchX < x+width &&
                touchY > y && touchY < y+height) {
            unPress();
            return true;
        }else{
            unPress();
            return false;
        }
    }

    public String parse(float sliderValue){
        return (formatter.format(sliderValue/20000f) + " km");
    }
}
