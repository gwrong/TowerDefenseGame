import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 * InputPanel constructs the left half of the Tower Defense GUI that lets
 * players choose which towers to place and also shows the wave number, score,
 * and remaining money
 *
 * @author Graham Wright
 * @version 1.0
 */

//need to draw towers and radii separately

public class InputPanel extends JPanel {

    private int score = 250;
    private int money = 150;
    private boolean stillPlaying, inFastForward;

    private int waveNumber;

    private JLabel scoreLabel, moneyLabel, waveLabel;
    private int currentTowerType;
    private Timer waveTimer;

    /**
    * Constructs the Input Panel GUI for a Tower Defense Game
    */
    public InputPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, GamePanel.HEIGHT));
        waveNumber = 0;

        add(Box.createRigidArea(new Dimension(60, 100)));
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        moneyLabel = new JLabel("Money: " + money);
        moneyLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        waveLabel = new JLabel("Wave Number: " + waveNumber);
        waveLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        add(waveLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(moneyLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));

        ButtonGroup radios = new ButtonGroup();
        JRadioButton basic = new JRadioButton("Basic Tower: 75");
        basic.addActionListener(new RadioListener(Tower.BASIC_TOWER));
        basic.setSelected(true);

        JRadioButton advanced = new JRadioButton("Advanced Tower: 150");
        advanced.addActionListener(new RadioListener(Tower.ADVANCED_TOWER));
        basic.setFont(new Font("Serif", Font.PLAIN, 15));
        advanced.setFont(new Font("Serif", Font.PLAIN, 15));

        radios.add(basic);
        radios.add(advanced);

        add(basic);
        add(advanced);
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton wave = new JButton("Send Wave");
        wave.addActionListener(new WaveButtonListener());
        add(wave);
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton fastForward = new JButton("Fast Forward");
        fastForward.addActionListener(new FastForwardButtonListener());
        add(fastForward);

        currentTowerType = Tower.BASIC_TOWER;
        stillPlaying = true;
        inFastForward = false;
        waveTimer = null;
    }

    /**
    * Gives a reference to the waveTimer that controls the releasing of
    * Monsters each wave to InputPanel so that it knows not to start a wave
    * before the previous wave has finished releasing monsters
    *
    * @param waveTimer The Timer that controls wave releases
    */
    public void assignWaveTimer(Timer waveTimer) {
        this.waveTimer = waveTimer;
    }

    /**
    * Gets the current Tower type that is selected for creation
    *
    * @return currentTowerType The int representation of the current Tower
    */
    public int getCurrentTowerType() {
        return currentTowerType;
    }

    /**
    * Gets the current wave number of the game
    *
    * @return waveNumber The current wave number of the game
    */
    public int getWaveNumber() {
        return waveNumber;
    }

    /**
    * Gets the current score
    *
    * @return score The score of the player
    */
    public int getScore() {
        return score;
    }

    /**
    * Updates the score and money variables from happenings in the game
    *
    * @param stats An array of the amount of score to be subtracted from
    * unkilled Monsters and the amount of money to add from killed monsters
    */
    public void updateStats(int[] stats) {
        takeFromScore(stats[0]);
        addToMoney(stats[1]);
    }

    /**
    * Checks if the user still has a positive score
    *
    * @return stillPlaying Whether or not the game is still in progress
    */
    public boolean isStillPlaying() {
        return stillPlaying;
    }

    /**
    * Checks if the game should run at double speed
    *
    * @return inDoubleSpeed Whether or not the game is at double speed
    */
    public boolean isInFastForward() {
        return inFastForward;
    }

    /**
    * Removes the amount of money spent from creating Towers from the total
    *
    * @param spent The amount of money spent by making Towers
    * @return boolean True if the resulting money value is positive,
    * false if the resulting money value would be negative (but does not
    * subtract if so)
    */
    public boolean removeFromMoney(int spent) {
        if (money - spent < 0) {
            return false;
        } else {
            money -= spent;
            moneyLabel.setText("Money: " + money);
            return true;
        }
    }

    private void takeFromScore(int scoreLoss) {
        score -= scoreLoss;
        if (score <= 0) {
            score = 0;
            stillPlaying = false;
        }
        scoreLabel.setText("Score: " + score);
    }

    private void addToMoney(int moneyAddition) {
        money += moneyAddition;
        moneyLabel.setText("Money: " + money);
    }

    private void endGame() {
        stillPlaying = false;
    }

    private class RadioListener implements ActionListener {

        private int tower;

        public RadioListener(int tower) {
            this.tower = tower;
        }
        /**
        * The required actionPerformed method
        *
        * @param e The ActionEvent happening from clicking on a Radio Button
        */
        public void actionPerformed(ActionEvent e) {
            if (!(currentTowerType == tower) || !(currentTowerType == tower)) {
                currentTowerType = tower;
            }
        }
    }

    private class WaveButtonListener implements ActionListener {

        /**
        * The required actionPerformed method
        *
        * @param e The ActionEvent caused by clicking the Wave button
        */
        public void actionPerformed(ActionEvent e) {
            if (waveNumber == 50) {
                waveNumber++;
                endGame();
            }
            if (!waveTimer.isRunning() && waveNumber < 50) {
                waveNumber++;
                waveLabel.setText("Wave Number: " + waveNumber);
            }
        }
    }

    private class FastForwardButtonListener implements ActionListener {

        /**
        * The required actionPerformed method
        *
        * @param e The ActionEvent caused by clicking the Wave button
        */
        public void actionPerformed(ActionEvent e) {
            inFastForward = !inFastForward;
        }
    }
}
