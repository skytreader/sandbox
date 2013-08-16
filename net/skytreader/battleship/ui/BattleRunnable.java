package net.skytreader.battleship.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.skytreader.battleship.game.BattleBoard;
import net.skytreader.battleship.networking.NetworkingInterface;

/**
Runnable for battles.

@author Chad Estioco
*/
public class BattleRunnable extends BattleView implements Runnable{
    
    private JFrame mainFrame;
    private JLabel statusLabel;
    private GridPaneClickListener paneListener = new GridPaneClickListener();
    private NetworkingInterface networkInterface;

    public BattleRunnable(BattleBoard boardModel, NetworkingInterface networkInterface){
        super(boardModel);
        this.boardModel.addObserver(this);
        this.networkInterface = networkInterface;
    }

    /**
    Draw a grid in the JPanel.

    @param panel the JPanel to which we draw the grid.
    */
    private void generateGrid(JPanel panel){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                BattleGridPane gridPane = new BattleGridPane(i, j,
                  Color.LIGHT_GRAY);
                gridPane.addActionListener(paneListener);
                gridPane.setSize(20, 20);
                panel.add(gridPane);
            }
        }
    }

    /**
    Listen to clicks on GridPanes. Should send the coordinates over the network.
    It is assumed that this listener is only attached to instances of
    BattleGridPane.
    */
    private class GridPaneClickListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            BattleGridPane pane = (BattleGridPane) ae.getSource();
            System.out.println("Row: " + pane.getRowInfo());
            System.out.println("Col: " + pane.getColInfo());
        }
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
        
        try{
            networkInterface.connect();
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(mainFrame,
              "Unable to connect to game server.",
              "Connection warning",
              JOptionPane.WARNING_MESSAGE);
            ioe.printStackTrace();
        }

        /*
         * ######################################
         * Grid Labels
         * ######################################
         */
        JLabel baseLabel = new JLabel("Base Ships:");
        JLabel trackingLabel = new JLabel("Tracking Grid:");

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
        generateGrid(basePanel);

        JPanel trackingPanel = new JPanel();
        trackingPanel.setLayout(new GridLayout(10, 10, 3, 3));
        generateGrid(trackingPanel);

        /*
         * ######################################
         * Group grid with label
         * ######################################
         */
        JPanel base = new JPanel();
        base.setLayout(new BoxLayout(base, BoxLayout.Y_AXIS));
        base.add(baseLabel);
        base.add(basePanel);

        JPanel tracking = new JPanel();
        tracking.setLayout(new BoxLayout(tracking, BoxLayout.Y_AXIS));
        tracking.add(trackingLabel);
        tracking.add(trackingPanel);

        gridsPanel.add(base);
        gridsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        gridsPanel.add(tracking);

        // Add Stuff to the frameContainer
        frameContainer.add(gridsPanel);

        //mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void update(Observable obs, Object obj){
    }

}
