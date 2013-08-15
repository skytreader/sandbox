package net.skytreader.battleship.ui;

import java.awt.Color;

import javax.swing.JButton;

/**
A Swing component to represent a block in the battle grid. Implemented
as a blank JLabel with a background color. Also holds the coordinates
of the represented block.

@author Chad Estioco
*/
public class BattleGridPane extends JButton{
    
    private int rowInfo;
    private int colInfo;

    public BattleGridPane(int rowInfo, int colInfo, Color bg){
        super(" ");
        super.setBackground(Color.LIGHT_GRAY);
        super.setFocusPainted(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setOpaque(true);

        this.rowInfo = rowInfo;
        this.colInfo = colInfo;
    }

    public int getRowInfo(){
        return rowInfo;
    }

    public int getColInfo(){
        return colInfo;
    }

}
