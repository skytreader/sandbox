package net.skytreader.battleship.ui;

import java.awt.Color;

import javax.swing.JLabel;

/**
A Swing component to represent a block in the battle grid. Implemented
as a blank JLabel with a background color. Also holds the coordinates
of the represented block.

@author Chad Estioco
*/
public class BattleGridPane extends JLabel{
    
    public BattleGridPane(int rowInfo, int colInfo, Color bg){
        super(" ");
        super.setBackground(Color.LIGHT_GRAY);
        super.setOpaque(true);
    }

}
