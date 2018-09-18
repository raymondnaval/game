/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class SpritesheetCoordinates {

    // Standing shooter coordinates.
    public final static Vector2 SHTR_STAND = new Vector2(0, 0);
    public final static int SH_STAND_W = 9;
    public final static int SH_STAND_H = 32;

    // Moving shooter coordinates.
    public final static Vector2 SHTR_WLK_0 = new Vector2(9, 1);
    public final static int SH_WLK_W_0 = 19;
    public final static int SH_WLK_H_0 = 32;
    public final static Vector2 SHTR_WLK_1 = new Vector2(28, 1);
    public final static int SH_WLK_W_1 = 21;
    public final static int SH_WLK_H_1 = 32;
    public final static Vector2 SHTR_WLK_2 = new Vector2(49, 1);
    public final static int SH_WLK_W_2 = 19;
    public final static int SH_WLK_H_2 = 32;
    public final static Vector2 SHTR_WLK_3 = SHTR_STAND;
    public final static int SH_WLK_W_3 = SH_STAND_W;
    public final static int SH_WLK_H_3 = SH_STAND_H;
    public final static Vector2 SHTR_WLK_4 = new Vector2(77, 2);
    public final static int SH_WLK_W_4 = 20;
    public final static int SH_WLK_H_4 = 32;
    public final static Vector2 SHTR_WLK_5 = new Vector2(97, 2);
    public final static int SH_WLK_W_5 = 22;
    public final static int SH_WLK_H_5 = 32;
    public final static Vector2 SHTR_WLK_6 = new Vector2(119, 2);
    public final static int SH_WLK_W_6 = 20;
    public final static int SH_WLK_H_6 = 32;

    // Small explosion.
    public final static Vector2 EXPL_SML_0 = new Vector2(139, 0);
    public final static int EXPL_SM_W0 = 16;
    public final static int EXPL_SM_H0 = 16;
    public final static Vector2 EXPL_SML_1 = new Vector2(139, 16);
    public final static int EXPL_SM_W1 = 16;
    public final static int EXPL_SM_H1 = 16;

    // Shooting.
    public final static Vector2 SHTR_SHOOT = new Vector2(21, 32);
    public final static int SHOOT_WDTH = 22;
    public final static int SHOOT_HGHT = 32;

    // Projectiles.
    public final static int PROJ_WIDTH = 8;
    public final static int PROJ_HGT_0 = 10;
    public final static int PROJ_HGT_1 = 8;
    public final static Vector2 PROJ_BLUE_0 = new Vector2(43, 32);
    public final static Vector2 PROJ_BLUE_1 = new Vector2(43, 42);
    public final static Vector2 PROJ_BLUE_2 = new Vector2(51, 32);
    public final static Vector2 PROJ_BLUE_3 = new Vector2(51, 42);
    public final static Vector2 PROJ_YELO_0 = new Vector2(59, 32);
    public final static Vector2 PROJ_YELO_1 = new Vector2(59, 42);
    public final static Vector2 PROJ_YELO_2 = new Vector2(67, 32);
    public final static Vector2 PROJ_YELO_3 = new Vector2(67, 42);
    public final static Vector2 PROJ_GRN_0 = new Vector2(75, 32);
    public final static Vector2 PROJ_GRN_1 = new Vector2(75, 42);
    public final static Vector2 PROJ_GRN_2 = new Vector2(83, 32);
    public final static Vector2 PROJ_GRN_3 = new Vector2(83, 42);
    public final static Vector2 PROJ_RED_0 = new Vector2(91, 32);
    public final static Vector2 PROJ_RED_1 = new Vector2(91, 42);
    public final static Vector2 PROJ_RED_2 = new Vector2(99, 32);
    public final static Vector2 PROJ_RED_3 = new Vector2(99, 42);
    public final static Vector2 PROJ_LPRP_0 = new Vector2(107, 32);
    public final static Vector2 PROJ_LPRP_1 = new Vector2(107, 42);
    public final static Vector2 PROJ_LPRP_2 = new Vector2(115, 32);
    public final static Vector2 PROJ_LPRP_3 = new Vector2(115, 42);
    public final static Vector2 PROJ_PRPL_0 = new Vector2(123, 32);
    public final static Vector2 PROJ_PRPL_1 = new Vector2(123, 42);
    public final static Vector2 PROJ_PRPL_2 = new Vector2(131, 32);
    public final static Vector2 PROJ_PRPL_3 = new Vector2(131, 42);
    public final static Vector2 PROJ_BLCK_0 = new Vector2(139, 32);
    public final static Vector2 PROJ_BLCK_1 = new Vector2(139, 42);
    public final static Vector2 PROJ_BLCK_2 = new Vector2(147, 32);
    public final static Vector2 PROJ_BLCK_3 = new Vector2(147, 42);
    
    // Squares.
    public final static int SQ_DIMENS = 16;
    public final static Vector2 SQUARE_YEL = new Vector2(0,64);
    public final static Vector2 SQUARE_GRN = new Vector2(16,64);
    public final static Vector2 SQUARE_RED = new Vector2(32,64);
    public final static Vector2 SQUARE_BLU = new Vector2(48,64);
    public final static Vector2 SQUARE_LPR = new Vector2(64,64);
    public final static Vector2 SQUARE_PRP = new Vector2(80,64);
    public final static Vector2 SQUARE_BLK = new Vector2(96,64);
    
    // Big explosions.
    public final static int BG_EXP_DMNS = 64;
    public final static Vector2 BIG_EXPL_0 = new Vector2(0, 80);
    public final static Vector2 BIG_EXPL_1 = new Vector2(64, 80);
    
    // Jump.
    public final static int JMP_HEIGHT = 20;
    public final static Vector2 JUMP_0 = new Vector2(0, 144);
    public final static int JUMP_WDTH_0 = 12;
    public final static Vector2 JUMP_1 = new Vector2(12, 144);
    public final static int JUMP_WDTH_1 = 27;
    public final static Vector2 JUMP_2 = new Vector2(39, 144);
    public final static int JUMP_WDTH_2 = 12;
    public final static Vector2 JUMP_3 = new Vector2(51, 144);
    public final static int JUMP_WDTH_3 = 27;

}
