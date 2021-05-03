package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import entities.Bird;
import entities.Plane;
import helpers.InputHandler;


public class GameScreen extends ScreenAdapter {

    GroupGame game;

    // variables for local gamestate
    // gamePhase = 0 for takeoff, 1 for inflight, 2 for landing
    private int gamePhase = 0;
    private float levelLength;
    private Plane plane;
    private ShapeRenderer shapeRenderer;
    private NumberFormat formatter = new DecimalFormat("#0.00");

    public static final float OBSTACLE_SPAWN_TIME = 5f;  //obstacle spawn time
    private Array<Bird> birds = new Array<Bird>(); // bird obstacle
    private float obstacleTimer;    // timer for obstacles



    public GameScreen(GroupGame game, float levelLength){
        this.game = game;
        this.levelLength = levelLength;
        this.plane = game.airplane;
        this.shapeRenderer = game.shapeRenderer;
//        birds.add(new Bird(Gdx.graphics.getWidth(), 1000, game.bird, plane));
        Gdx.input.setInputProcessor(new InputHandler(game));
    }

    @Override
    public void show(){
        // reset all gamestate variables
        // plane position
        // bird status
        // management trackers
        plane.x = 0;
        plane.y = 200;
        plane.xVel = 0;
        plane.yVel = 0;
        plane.xAcc = 0;
        plane.yAcc = 0;
        plane.lift = 0;
        plane.gravity = -1;
        plane.rot = 0;
        game.time = 0;
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(88/255f, 88/255f, 128/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font.setColor(1, 1, 1, 1);
        game.font.getData().setScale(1.0f, 1.0f);

        game.batch.begin();

        //draw world
        // Runway Start
        if(gamePhase == 0){
            //game.runway.flip(1, 1);
            game.batch.draw(game.runway.getTexture(), 0 - plane.x, 0 - plane.y + 200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), false, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - plane.x + game.runway.getWidth(), 0 - plane.y + 200);
        }
        // In the air
        if(gamePhase == 1){
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000, 0-plane.y+200);
            game.batch.draw(game.ground_loop.getTexture(), (0 - plane.x + game.runway.getWidth()) % 3000 + game.ground_loop.getWidth(), 0-plane.y+200);
        }
        // Runway End
        if(gamePhase == 2){
            game.batch.draw(game.runway.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200, game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), true, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - (plane.x - levelLength - game.runway.getWidth() - game.plane.getWidth() - 100), 0-plane.y+200);
        }

        // draw plane
        if(plane.y < 450){
            game.batch.draw(game.plane, 100, plane.y, (int)(100+game.plane.getWidth()/2), (int)(game.plane.getHeight()-44),
                    game.plane.getWidth(), game.plane.getHeight(), 1, 1, plane.rot);
        }
        if(plane.y >= 450){
            game.batch.draw(game.plane, 100, 450, (int)(100+game.plane.getWidth()/2), (int)(game.plane.getHeight()-44),
                    game.plane.getWidth(), game.plane.getHeight(), 1, 1, plane.rot);
        }


        //SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);

        //draw birds
        for(Bird b: birds){
            if(b != null){
                game.batch.draw(game.bird, b.getRenderX(), b.getRenderY(), 200,200);
            }
        }
        // draw ui
        game.font.draw(game.batch, "Time: " + parseTime(game.time), game.w*0.1f, game.h*0.1f);
        game.batch.draw(game.miniPlane, game.w*(float)(plane.x / (levelLength+game.runway.getWidth()*3)), game.h*0.86f);
        game.batch.draw(game.altMark, (float)(5), game.h*(float)((plane.y)/4000));

        // draw debug values
        //game.font.draw(game.batch, "Plane Sim WIP1", game.w * .1f, game.h * 0.89f);
        //game.font.draw(game.batch, "Tap left corners for accel, tap right corners for rotation", game.w * .1f, game.h * 0.79f);
        game.font.getData().setScale(0.7f, 0.7f);
        game.font.draw(game.batch, "vel x,y: " + plane.xVel + " " + plane.yVel, game.w * .05f, game.h * 0.7f);
        game.font.draw(game.batch, "acc x,y: " + plane.xAcc + " " + plane.yAcc, game.w * .05f, game.h * 0.65f);
        game.font.draw(game.batch, "rotation: " + plane.rot + " " + "", game.w * 0.5f, game.h * 0.6f);

        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.polygon(plane.getBoundingRect().getTransformedVertices());
        for(Bird bird : birds){
            shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
        }
        shapeRenderer.end();
    }
/*          // if draw in render(), no need for this block
    public void renderObs(float delta){

        update(delta);

        //clear screen
        // GdxUtils.clearScreen();

        renderDebugObs();

    }

    public void renderDebugObs(){

        renderer.setProjectMatrix

    }
*/
    public void update(float delta){
        game.time += 0.01666666;
        plane.update();
        updateObstacles(delta);
        // update gamestate flags
        // in-the-air
        if(plane.x > game.runway.getWidth()){
            gamePhase = 1;
        }
        // touch-down
        if(plane.x > game.runway.getWidth() + levelLength){
            gamePhase = 2;
        }
        // reached the end
        if(plane.x > game.runway.getWidth()*2 + levelLength){
            gamePhase = 0;
            game.setScreen(new EndScreen(game));
        }

    }

    private void updateObstacles(float delta) {

        for(Bird obstacle: birds){
            obstacle.update(delta);
        }

        createNewObstacle(delta);
    }

    private void createNewObstacle(float delta) {

        obstacleTimer += delta;      //delta
        for(Bird obstacle : birds){
            if(overlaps(plane.getBoundingRect(), obstacle.getBoundingCircle())){
                game.setScreen(new EndScreen(game));
            }
        }
        if(obstacleTimer >= OBSTACLE_SPAWN_TIME && gamePhase == 1){

//            float min = 0f;
//            float max = 12534f; //instead of the number it should be world width
//            float obstacleX = MathUtils.random(min,max);
//            float obstacleY = 152351f; // instead of number, it should be world height or swap X and Y

            birds.add(new Bird(Gdx.graphics.getWidth(), (float) (plane.y + Math.random() * (Gdx.graphics.getHeight())), game.bird, plane));


            obstacleTimer = 0f;
        }

    }

    public boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        Vector2 center=new Vector2(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    public String parseTime(float timing){
        return formatter.format(timing);
    }

}
