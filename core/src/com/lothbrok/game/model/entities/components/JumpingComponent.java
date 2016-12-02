package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class JumpingComponent extends AbstractComponent {

    private float jumpHeight = 0f;
    private float maxJumpHeight;
    private float jumpSpeed;
    private float jumpDeceleration;
    private float minJumpSpeed;
    private float baseJumpSpeed;

    public JumpingComponent(Entity entity, float maxJumpHeight, float jumpDeceleration, float minJumpSpeed, float baseJumpSpeed) {
        super(entity);
        this.maxJumpHeight = maxJumpHeight;
        this.jumpDeceleration = jumpDeceleration;
        this.minJumpSpeed = minJumpSpeed;
        this.baseJumpSpeed = baseJumpSpeed;
        this.jumpSpeed = baseJumpSpeed;
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
