package com.lothbrok.game.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.ashley.Mappers;
import com.lothbrok.game.ashley.components.CameraComponent;
import com.lothbrok.game.ashley.components.CameraControllerComponent;
import com.lothbrok.game.ashley.components.SpeedComponent;

public class CameraControllerSystem extends EntitySystem {

    private Entity entity;

    @Override
    public void addedToEngine(Engine engine) {
        entity = engine.getEntitiesFor(Family.all(CameraComponent.class,
                                                  CameraControllerComponent.class,
                                                  SpeedComponent.class)
                                                  .get()).get(0);
    }

    @Override
    public void update(float deltaTime) {
        CameraControllerComponent controller = Mappers.CAMERA_CONTROLLER_COMPONENT_MAPPER.get(entity);
        boolean[] commands = controller.commands;
        Camera camera = Mappers.CAMERA_MAPPER.get(entity).camera;
        Vector2 speed = Mappers.SPEED_COMPONENT_COMPONENT_MAPPER.get(entity).speed;

        if(commands[controller.MOVE_LEFT]) {
            camera.translate(-speed.x, 0f, 0f);
        }
        if(commands[controller.MOVE_RIGHT]) {
            camera.translate(speed.x, 0f, 0f);
        }
        if(commands[controller.MOVE_DOWN]) {
            camera.translate(0f, -speed.y, 0f);
        }
        if(commands[controller.MOVE_UP]) {
            camera.translate(0f, speed.y, 0f);
        }
        if(commands[controller.ZOOM_IN]) {

        }
        if(commands[controller.ZOOM_OUT]) {

        }
    }
}
