package com.atilsamancioglu.survivorbirdstarter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.2f;
	float beeVelocity = 4;
	Random random;
	int score = 0;
	int scoredBee = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle birdCircle;

	ShapeRenderer shapeRenderer;

	int numberOfBees = 4;
	float [] beeX = new float[numberOfBees];
	float [] beeOffSet = new float[numberOfBees];
	float [] beeOffSet2 = new float[numberOfBees];
	float [] beeOffSet3 = new float[numberOfBees];
 	float distance = 0;

 	Circle [] beeCircles;
 	Circle [] beeCircles2;
 	Circle [] beeCircles3;


	@Override
	public void create () {

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() / 2;

		random = new Random();

		birdX = Gdx.graphics.getWidth() / 3 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		beeCircles = new Circle[numberOfBees];
		beeCircles2 = new Circle[numberOfBees];
		beeCircles3 = new Circle[numberOfBees];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		font2.getData().setScale(6);

		for (int i = 0; i < numberOfBees; i++) {

			beeOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			beeOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			beeOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			beeX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;

			beeCircles[i] = new Circle();
			beeCircles2[i] = new Circle();
			beeCircles3[i] = new Circle();

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (beeX[scoredBee] < Gdx.graphics.getWidth() / 3 - bird.getHeight() / 2) {
				score++;

				if (scoredBee < numberOfBees - 1) {
					scoredBee++;
				} else {
					scoredBee = 0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -7;
			}

			for (int i = 0; i < numberOfBees; i++) {

				if (beeX[i] < Gdx.graphics.getWidth() / 15) {
					beeX[i] += numberOfBees * distance;

					beeOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					beeOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					beeOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				} else {
					beeX[i] -= beeVelocity;
				}

				batch.draw(bee, beeX[i], Gdx.graphics.getHeight() / 2 + beeOffSet[i] ,Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8 );
				batch.draw(bee2, beeX[i], Gdx.graphics.getHeight() / 2 + beeOffSet2[i],Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8 );
				batch.draw(bee3, beeX[i], Gdx.graphics.getHeight() / 2 + beeOffSet3[i],Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8 );

				beeCircles[i] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
				beeCircles2[i] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
				beeCircles3[i] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
			}


			if (birdY > 0 && birdY < Gdx.graphics.getHeight() / 1) {
				velocity += gravity;
				birdY -= velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch, "GAME OVER! TAP TO PLAY AGAIN!", Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

				for (int i = 0; i < numberOfBees; i++) {

					beeOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					beeOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					beeOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					beeX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;

					beeCircles[i] = new Circle();
					beeCircles2[i] = new Circle();
					beeCircles3[i] = new Circle();

				}

				velocity = 0;
				scoredBee = 0;
				score = 0;
			}
		}


		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8);

		font.draw(batch, String.valueOf("SCORE: " + score), 100, 200);


		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);


		for (int i = 0; i < numberOfBees; i++) {
			//shapeRenderer.circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(beeX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + beeOffSet3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(birdCircle, beeCircles[i]) || Intersector.overlaps(birdCircle, beeCircles2[i]) || Intersector.overlaps(birdCircle, beeCircles3[i])) {
				gameState = 2;
			}
		}

		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
