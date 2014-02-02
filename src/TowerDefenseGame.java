import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * This class controls the presentation of the Tower Defense Game
 *
 * @author Graham Wright
 * @version 1.0
 */
public class TowerDefenseGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InputPanel input = new InputPanel();
        frame.add(input, BorderLayout.WEST);
        frame.add(new GamePanel(input));
        frame.pack();
        frame.setVisible(true);
    }
}
