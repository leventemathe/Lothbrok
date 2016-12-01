package com.lothbrok.game.assets.utils;

//TODO implement FileHandleResolvers to remove folder names
public class AssetsConstants {
    //TODO figure out a size policy l,xl etc
    public static final String RALEWAY_LIGHT_FONT_PATH = "fonts/raleway_light.otf";

    public static final String PLAYER_ANIMATION_PATH = "xl/anim/player/player.scml";
    public static final String PLAYER_ANIMATION_ENTITY_PLAYER = "player";
    public static final String PLAYER_ANIMATION_BONE_ATTACK = "swordarm_bone";
    public static final String PLAYER_ANIMATION_SPRITE_BODY = "body";
    public static final String PLAYER_ANIMATION_SPRITE_LEFT_LEG = "left_leg";
    public static final String PLAYER_ANIMATION_SPRITE_RIGHT_LEG = "right_leg";
    public static final float PLAYER_ANIMATION_BOTTOM_DELTA = 0.04f;
    public static final float PLAYER_ANIMATION_TOP_DELTAT = 0.1f;
    public static final String PLAYER_ANIMATION_IDLE = "idle";
    public static final String PLAYER_ANIMATION_WALKING = "walking";
    public static final String PLAYER_ANIMATION_JUMPING = "jumping";
    public static final String PLAYER_ANIMATION_FALLING = "falling";
    public static final String PLAYER_ANIMATION_ATTACKING = "attacking";

    public static final String MAP_PREFIX = "xl/map/map";
    public static final String MAP_POSTFIX = ".tmx";

    public static final String MENU_ATLAS_PATH = "l/img/menu/menu.pack.atlas";
    public static final String MENU_SKIN_PATH = "l/img/menu/menuskin.json";
    public static final String MENU_FONT_PATH = "fonts/pr_viking.ttf";

    public static final String MOBILE_CONTROLS_SKIN_PATH = "xl/img/mobile_controls/mobile_controls_skin.json";
    public static final String MOBILE_CONTROLS_ATLAS_PATH = "xl/img/mobile_controls/mobile_controls.pack.atlas";
}
