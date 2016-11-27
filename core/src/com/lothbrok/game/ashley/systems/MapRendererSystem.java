package com.lothbrok.game.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lothbrok.game.ashley.Mappers;
import com.lothbrok.game.ashley.components.MapComponent;
import com.lothbrok.game.renderers.MyOrthogonalTiledMapRenderer;
import com.lothbrok.game.screens.GameScreen;

public class MapRendererSystem extends EntitySystem{

    private static final String TAG = MapRendererSystem.class.getSimpleName();

    private Entity entity;
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void addedToEngine(Engine engine) {
        entity = engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0);
        Map map = Mappers.MAP_MAPPER.get(entity).map;
        renderer = new MyOrthogonalTiledMapRenderer((TiledMap)map, 1f/540f, GameScreen.batch);
    }

    @Override
    public void update(float deltaTime) {
        renderer.setView((OrthographicCamera)GameScreen.camera);
        renderer.render();
        Gdx.app.debug(TAG, "rendering");
    }
}
