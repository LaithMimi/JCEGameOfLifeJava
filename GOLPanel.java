import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GOLPanel extends JPanel {
    /*Question 2*/
    GOLMatrix golMatrix;
    Timer timer;
    int interval; //The duration of the clock
    private JButton cmdSlower, cmdFaster, cmdNext, cmdGo, cmdClear;
    private JLabel lblGenerations;

    public GOLPanel( int worldSize ) {
        golMatrix=new GOLMatrix(worldSize);
        interval=200;
        int n=golMatrix.getWorld().length;

        setLayout(new BorderLayout());

        //initializing the graphic fields
        JPanel gameBoard=new JPanel();
        gameBoard.setLayout(new GridLayout(n, n));

        for (int i=0 ; i<n ; i++) {
            for (int j=0 ; j<n ; j++) {
                JButton cell=new JButton();
                cell.setBackground(Color.WHITE);
                cell.addMouseListener(new CellClickListener(i, j));
                gameBoard.add(cell);
            }
        }
        add(gameBoard, BorderLayout.CENTER);


        //buttons
        cmdClear=new JButton("Clear");
        cmdNext=new JButton("Next");
        cmdGo=new JButton("Go");
        cmdFaster=new JButton("Faster");
        cmdSlower=new JButton("Slower");

        lblGenerations=new JLabel("Number of generations: 0");
        add(lblGenerations, BorderLayout.SOUTH);

        cmdClear.setEnabled(true);
        cmdNext.setEnabled(true);
        cmdGo.setEnabled(true);
        cmdFaster.setEnabled(false);
        cmdSlower.setEnabled(false);

        JPanel controlPanel=new JPanel();
        controlPanel.add(cmdClear);
        controlPanel.add(cmdNext);
        controlPanel.add(cmdGo);
        controlPanel.add(cmdFaster);
        controlPanel.add(cmdSlower);

        add(controlPanel, BorderLayout.NORTH);

        timer=new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                golMatrix.nextGeneration();
                lblGenerations.setText("Number of generations: "+golMatrix.getGenerations());
                updateGrid();
            }
        });

        //Next button action listener
        cmdNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                golMatrix.nextGeneration();
                lblGenerations.setText("Number of generations: "+golMatrix.getGenerations());
                updateGrid();
            }
        });

        // Go button action listener
        cmdGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (timer.isRunning()) {
                    //stop
                    timer.stop();
                    cmdGo.setText("Go");
                    cmdNext.setEnabled(true);
                    cmdClear.setEnabled(true);
                    cmdFaster.setEnabled(false);
                    cmdSlower.setEnabled(false);
                }else {
                    //Start
                    timer.start();
                    cmdGo.setText("Stop");
                    cmdNext.setEnabled(false);
                    cmdClear.setEnabled(false);
                    cmdFaster.setEnabled(true);
                    cmdSlower.setEnabled(true);
                }
            }
        });

        //Clear button action listener
        cmdClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                golMatrix.clearWorld();
                lblGenerations.setText("Number of generations: 0");
                updateGrid();
            }
        });

        //Faster button action listener
        cmdFaster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (interval>100) {
                    interval-=20;
                    timer.setDelay(interval);
                }
            }
        });

        //Slower button action listener
        cmdSlower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (interval<1000) {
                    interval+=20;
                    timer.setDelay(interval);
                }
            }
        });

    }

    private void updateGrid() {
        Component[] components=((JPanel) this.getComponent(0)).getComponents();
        for (int i=0 ; i<components.length ; i++) {
            JButton cellButton=(JButton) components[i];
            int rowIndex=i / golMatrix.getWorld().length;
            int colIndex=i % golMatrix.getWorld().length;
            if (golMatrix.getWorld()[rowIndex][colIndex])
                cellButton.setBackground(Color.BLUE);
            else
                cellButton.setBackground(Color.WHITE);
        }
    }

    private class CellClickListener extends MouseAdapter {
        private int row, col;

        public CellClickListener( int row, int col ) {
            this.row=row;
            this.col=col;
        }

        @Override
        public void mouseClicked( MouseEvent e ) {
            JButton cell=(JButton) e.getSource();
            golMatrix.flipCell(row, col);

            if (golMatrix.getWorld()[row][col]) {
                cell.setBackground(Color.BLUE);
            }else {
                cell.setBackground(Color.WHITE);
            }

            updateGrid();
        }
    }


}




