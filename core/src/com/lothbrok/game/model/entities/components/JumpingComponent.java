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
        if(entity.actionState == Entity.ActionState.STANDING) {
            jumpHeight = 0f;
        }
        if(entity.actionState == Entity.ActionState.STANDING || entity.actionState == Entity.ActionState.JUMPING ||
                entity.actionState == Entity.ActionState.MIDJUMP) {
            if (jumpHeight < maxJumpHeight) {
                decelerateJumping();
                float backupY = entity.position.y;
                entity.prevPosition.y = backupY;
                entity.position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
                entity.actionState = Entity.ActionState.JUMPING;
            }
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
