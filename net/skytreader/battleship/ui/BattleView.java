package net.skytreader.battleship.ui;

import java.util.Observer;

import net.skytreader.battleship.game.BattleBoard;

public abstract class BattleView implements Observer {
    
    protected BattleBoard boardModel;

    public BattleView(BattleBoard model){
        boardModel = model;
    }

}
