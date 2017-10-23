package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
    static class Ball {
        private Texture texture;
        private Vector2 position;
        private Vector2 velocity;
        private Square square;

        final private int radius = 50;
        final private float speed = 500.0f;
        final private int side = 2 * this.radius + 1;

        public Ball(Square square) {
            this.square = square;

            this.position = new Vector2(640, 360);
            this.velocity = new Vector2(1.0f, 1.0f);

            Pixmap pixmap = new Pixmap(this.side, this.side, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.BLACK);
            pixmap.fillCircle(this.radius, this.radius, this.radius);

            this.texture = new Texture(pixmap);

            pixmap.dispose();
        }

        public void render(SpriteBatch batch) {
            batch.draw(this.texture, this.position.x, this.position.y);
        }

        public void update(float dt) {
            if (this.check()) {
                this.velocity.y *= -1.0f;
                this.velocity.x *= -1.0f;
            }

            if ((int)this.position.y >= 720 - this.side) {
                this.velocity.y = -1.0f;
            }
            if ((int)this.position.x >= 1280 - this.side) {
                this.velocity.x = -1.0f;
            }
            if ((int)this.position.y <= 0) {
                this.velocity.y = 1.0f;
            }
            if ((int)this.position.x <= 0) {
                this.velocity.x = 1.0f;
            }

            this.position.mulAdd(this.velocity.cpy().nor().scl(this.speed), dt);
        }

        private boolean check() {
            Rectangle squareRectangle = new Rectangle(
                    (int)this.square.getPosition().x,
                    (int)this.square.getPosition().y,
                    this.square.getSize(),
                    this.square.getSize()
            );
            Rectangle cirlceRectangle = new Rectangle(
                    (int)this.position.x,
                    (int)this.position.y,
                    this.side,
                    this.side
            );

            return squareRectangle.intersects(cirlceRectangle);
        }
    }

    static class Square {
        private Texture texture;
        private Vector2 position;

        final private int side = 200;

        public Square() {
            this.position = new Vector2(640, 360);

            Pixmap pixmap = new Pixmap(this.side, this.side, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.RED);
            pixmap.fill();

            this.texture = new Texture(pixmap);

            pixmap.dispose();
        }

        public void render(SpriteBatch batch) {
            batch.draw(this.texture, this.position.x, this.position.y);
        }

        public void update(float dt) {
            int mx = Gdx.input.getX() - this.side / 2;
            int my = 720 - Gdx.input.getY() - this.side / 2;

            this.position.set(mx, my);
        }

        public Vector2 getPosition() {
            return position;
        }

        public int getSize() {
            return this.side;
        }
    }

	SpriteBatch batch;
	Ball ball;
    Square square;

	@Override
	public void create () {
		batch   = new SpriteBatch();
        square  = new Square();
        ball    = new Ball(square);
    }

	@Override
	public void render () {
	    float dt = Gdx.graphics.getDeltaTime();
        update(dt);
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

        ball.render(batch);
        square.render(batch);

		batch.end();
	}

	public void update(float dt) {
        ball.update(dt);
        square.update(dt);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
