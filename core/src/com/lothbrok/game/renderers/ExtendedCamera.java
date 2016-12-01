package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class ExtendedCamera {

    private static final String TAG = ExtendedCamera.class.getSimpleName();
    private Camera camera;

    private float speed = 1.5f;
    private float zoomSpeed = 1.5f;

    private float toleration = 0.001f;

    public ExtendedCamera(Camera camera) {
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

    public void snapToX(float x) {
        camera.position.x = x;
    }

    public void snapToY(float y) {
        camera.position.x = y;
    }

    public void snapTo(Vector2 pos) {
        camera.position.x = pos.x;
        camera.position.y = pos.y;
    }

    public void moveTo(Vector2 targetPos, float deltaTime) {
        Vector2 cameraPos = new Vector2(camera.position.x, camera.position.y);
        float distance = cameraPos.dst(targetPos);
        Vector2 direction = (new Vector2(targetPos.x - cameraPos.x, targetPos.y - cameraPos.y)).nor();
        if(distance < toleration) {
            snapTo(targetPos);
        } else {
            camera.translate(direction.x * speed * deltaTime, direction.y * speed * deltaTime, 0f);
        }
        Gdx.app.debug(TAG, "direction: " + direction.x + ", " + direction.y + ", distannce: " + distance);
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
