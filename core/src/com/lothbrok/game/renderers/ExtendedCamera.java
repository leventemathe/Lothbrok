package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ExtendedCamera {

    private static final String TAG = ExtendedCamera.class.getSimpleName();
    private Camera camera;

    private final float BASE_SPEED = 1.5f;
    private float speed = BASE_SPEED;
    private float acceleration = 1.01f;
    private float zoomSpeed = 1.5f;

    private float tolerance = 0.08f;

    private Rectangle border;

    public ExtendedCamera(Camera camera, Map map) {
        border = new Rectangle(0f, 0f, (int)map.getProperties().get("width"), (int)map.getProperties().get("height"));
        this.camera = camera;
    }

    public void moveUp(float deltaTime) {
        camera.translate(0f, speed*deltaTime, 0f);
    }

    public void moveDown(float deltaTime) {
        camera.translate(0f, -speed*deltaTime, 0f);
    }

    public void moveLeft(float deltaTime) {
        camera.translate(-speed*deltaTime, 0f, 0f);
    }

    public void moveRight(float deltaTime) {
        camera.translate(speed*deltaTime, 0f, 0f);
    }

    public void zoomIn(float deltaTime) {
        if(camera instanceof OrthographicCamera) {
            ((OrthographicCamera)camera).zoom -= zoomSpeed * deltaTime;
        }
    }

    public void zoomOut(float deltaTime) {
        if(camera instanceof OrthographicCamera) {
            ((OrthographicCamera)camera).zoom += zoomSpeed * deltaTime;
        }
    }

    private boolean isEndOfMapLeft(float x) {
        if(x < border.x + camera.viewportWidth/2f) {
            return true;
        }
        return false;
    }

    private boolean isEndOfMapRight(float x) {
        if(x > border.width - camera.viewportWidth/2f) {
            return true;
        }
        return false;
    }

    private boolean isEndOfMapBottom(float y) {
        if(y < border.y + camera.viewportHeight/2f) {
            return true;
        }
        return false;
    }

    private boolean isEndOfMapTop(float y) {
        if(y > border.height - camera.viewportHeight/2f) {
            return true;
        }
        return false;
    }

    public void snapToX(float x) {
        if(isEndOfMapLeft(x)) {
            camera.position.x = border.x + camera.viewportWidth/2f;
            return;
        }
        if(isEndOfMapRight(x)) {
            camera.position.x = border.width - camera.viewportWidth/2f;
            return;
        }
        camera.position.x = x;
    }

    public void snapToY(float y) {
        if(isEndOfMapBottom(y)) {
            camera.position.y = border.y + camera.viewportHeight/2f;
            return;
        }
        if(isEndOfMapTop(y)) {
            camera.position.y = border.height - camera.viewportHeight/2f;
            return;
        }
        camera.position.y = y;
    }

    public void snapTo(Vector2 pos) {
        snapToX(pos.x);
        snapToY(pos.y);
    }

    public void moveToX(float x, float deltaTime) {
        float distance = x - camera.position.x;
        float direction = distance / Math.abs(distance);
        if(Math.abs(distance) < tolerance) {
            snapToX(x);
            speed = BASE_SPEED;
        } else {
            camera.position.x += direction * speed * deltaTime;
            if(isEndOfMapLeft(camera.position.x)) {
                camera.position.x = border.x + camera.viewportWidth/2f;
            } else if(isEndOfMapRight(camera.position.x)) {
                camera.position.x = border.width - camera.viewportWidth/2f;
            } else {
                speed *= acceleration;
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
