import javax.swing.*;

public class GOLTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GOLTester::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame=new JFrame( "Game of Life" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        GOLPanel panel=new GOLPanel( 20); // Specify the world size here
        frame.add( panel );

        frame.pack();
        frame.setVisible( true );
    }
}
