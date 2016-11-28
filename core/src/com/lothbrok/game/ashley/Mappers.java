package com.lothbrok.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.lothbrok.game.ashley.components.CameraComponent;
import com.lothbrok.game.ashley.components.CameraControllerComponent;
import com.lothbrok.game.ashley.components.MapComponent;
import com.lothbrok.game.ashley.components.SpeedComponent;

public class Mappers {

    public static final ComponentMapper<MapComponent> MAP_MAPPER = ComponentMapper.getFor(MapComponent.class);
    public static final ComponentMapper<CameraComponent> CAMERA_MAPPER = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<CameraControllerComponent> CAMERA_CONTROLLER_COMPONENT_MAPPER = ComponentMapper.getFor(CameraControllerComponent.class);
    public static final ComponentMapper<SpeedComponent> SPEED_COMPONENT_COMPONENT_MAPPER = ComponentMapper.getFor(SpeedComponent.class);
}
