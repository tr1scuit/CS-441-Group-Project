package com.mygdx.groupgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Bird;
import entities.BounceBird;
import entities.Plane;
import entities.Wind;
import helpers.InputHandler;
import ui.Speedometer;


public class GameScreen extends ScreenAdapter {

    RunwayRunners game;

    // variables for local gamestate
    // gamePhase = 0 for takeoff, 1 for inflight, 2 for landing
    public static int gamePhase = 0;
    private float levelLength;
    private Plane plane;
    private ShapeRenderer shapeRenderer;
    private NumberFormat formatter = new DecimalFormat("#0.00");
    private float runtime = 0f;
    private float groundTime = 0;
    private int damage = 0;
    private int maxSpeed = 70;
    public static final float OBSTACLE_SPAWN_TIME = 5f;  //obstacle spawn time
    private List<Bird> birds = new ArrayList<Bird>(); // bird obstacle
    private Array<Wind> winds = new Array<>();
    private float obstacleTimer;    // timer for obstacles
    Speedometer speedometer;





    public GameScreen(RunwayRunners game, float levelLength){
        this.game = game;
        this.levelLength = levelLength;
        this.plane = game.airplane;
        plane.crashed = false;
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
        gamePhase = 0;
        groundTime = 0;
        damage = 0;
        speedometer = new Speedometer((game.w * 0.833f), 30, 250, 250, plane);
        speedometer.reset();
        plane.x = 0;
        plane.y = 200;
        plane.xVel = 0;
        plane.yVel = 0;
        plane.xAcc = 0;
        plane.yAcc = 0;
        plane.lift = 0;
        plane.rot = 0;
        plane.crashed = false;
        plane.maxSpeed = 70;
        game.time = 0;
        birds.clear();
    }

    @Override
    public void render(float delta) {

        update(delta);
        runtime += delta;
        Gdx.gl.glClearColor(88/255f, 88/255f, 128/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font.setColor(1, 1, 1, 1);
        game.font.getData().setScale(1.0f, 1.0f);

        game.batch.begin();

        //draw world
        // Runway Start
        if(gamePhase == 0){
            //game.runway.flip(1, 1);
            game.batch.draw(game.runway.getTexture(), 0 - plane.x, 0 - plane.y + 200, 2*game.runway.getWidth(), game.runway.getHeight(),
                    (int)game.runway.getX(), (int)game.runway.getY(), (int)game.runway.getWidth(), (int)game.runway.getHeight(), false, false);
            game.batch.draw(game.ground_loop.getTexture(), 0 - plane.x + 2*game.runway.getWidth(), 0 - plane.y + 200);
//            game.batch.draw(game.ground_loop.getTexture(), 0 - plane.x + 2 * game.runway.getWidth(), 0 - plane.y + 200);
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
                game.batch.draw(game.birdAnimation.getKeyFrame(runtime), b.getRenderX(), b.getRenderY(), 200,200);
            }
        }
        //draw wind
        for(Wind w : winds){
            if(w != null){
                game.batch.draw(game.wind, w.getRenderX(),w.getRenderY(), 200, 200);
            }
        }

        
        // draw ui
        game.font.draw(game.batch, "Time: " + parseTime(game.time), game.w*0.1f, game.h*0.1f);
        game.batch.draw(game.miniPlane, game.w*(float)(plane.x / (levelLength+game.runway.getWidth()*3)), game.h*0.86f);
        game.batch.draw(game.altMark, (float)(5), game.h*(float)((plane.y)/2100));
        game.batch.draw(speedometer.getMeter(), speedometer.getX(), speedometer.getY(), speedometer.getX(), speedometer.getY(), 250, 250, 1, 1, 0, 0, 0, 250, 250, false, false);
        game.batch.draw(speedometer.getNeedle(), speedometer.getX(), speedometer.getY(), 125, 125, 250, 250, 1, 1, speedometer.getRot(), 0, 0, 250, 250, false, false);

        // draw debug values
        //game.font.draw(game.batch, "Plane Sim WIP1", game.w * .1f, game.h * 0.89f);
        //game.font.draw(game.batch, "Tap left corners for accel, tap right corners for rotation", game.w * .1f, game.h * 0.79f);
        /*
        game.font.getData().setScale(0.7f, 0.7f);
        game.font.draw(game.batch, "vel x,y: " + plane.x + " " + plane.y, game.w * .05f, game.h * 0.75f);
        game.font.draw(game.batch, "vel x,y: " + plane.xVel + " " + plane.yVel, game.w * .05f, game.h * 0.7f);
        game.font.draw(game.batch, "acc x,y: " + plane.xAcc + " " + plane.yAcc, game.w * .05f, game.h * 0.65f);
        game.font.draw(game.batch, "rotation: " + plane.rot + " " + "", game.w * 0.5f, game.h * 0.6f);
*/
        game.batch.end();
/*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.polygon(plane.getBoundingRect().getTransformedVertices());
        for(Bird bird : birds){
            shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
        }
        for(Wind wind : winds){
            shapeRenderer.circle(wind.getBoundingCircle().x, wind.getBoundingCircle().y, wind.getBoundingCircle().radius);
        }
        shapeRenderer.end();
*/
    }

    public void update(float delta){
        game.time += 0.01666666;
        plane.update();
        speedometer.update();
        updateObstacles(delta);
        // update gamestate flags
        // in-the-air

        // clear states
        if(gamePhase == 0 || gamePhase == 2){
            birds.clear();
            winds.clear();
        }


        // fail conditions

        if(plane.crashed){
            gamePhase = 0;
            game.setScreen(new EndScreen(game, game.time, 2));
        }
        if(damage >= 3){
            game.setScreen(new EndScreen(game, game.time, 1));
        }

        if(plane.x > 2*game.runway.getWidth()){
            gamePhase = 1;
        }
        // touch-down
        if(plane.x > game.runway.getWidth() + levelLength){
            gamePhase = 2;
        }
        // reached the end
        if(plane.x > game.runway.getWidth()*2 + levelLength){
            gamePhase = 0;
            if(plane.y > 250){
                game.setScreen(new EndScreen(game, game.time, 3));
            } else{
                game.setScreen(new EndScreen(game, game.time, 0));
            }

        }

    }

    private void updateObstacles(float delta) {

        for(Bird obstacle: birds){
            obstacle.update(delta);
        }
        for(Wind obstacleWind: winds){
            obstacleWind.update(delta);
        }

        createNewObstacle(delta);

    }

    private void createNewObstacle(float delta) {

        obstacleTimer += delta;      //delta

        Iterator<Bird> itr = birds.iterator();
        Bird obstacle = null;
        while(itr.hasNext()){
            obstacle = itr.next();
            if((obstacle != null) && overlaps(plane.getBoundingRect(), obstacle.getBoundingCircle())){
                damage++;
                itr.remove();
            }
        }

        Iterator<Wind> itr2 = winds.iterator();
        Wind obstacleW = null;
        while(itr2.hasNext()){
            obstacleW = itr2.next();
            if((obstacleW != null) && overlaps(plane.getBoundingRect(), obstacleW.getBoundingCircle())){
                plane.xVel -= 17;
                itr2.remove();
            }
        }


        if(obstacleTimer >= OBSTACLE_SPAWN_TIME && gamePhase == 1 && birds.size() <= 2){

//            float min = 0f;
//            float max = 12534f; //instead of the number it should be world width
//            float obstacleX = MathUtils.random(min,max);
//            float obstacleY = 152351f; // instead of number, it should be world height or swap X and Y

//            birds.add(new Bird(Gdx.graphics.getWidth(), (float) (plane.y + Math.random() * (Gdx.graphics.getHeight())), game.bird, plane));
            birds.add(new BounceBird(Gdx.graphics.getWidth(), (float) (plane.y + Math.random() * (Gdx.graphics.getHeight())), game.bird, plane));
            winds.add(new Wind(Gdx.graphics.getWidth()+((float)Math.random()*500), (float) (plane.y + Math.random() * (Gdx.graphics.getHeight())), game.wind, plane));

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
