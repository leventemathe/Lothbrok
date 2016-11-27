package com.lothbrok.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.lothbrok.game.ashley.components.MapComponent;

public class Mappers {

    public static final ComponentMapper<MapComponent> MAP_MAPPER = ComponentMapper.getFor(MapComponent.class);
}
