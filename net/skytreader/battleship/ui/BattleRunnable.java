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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.util.Observable;
import java.util.HashMap;

import javax.swing.Box;
import net.skytreader.battleship.Main;
import net.skytreader.battleship.game.BattleBoard;
import net.skytreader.battleship.game.Battleship;
import net.skytreader.battleship.networking.NetworkingInterface;

/**
Runnable for battles.

@author Chad Estioco
*/
public class BattleRunnable extends BattleView implements Runnable{
    
    private JDialog newGameDialog;
    private JFrame mainFrame;
    private JLabel statusLabel;
    private GridPaneClickListener paneListener = new GridPaneClickListener();
    private NetworkingInterface networkInterface;
    private boolean isRecvMode = true;
    // FIXME Hack!
    private BattleGridPane[][] map = new BattleGridPane[10][10];

    // New game config fields
    private JTextField[] patrolFields;
    private JTextField[] destroyerSubFields;
    private JTextField[] battleshipFields;
    private JTextField[] aircraftCarrierFields;

    private final Color SHIP_HIT = Color.RED;
    private final Color SHIP = Color.GRAY;
    private final Color SEA = Color.BLUE;
    private final Color UNKNOWN = Color.LIGHT_GRAY;

    private final String PATROL_BOAT_LABEL = "Patrol Boat";
    private final String DESTROYER_LABEL = "Destroyer";
    private final String BATTLESHIP_LABEL = "Battleship";
    private final String AIRCRAFT_CARRIER_LABEL = "Aircraft Carrier";

    private final HashMap<String, JTextField[]> textFieldMapping;

    public BattleRunnable(BattleBoard boardModel, 
      NetworkingInterface networkInterface){
        super(boardModel);
        this.boardModel.addObserver(this);
        this.networkInterface = networkInterface;

        // Initialize the text fields
        patrolFields = new JTextField[BattleBoard.PATROL_BOAT_COUNT];
        destroyerSubFields = new JTextField[BattleBoard.DESTROYER_SUB_COUNT];
        battleshipFields = new JTextField[BattleBoard.BATTLESHIP_COUNT];
        aircraftCarrierFields = new JTextField[BattleBoard.AIRCRAFT_CARRIER_COUNT];

        // Set-up the look-up map for textfields.
        textFieldMapping = new HashMap<String, JTextField[]>();

        textFieldMapping.put(PATROL_BOAT_LABEL, patrolFields);
        textFieldMapping.put(DESTROYER_LABEL, destroyerSubFields);
        textFieldMapping.put(BATTLESHIP_LABEL, battleshipFields);
        textFieldMapping.put(AIRCRAFT_CARRIER_LABEL, aircraftCarrierFields);
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
            boolean isHorizontal = ships[i].isHorizontalOrientation();
            
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
    Abstraction for everytime we want to "open" the new game dialog.
    */
    private void openNewGameDialog(){
        mainFrame.setEnabled(false);
        newGameDialog.setVisible(true);
    }

    /**
    Handles clicks to the Game->New Game menu item.

    Creates a new JFrame to ask for the configuration of the new game. Disables
    input to the main JFrame. Upon close, re-enable input to main JFrame.
    */
    private class NewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            openNewGameDialog();
        }
    }

    /**
    Window listener for the new game frame. Only interested in the close event.
    */
    private class NewGameWindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent we){
            mainFrame.setEnabled(true);
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

    /**
    Abstraction for everytime we want to "close" the new game dialog box.
    */
    private void closeNewGameDialog(){
        mainFrame.setEnabled(true);
        newGameDialog.setVisible(false);
    }
    
    private class CancelNewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            closeNewGameDialog();
        }
    }

    // TODO
    private class OkNewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            closeNewGameDialog();

            // Get the coords values and set them to board.
            Battleship[] ships = new Battleship[10];
            int i = 0;

            for(; i < patrolFields.length; i++){
                //ships[i] = new BattleShip(BattleShip.PATROL_BOAT, i)
                System.out.println("Patrol boat at: " + patrolFields[i].getText());
            }
        }
    }
    
    /**
    Set-up the new game dialog so we don't have to assemble it every time new
    game is triggered. Try not to call this more than once (though I don't see
    any ill effects of doing so).
    */
    private void setUpNewGameDialog(){
        newGameDialog = new JDialog(new JFrame(), "New Battleship Game");
        newGameDialog.addWindowListener(new NewGameWindowListener());
        newGameDialog.setResizable(false);
        Container newGameContainer = newGameDialog.getContentPane();
        newGameContainer.setLayout(new BoxLayout(newGameContainer,
          BoxLayout.Y_AXIS));

        JLabel instructions = new JLabel("Enter coordinates of first partition:");
        newGameContainer.add(instructions);
        
        /*
         * ######################################
         * 4 Patrol Boats
         * ######################################
         */
        JPanel[] patrolPanels = newGameConfigPanel(BattleBoard.PATROL_BOAT_COUNT,
          PATROL_BOAT_LABEL);

        for(int i = 0; i < BattleBoard.PATROL_BOAT_COUNT; i++){
            newGameContainer.add(patrolPanels[i]);
        }

        /*
         * ######################################
         * 3 Destroyers
         * ######################################
         */
        JPanel[] destroyerPanels = newGameConfigPanel(BattleBoard.DESTROYER_SUB_COUNT,
          DESTROYER_LABEL);

        for(int i = 0; i < BattleBoard.DESTROYER_SUB_COUNT; i++){
            newGameContainer.add(destroyerPanels[i]);
        }

        /*
         * ######################################
         * 2 Battleships
         * ######################################
         */
        JPanel[] battleshipPanels = newGameConfigPanel(BattleBoard.BATTLESHIP_COUNT,
          BATTLESHIP_LABEL);

        for(int i = 0; i < BattleBoard.BATTLESHIP_COUNT; i++){
            newGameContainer.add(battleshipPanels[i]);
        }

        /*
         * ######################################
         * ONE AIRCRAFT CARRIER
         * ######################################
         */
        JPanel[] aircraftCarrierPanels = newGameConfigPanel(
          BattleBoard.AIRCRAFT_CARRIER_COUNT, AIRCRAFT_CARRIER_LABEL);

        for(int i = 0; i < BattleBoard.AIRCRAFT_CARRIER_COUNT; i++){
            newGameContainer.add(aircraftCarrierPanels[i]);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OkNewGameListener());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelNewGameListener());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        newGameContainer.add(buttonPanel);

        newGameDialog.pack();
    }

    /**
    Creates configuration panels for ships in the new game dialog.
    */
    private JPanel[] newGameConfigPanel(int limit, String prompt){
        JLabel[] configLabels = new JLabel[limit];
        JPanel[] configPanels = new JPanel[limit];
        JTextField[] fields = textFieldMapping.get(prompt);

        for(int i = 0; i < limit; i++){
            configPanels[i] = new JPanel();
            configPanels[i].setLayout(new BoxLayout(configPanels[i],
              BoxLayout.X_AXIS));
            configLabels[i] = new JLabel(prompt + " " + (i + 1) + ": ");
            fields[i] = new JTextField("0 0", 5);

            configPanels[i].add(configLabels[i]);
            configPanels[i].add(fields[i]);
        }

        return configPanels;
    }


    public void run(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        setUpNewGameDialog();

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
