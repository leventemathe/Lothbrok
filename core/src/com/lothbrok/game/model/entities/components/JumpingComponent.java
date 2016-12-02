package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class JumpingComponent extends AbstractComponent {

    protected float jumpHeight = 0f;
    protected float maxJumpHeight;
    protected float jumpSpeed;
    protected float jumpDeceleration;
    protected float minJumpSpeed;
    protected float baseJumpSpeed;

    public JumpingComponent(Entity entity) {
        super(entity);
    }

    public void jump(float delta) {
        if(entity.actionState.equals(Entity.ActionState.STANDING) || entity.actionState.equals(Entity.ActionState.JUMPING) ||
                entity.actionState == Entity.ActionState.MIDJUMP) {
            if(jumpHeight < maxJumpHeight) {
                decelerateJumping();
                entity.prevPosition.y = entity.position.y;
                entity.position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
//                if(isTopColliding()) {
//                    position.y = prevPosition.y;
//                } else {
//                    actionState = MovingEntity.ActionState.JUMPING;
//                }
            }
            //Gdx.app.debug(TAG, "jumping");
        }
    }

    private void decelerateJumping() {
        if(entity.actionState == Entity.ActionState.MIDJUMP && jumpSpeed > minJumpSpeed) {
            jumpSpeed *= jumpDeceleration;
        } else if(entity.actionState != Entity.ActionState.MIDJUMP) {
            jumpSpeed = baseJumpSpeed;
        }
    }

}
