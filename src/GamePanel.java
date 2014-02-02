import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Collections;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;
import java.awt.Rectangle;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    public static final int HEIGHT = 700, WIDTH = 700;
    public static final Rectangle BOUNDS = new Rectangle(HEIGHT, WIDTH);

    private InputPanel inputPanel;
    private ArrayList<Line> path;
    private ArrayList<Tower> towers;
    private ArrayList<Monster> monsters;
    private Timer gameTimer;
    private Timer waveTimer;
    private int waveNumber;
    private boolean previouslyFastForward;

    /**
    * Constructs the Game Panel GUI for the Tower Defense Game
    *
    * @param input The InputPanel for the Tower Defense Game
    */
    public GamePanel(InputPanel input) {
        inputPanel = input;
        waveNumber = 0;

        towers = new ArrayList<Tower>();
        monsters = new ArrayList<Monster>();
        constructPath();
        setPreferredSize(new Dimension(HEIGHT, WIDTH));
        setBackground(Color.WHITE);

        addMouseListener(new ClickListener());

        int xStart = (int) path.get(0).getStart().getX();
        int yStart = (int) path.get(0).getStart().getY();

        waveTimer = new Timer(150, new WaveListener(xStart, yStart));
        inputPanel.assignWaveTimer(waveTimer);

        gameTimer = new Timer(30, new GameListener());
        gameTimer.start();

        previouslyFastForward = false;
    }

    /**
    * Deals with the drawing of the components for the GUI
    *
    * @param g The Graphics object used to draw things to the screen
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));
        drawAll(g);
    }

    /**
    * Make the Towers fire to the farthest along Monster if in range
    */
    public void fireTowers() {
        for (int i = 0; i < towers.size(); i++) {
            for (int j = 0; j < monsters.size(); j++) {
                Monster curMonster = monsters.get(j);
                if (i < towers.size() && towers.get(i).canReach(curMonster)) {
                    towers.get(i).fire(curMonster);
                    i++;
                    j = -1;
                }
            }
        }
    }

    /**
    * Checks for Monster deaths and reaching the end. It gathers the amount
    * of score lost from unkilled Monsters and money generated from killed
    * monsters
    *
    * @return int[] The array of score lost and money gained
    */
    public int[] checkForDeath() {
        int[] stats = new int[2];
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).didReachEnd()) {
                stats[0] += monsters.get(i).getScoreLoss();
                monsters.remove(i);
                i--;
            } else if (monsters.get(i).isDead()) {
                stats[1] += monsters.get(i).getMoneyValue();
                monsters.remove(i);
                i--;
            }
        }
        return stats;
    }

    /**
    * Moves all Monsters in the game, also sends new waves and deals with
    * fast forward needs
    */
    public void moveAll() {
        if (inputPanel.isInFastForward() && !previouslyFastForward) {
            gameTimer.stop();
            gameTimer = new Timer(1, new GameListener());
            previouslyFastForward = true;
            gameTimer.start();
        } else if (!inputPanel.isInFastForward() && previouslyFastForward) {
            gameTimer.stop();
            gameTimer = new Timer(30, new GameListener());
            previouslyFastForward = false;
            gameTimer.start();
        }
        if (inputPanel.getWaveNumber() > waveNumber) {
            waveNumber++;
            sendWave();
        }
        for (Monster monster : monsters) {
            monster.setBeingAttacked(false);
            monster.move(path);
        }
    }

    private void orderMonsters() {
        Collections.sort(monsters);
    }

    private void drawAll(Graphics g) {
        for (Tower tower : towers) {
            tower.drawReach(g);
        }

        for (Tower tower : towers) {
            tower.drawTower(g);
        }

        for (Line line : path) {
            line.draw(g);
        }

        for (Monster monster : monsters) {
            monster.draw(g);
        }
    }

    private void sendWave() {
        if (!(waveTimer != null && waveTimer.isRunning())) {
            waveTimer.start();
        }
    }

    private void constructPath() {
        path = new ArrayList<Line>(5);
        path.add(new Line(700, 600, 200, 600));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                                                        new Point(200, 100)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                                                        new Point(600, 100)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                                                        new Point(600, 400)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                                                        new Point(0, 400)));
    }

    private class GameListener implements ActionListener {

        /**
        * Required actionPerformed method
        *
        * @param e The ActionEvent that happens with each tick of the game
        * Timer
        */
        public void actionPerformed(ActionEvent e) {
            if (inputPanel.isStillPlaying() || !monsters.isEmpty()) {
                moveAll();
                orderMonsters();
                fireTowers();
                inputPanel.updateStats(checkForDeath());
                repaint();
            } else {
                if (waveNumber > 50 && monsters.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You won! "
                                                        + "Congratulations!");
                    System.exit(0);
                } else if (inputPanel.getScore() <= 0) {
                    JOptionPane.showMessageDialog(null, "You Lost! Better "
                                                        + "luck next time!");
                    System.exit(0);
                }
            }
        }
    }

    private class WaveListener implements ActionListener {

        private int waveComparator, xStart, yStart;
        private int numTimes;

        /**
        * The WaveListener constructor
        *
        * @param xStart The starting x coordinate of Monsters being created at
        * the start of a wave
        * @param yStart The starting y coordinate of Monsters being created at
        * the start of a wave
        */
        public WaveListener(int xStart, int yStart) {
            this.waveComparator = 2 * waveNumber;
            this.xStart = xStart;
            this.yStart = yStart;
            numTimes = 0;
        }

        /**
        * Required actionPerformed method
        *
        * @param e The ActionEvent that happens with each tick of the wave
        * Timer which releases Monsters during the start of a new wave
        * at structured time intervals
        */
        public void actionPerformed(ActionEvent e) {
            numTimes++;
            waveComparator = waveNumber * 2;
            Random rand = new Random();
            if (numTimes > 10) {
                waveTimer.stop();
                numTimes = 0;
            } else {
                if (waveComparator < 6) {
                    numTimes += (10 - 2 * waveComparator);
                    monsters.add(new RegularMonster(xStart, yStart));
                } else {
                    int random = rand.nextInt(waveComparator) + 1;
                    if (random < 10 && waveNumber < 15) {
                        monsters.add(new RegularMonster(xStart, yStart));
                    } else if (random < 20 && waveNumber < 20) {
                        monsters.add(new FastMonster(xStart, yStart));
                    } else if (random < 30 && waveNumber < 35) {
                        monsters.add(new FasterMonster(xStart, yStart));
                    } else if (random < 50 && waveNumber < 30) {
                        //numTimes++;
                        monsters.add(new HardMonster(xStart, yStart));
                    } else if (random < 80 && waveNumber < 40) {
                        numTimes++;
                        monsters.add(new IntenseMonster(xStart, yStart));
                    } else {
                        if (waveNumber < 45) {
                            numTimes += 5;
                        } else if (waveNumber < 48) {
                            numTimes += 3;
                        } else {
                            numTimes++;
                        }
                        monsters.add(new UltimateMonster(xStart, yStart));
                    }
                }
            }
        }
    }

    private class ClickListener extends MouseAdapter {

        /**
        * Creates a new Tower based on location and Tower type
        *
        * @param p The location of the new Tower
        * @param tower The int representation of the new Tower type
        * @return The newly constructed tower
        */
        public Tower createTower(Point p, int tower) {
            Tower result = null;
            if (tower == Tower.BASIC_TOWER) {
                result = new BasicTower(p);
            } else if (tower == Tower.ADVANCED_TOWER) {
                result = new AdvancedTower(p);
            }
            if (!result.canPlaceOnMap(towers)
                            || !inputPanel.removeFromMoney(result.getCost())) {
                return null;
            } else {
                return result;
            }
        }

        /**
        * Required mousePressed method
        *
        * @param e The MouseEvent that happens with a click of the Mouse in
        * the Game Panel
        */
        public void mousePressed(MouseEvent e) {
            Point originalLoc = e.getPoint();
            Point loc = new Point((int) originalLoc.getX() - 12,
                                                (int) originalLoc.getY() - 12);
            int type = inputPanel.getCurrentTowerType();
            Tower newTower = createTower(loc, type);
            if (newTower != null) {
                towers.add(newTower);
            }
            repaint();
        }
    }
}