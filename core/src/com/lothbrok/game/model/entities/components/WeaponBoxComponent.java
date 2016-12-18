package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.model.entities.Entity;

public class WeaponBoxComponent extends AbstractComponent {

    private Rectangle weaponBox;

    public WeaponBoxComponent(Entity entity) {
        super(entity);
        weaponBox = new Rectangle(entity.position.x, entity.position.y, 0f, 0f);
    }

    public Rectangle getWeaponBox() {
        return weaponBox;
    }

    public void setWeaponBox(Rectangle weaponBox) {
        this.weaponBox = weaponBox;
    }
}
