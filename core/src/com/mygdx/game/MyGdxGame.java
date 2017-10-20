package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
    static class Obj {
        private Texture texture;
        private Vector2 position;
        private float vx, vy;
        private float angle;
        private float scale;

        public Obj(Texture texture, float x, float y, float angle) {
            this.texture = texture;
            this.position = new Vector2(x, y);
            this.vx = vx;
            this.vy = vy;
            this.angle = angle;
            this.scale = 1.0f;
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 128, position.y - 128, 128, 128, 256, 256, scale, scale, angle, 0, 0, 256, 256, false, false);
        }
        public void update(float dt) {
            Vector2 m = new Vector2(Gdx.input.getX(), 720 - Gdx.input.getY());
            Vector2 v = m.cpy().sub(position).nor().scl(60.0f);

            if (m.cpy().sub(position).len() < 200) {
                if (Gdx.input.isTouched()) {
                    position.mulAdd(v, -1 * dt);
                } else {
                    position.mulAdd(v, dt);
                }
            }

            angle += dt * 100.0f;

            // if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            //     x += vx * dt;
            // } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //     x -= vx * dt;
            // }
            // if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            //     y += vy * dt;
            // } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //     y -= vy * dt;
            // }

            // if (Gdx.input.justTouched()) {
            //     if (Math.abs(Gdx.input.getX() - x) < 128 * scale && Math.abs((720 - Gdx.input.getY()) - y) < 128 * scale) {
            //         scale += 0.1f;
            //     }
            // }

        }
    }

	SpriteBatch batch;
	Obj[] objs;

	@Override
	public void create () {
		batch = new SpriteBatch();
		objs = new Obj[100];
		Texture texture = new Texture("badlogic.jpg");

        for (int i = 0; i < objs.length; i++) {
            objs[i] = new Obj(texture, MathUtils.random(0, 1280), MathUtils.random(0, 720), MathUtils.random(0, 1000));
        }
    }

	@Override
	public void render () {
	    float dt = Gdx.graphics.getDeltaTime();
        update(dt);
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

        for (int i = 0; i < objs.length; i++) {
            objs[i].render(batch);
        }

		batch.end();
	}

	public void update(float dt) {
        for (int i = 0; i < objs.length; i++) {
            objs[i].update(dt);
        }
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
