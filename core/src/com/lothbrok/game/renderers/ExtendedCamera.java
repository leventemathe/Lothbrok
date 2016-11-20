package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ExtendedCamera {

    private Camera camera;

    private float speed = 1.5f;
    private float zoomSpeed = 0.01f;

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
            ((OrthographicCamera)camera).zoom -= zoomSpeed;
        }
    }

    public void zoomOut(float deltaTime) {
        if(camera instanceof OrthographicCamera) {
            ((OrthographicCamera)camera).zoom += zoomSpeed;
        }
    }


    //Getters & Setters
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
