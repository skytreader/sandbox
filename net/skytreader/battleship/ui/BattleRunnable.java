package net.skytreader.battleship.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.skytreader.battleship.game.BattleBoard;

/**
Runnable for battles.

@author Chad Estioco
*/
public class BattleRunnable extends BattleView implements Runnable{
    
    private JFrame mainFrame;
    private JLabel statusLabel;

    public BattleRunnable(BattleBoard boardModel){
        super(boardModel);
        this.boardModel.addObserver(this);
    }

    public void run(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        mainFrame = new JFrame("Battleship");
        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container frameContainer = mainFrame.getContentPane();
        frameContainer.setLayout(new BoxLayout(frameContainer, BoxLayout.Y_AXIS));
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JLabel baseLabel = new JLabel("Base Ships:");
        JLabel trackingLabel = new JLabel("Tracking Grid:");
        labelPanel.add(baseLabel);
        labelPanel.add(trackingLabel);

        /*
         * ######################################
         * Battleship Grids
         * ######################################
         */
        JPanel gridsPanel = new JPanel();
        gridsPanel.setLayout(new BoxLayout(gridsPanel, BoxLayout.X_AXIS));

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new GridLayout(10, 10, 3, 3));

        // Add grid for basePanel
        for(int i = 0; i < 100; i++){
            JLabel gridPane = new JLabel(" ");
            //JPanel gridPane = new JPanel();
            gridPane.setBackground(Color.LIGHT_GRAY);
            gridPane.setOpaque(true);
            gridPane.setSize(20, 20);
            basePanel.add(gridPane);
        }

        JPanel trackingPanel = new JPanel();
        trackingPanel.setLayout(new GridLayout(10, 10));

        gridsPanel.add(basePanel);
        gridsPanel.add(trackingPanel);

        // Add Stuff to the frameContainer
        frameContainer.add(labelPanel);
        frameContainer.add(gridsPanel);

        //mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void update(Observable obs, Object obj){
    }

}
