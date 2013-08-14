package net.skytreader.battleship.ui;

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

public class BattleRunnable extends BattleView implements Runnable{
    
    private JFrame mainFrame;
    private JLabel baseLabel;
    private JLabel trackingLabel;
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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container frameContainer = mainFrame.getContentPane();
        frameContainer.setLayout(new BoxLayout(frameContainer, BoxLayout.Y_AXIS));

        JPanel gridsPanel = new JPanel();
        gridsPanel.setLayout(new GridLayout(2, 2));

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void update(Observable obs, Object obj){
    }

}
