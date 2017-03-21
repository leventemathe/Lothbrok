package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.ActionListener;
import com.lothbrok.game.model.entities.Entity;

public class JumpingComponent extends AbstractComponent {

    private float jumpHeight = 0f;
    private float maxJumpHeight;
    private float jumpSpeed;
    private float jumpDeceleration;
    private float minJumpSpeed;
    private float baseJumpSpeed;

    private ActionListener jumpListener;

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
            if(jumpListener != null) {
                jumpListener.listen();
            }
        }
        if(entity.actionState == Entity.ActionState.STANDING || entity.actionState == Entity.ActionState.JUMPING ||
                entity.actionState == Entity.ActionState.MIDJUMP) {
            if (jumpHeight < maxJumpHeight) {
                decelerateJumping();
                float backupY = entity.getPositionY();
                entity.setPrevPositionY(backupY);
                entity.setPositionY(entity.getPositionY() + jumpSpeed * delta);
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

    public void setJumpListener(ActionListener jumpListener) {
        this.jumpListener = jumpListener;
    }
}
