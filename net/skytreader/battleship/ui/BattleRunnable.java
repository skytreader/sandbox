package net.skytreader.battleship.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.skytreader.battleship.Main;
import net.skytreader.battleship.game.BattleBoard;
import net.skytreader.battleship.game.Battleship;
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
    private boolean isRecvMode = true;
    // FIXME Hack!
    private BattleGridPane[][] map = new BattleGridPane[10][10];

    private final Color SHIP_HIT = Color.RED;
    private final Color SHIP = Color.GRAY;
    private final Color SEA = Color.BLUE;

    private final Color UNKNOWN = Color.LIGHT_GRAY;

    public BattleRunnable(BattleBoard boardModel,
      NetworkingInterface networkInterface){
        super(boardModel);
        this.boardModel.addObserver(this);
        this.networkInterface = networkInterface;
    }

    /**
    Change color of grid cells in the base map to reflect the layout of ships.
    Must be called every new game; this is not concerned with showing what parts
    of ships are damaged.

    Assumes that the base map has been preloaded with sea/water colors.
    */
    private void laydownShips(){
        Battleship[] ships = boardModel.getShips();
        int limit = ships.length;

        for(int i = 0; i < limit; i++){
            int shipRow = ships[i].getRow();
            int shipCol = ships[i].getCol();
            int shipSpan = ships[i].getSpan() - 1;
            boolean isHorizontal = ships[i].getIsHorizontalOrientation();
            
            map[shipRow][shipCol].setBackground(SHIP);
            int incr = 0;

            if(isHorizontal){
                while(incr < shipSpan){
                    map[shipRow][shipCol + incr].setBackground(SHIP);
                    incr++;
                }
            } else{
                while(incr < shipSpan){
                    map[shipRow + incr][shipCol].setBackground(SHIP);
                    incr++;
                }
            }
        }
    }

    /**
    Draw a grid in the JPanel.

    @param panel the JPanel to which we draw the grid.
    @param isTracking if true, add an ActionListener to the the grid panes.
    @param c the color of the grid cells.
    */
    private void generateGrid(JPanel panel, boolean isTracking, Color c){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                BattleGridPane gridPane = new BattleGridPane(i, j, c);
                if(isTracking){
                    gridPane.addActionListener(paneListener);
                } else{
                    map[i][j] = gridPane;
                }
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
            System.out.println("isRecvMode? " + isRecvMode);
            if(isRecvMode){
                return;
            }
            BattleGridPane pane = (BattleGridPane) ae.getSource();
            pane.setBackground(Color.BLACK);
            System.out.println("Row: " + pane.getRowInfo());
            System.out.println("Col: " + pane.getColInfo());
            try{
                networkInterface.sendHit(pane.getRowInfo(), pane.getColInfo());
                System.out.println("Sent a hit.");
                isRecvMode = true;
            } catch(Exception e){
                JOptionPane.showMessageDialog(mainFrame,
                  "Unable to send hit.",
                  "Connection warning",
                  JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
    Handles clicks to the Game->Quit menu item.
    */
    private class QuitListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            mainFrame.setVisible(false);
            System.exit(0);
        }
    }

    /**
    Handles clicks to the About->Game menu item.
    */
    private class AboutGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            JOptionPane.showMessageDialog(mainFrame,
              "Battleship v" + Main.VERSION,
              "About Battleship",
              JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
    Handles clicks to the Game->New Game menu item.

    Creates a new JFrame to ask for the configuration of the new game. Disables
    input to the main JFrame. Upon close, re-enable input to main JFrame.
    */
    private class NewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            mainFrame.setEnabled(false);
            System.out.println("Opening: Is mainFrame enabled? " + mainFrame.isEnabled());
            JFrame newGameFrame = new JFrame("New Battleship Game");
            newGameFrame.addWindowListener(new NewGameWindowListener());
            Container newGameContainer = newGameFrame.getContentPane();
            newGameContainer.setLayout(new BoxLayout(newGameContainer,
              BoxLayout.Y_AXIS));
            newGameContainer.add(new JLabel("The quick brown fox jumps over the lazy dog"));
            newGameFrame.pack();
            newGameFrame.setVisible(true);
        }
    }
    
    /**
    Window listener for the new game frame. Only interested in the close event.
    */
    private class NewGameWindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent we){
            mainFrame.setEnabled(true);
            System.out.println("Closing: Is mainFrame enabled? " + mainFrame.isEnabled());
        }
    }

    private class RecvThread implements Runnable{
        public void run(){
            System.out.println("RECV thread running.");
            try{
                while(true){
                    if(isRecvMode && map != null){
                        int[] coords = networkInterface.receiveHit();
                        
                        if(boardModel.hit(coords[0], coords[1])){
                            map[coords[0]][coords[1]].setBackground(SHIP_HIT);
                        }
                        isRecvMode = false;
                    }
                    Thread.sleep(1000);
                    System.out.println("will receive? " + isRecvMode);
                }
             } catch(Exception e){
                e.printStackTrace();
             }
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
        frameContainer.setLayout(new BoxLayout(frameContainer,
          BoxLayout.Y_AXIS));
        
        try{
            networkInterface.connect();
            new Thread(new RecvThread()).start();
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(mainFrame,
              "Unable to connect to game server.",
              "Connection warning",
              JOptionPane.WARNING_MESSAGE);
            ioe.printStackTrace();
        }

        /*
         * ######################################
         * Menu Bar
         * ######################################
         */
        JMenuBar menubar = new JMenuBar();

        // Game menu and related items
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game...");
        newGame.addActionListener(new NewGameListener());
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitListener());
        gameMenu.add(newGame);
        gameMenu.add(quit);
        menubar.add(gameMenu);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutGame = new JMenuItem("Game");
        aboutGame.addActionListener(new AboutGameListener());
        aboutMenu.add(aboutGame);
        menubar.add(aboutMenu);

        mainFrame.setJMenuBar(menubar);

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
        generateGrid(basePanel, false, SEA);

        JPanel trackingPanel = new JPanel();
        trackingPanel.setLayout(new GridLayout(10, 10, 3, 3));
        generateGrid(trackingPanel, true, UNKNOWN);

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
