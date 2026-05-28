package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MiniGame extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    BitmapFont font;

    Rectangle player;
    Rectangle food;
    Rectangle enemy;

    int score = 0;

    float speed = 5;

    boolean gameOver = false;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        font = new BitmapFont();

        // Player
        player = new Rectangle();
        player.x = 100;
        player.y = 100;
        player.width = 50;
        player.height = 50;

        // Food
        food = new Rectangle();
        spawnFood();

        // Enemy
        enemy = new Rectangle();
        enemy.x = 400;
        enemy.y = 300;
        enemy.width = 50;
        enemy.height = 50;
    }

    private void spawnFood() {

        food.width = 40;
        food.height = 40;

        food.x = MathUtils.random(0, 760);
        food.y = MathUtils.random(0, 560);
    }

    @Override
    public void render() {

        // Background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!gameOver) {

            // Movement
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.x -= speed;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.x += speed;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.y += speed;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.y -= speed;
            }

            // Boundaries
            if(player.x < 0) player.x = 0;
            if(player.y < 0) player.y = 0;
            if(player.x > 750) player.x = 750;
            if(player.y > 550) player.y = 550;

            // Enemy movement
            enemy.x -= 2;

            if(enemy.x < 0) {
                enemy.x = 800;
                enemy.y = MathUtils.random(0, 550);
            }

            // Food collision
            if(player.overlaps(food)) {

                score++;

                speed += 0.5f;

                System.out.println("Score: " + score);
                spawnFood();

                // Prevent instant multiple collision
                player.x = 100;
                player.y = 100;
               // spawnFood();
            }

            // Enemy collision
            if(player.overlaps(enemy)) {
                gameOver = true;
            }
        }

        // Draw shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Player color
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.x, player.y,
            player.width, player.height);

        // Food color
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(food.x, food.y, 25);

        // Enemy color
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(enemy.x, enemy.y,
            enemy.width, enemy.height);

        shapeRenderer.end();

        // Draw text
        batch.begin();

        font.draw(batch, "Score: " + score, 20, 580);

        if(gameOver) {
          //  font.draw(batch,
            //    "GAME OVER!",
              //  350,
                //300);

            font.draw(batch,
                "⭐ Score: " + score,
                20,
                580);

            if(score >= 5 && !gameOver) {

                font.draw(batch,
                    "🔥 Awesome! Keep Going!",
                    280,
                    560);
            }

            if(score >= 10 && !gameOver) {

                font.draw(batch,
                    "Great Player!",
                    320,
                    530);
            }

            if(gameOver) {

                font.draw(batch,
                    "GAME OVER!",
                    320,
                    320);

                font.draw(batch,
                    "Congratulations your  Final Score: " + score,
                    300,
                    280);

                font.draw(batch,
                    "Nice Try!",
                    340,
                    240);
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {

        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
