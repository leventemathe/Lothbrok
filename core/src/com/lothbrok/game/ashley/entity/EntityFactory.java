package com.lothbrok.game.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.lothbrok.game.ashley.components.MapComponent;
import com.lothbrok.game.assets.Assets;

public class EntityFactory {

    private EntityFactory() {}

    public static final Entity createMap() {
        Entity entity = new Entity();

        MapComponent mapComponent = new MapComponent();
        mapComponent.map = Assets.instance.getMap(1);

        entity.add(mapComponent);

        return entity;
    }
}
