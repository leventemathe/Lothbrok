package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.model.entities.Player;


public class PlayerAnimation implements Disposable {

    private SpriterAnimation animation;
    private PlayerAttackingStoppedListener playerAttackingStoppedListener;

    public PlayerAnimation(SpriterAnimation animation) {
        init(animation);
    }

    private void init(SpriterAnimation animation) {
        this.animation = animation;
        this.animation.setCurrentEntity(AssetsConstants.PLAYER_ANIMATION_ENTITY_PLAYER);
        this.animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_IDLE);
    }

    public void attackWhileMoving() {
        animation.setPlayerTweener(AssetsConstants.PLAYER_ANIMATION_ATTACKING, AssetsConstants.PLAYER_ANIMATION_WALKING, AssetsConstants
                .PLAYER_ANIMATION_BONE_ATTACK);
    }

    public Rectangle getBodyBoudningBox() {
        Rectangle bodyRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_BODY);
        Rectangle legRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG);
        if(bodyRect != null && legRect != null) {
            return new Rectangle(bodyRect.x,
                    legRect.y + AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA,
                    bodyRect.width,
                    bodyRect.height + legRect.height - AssetsConstants.PLAYER_ANIMATION_TOP_DELTAT);
        }
        return null;
    }

    public Rectangle getFootSensor() {
        Rectangle leftLegRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG);
        Rectangle rightLegRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_RIGHT_LEG);
        if(leftLegRect != null && rightLegRect != null) {
            if(!animation.isFlipped()) {
                return new Rectangle(leftLegRect.x,
                        leftLegRect.y,
                        rightLegRect.x + rightLegRect.width - leftLegRect.x,
                        AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
            }
            return new Rectangle(rightLegRect.x,
                    rightLegRect.y,
                    leftLegRect.x + leftLegRect.width - rightLegRect.x,
                    AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
        }
        return null;
    }

    public SpriterAnimation getAnimation() {
        return animation;
    }

    public void setStopAttackingListener(Player player) {
        if(playerAttackingStoppedListener == null) {
            playerAttackingStoppedListener = new PlayerAttackingStoppedListener(player);
            animation.setControllerListener(SpriterAnimation.PLAY_ONCE, playerAttackingStoppedListener);
            //animation.setControllerListener(animation.getPlayerTweener().getFirstPlayer(), playerAttackingStoppedListener);
        }
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    private class PlayerAttackingStoppedListener implements com.brashmonkey.spriter.Player.PlayerListener {

        private Player player;

        public PlayerAttackingStoppedListener(Player player) {
            this.player = player;
        }

        @Override
        public void animationFinished(Animation animation) {
            player.stopAttacking();
        }

        @Override
        public void animationChanged(Animation oldAnim, Animation newAnim) {

        }

        @Override
        public void preProcess(com.brashmonkey.spriter.Player player) {

        }

        @Override
        public void postProcess(com.brashmonkey.spriter.Player player) {

        }

        @Override
        public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {

        }
    }
}
