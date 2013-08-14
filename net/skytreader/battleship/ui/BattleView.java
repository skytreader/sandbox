package net.skytreader.battleship.ui;

import java.util.Observer;

import net.skytreader.battleship.game.BattleBoard;

abstract class BattleView implements Observer {
    
    private BattleBoard boardModel;

    public BattleView(BattleBoard model){
        boardModel = model;
    }

}
