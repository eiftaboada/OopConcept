package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Encounter extends JPanel {

    private final JFrame frame;
    private final String selectedCharacter;
    private final int levelNumber;

    private Image background;
    private Image narratorSprite;

    private int dialogueIndex = 0;

    private final String bossName;
    private final String[] dialogues;

    private static final Color PANEL_BG     = new Color(10, 13, 22, 215);
    private static final Color BORDER_COLOR = new Color(26, 42, 60);
    private static final Color TEXT_YELLOW  = new Color(230, 200, 60);
    private static final Color TEXT_WHITE   = new Color(200, 215, 230);
    private static final Color TEXT_DIM     = new Color(100, 120, 140);

    public Encounter(JFrame frame, String selectedCharacter, int levelNumber) {
        this.frame = frame;
        this.selectedCharacter = selectedCharacter;
        this.levelNumber = levelNumber;

        bossName  = resolveBossName(levelNumber);
        dialogues = resolveDialogues(levelNumber, selectedCharacter);

        setLayout(null);

        URL bgURL = getClass().getResource(resolveBgPath(levelNumber));
        if (bgURL != null) background = new ImageIcon(bgURL).getImage();

        URL narURL = getClass().getResource("/images/narrator.png");
        if (narURL != null)
            narratorSprite = new ImageIcon(narURL).getImage()
                    .getScaledInstance(110, 140, Image.SCALE_SMOOTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialogueIndex++;
                if (dialogueIndex >= dialogues.length) goToFight();
                else repaint();
            }
        });
    }

    private static String resolveBossName(int level) {
        return switch (level) {
            case 1 -> "Raven  [ The Blood Knight ]";
            case 2 -> "Maro  [ The Corrupted Knight ]";
            case 3 -> "Anivia  [ The Dragon Knight ]";
            case 4 -> "Zara  [ The God's Knight ]";
            case 5 -> "Quinver  [ The Final Judgement ]";
            default -> " ";
        };
    }

    private static String resolveBgPath(int level) {
        return switch (level) {
            case 1 -> "/images/LevelOneBg.jpg"; // Underground
            case 2 -> "/images/LevelTwoBg.png"; // Ocean
            case 3 -> "/images/LevelThreeBg.png"; // Snow
            case 4 -> "/images/LevelFourBg.png"; // Desert
            case 5 -> "/images/LevelFiveBg.jpg"; // Space
            default -> "/images/LevelOneBg.jpg";
        };
    }

    private static String[] resolveDialogues(int level, String character) {
        String player = (character != null) ? character : "Hero";

        return switch (level) {

            // LEVEL 1 – Raven (Underground)
            case 1 -> new String[]{
                    "You feel it, don't you? The weight of the earth above you… pressing down.",
                    "This underground tomb has swallowed warriors greater than you.",
                    "Down here, there is no escape… only silence and death.",
                    "[ " + player + " ]  \"Then I'll carve my way back to the surface.\""
            };

            // LEVEL 2 – Maro (Ocean)
            case 2 -> new String[]{
                    "The ocean surrounds you. Every drop answers to me.",
                    "You cannot run. You cannot breathe. The depths will claim you.",
                    "Even now, the tides are closing in.",
                    "[ " + player + " ]  \"Then I'll rise above your waves.\""
            };

            // LEVEL 3 – Anivia (Snow)
            case 3 -> new String[]{
                    "Cold… endless cold. It seeps into your bones, doesn't it?",
                    "Out here, warmth is a memory… and hope freezes just as quickly.",
                    "I lost everything to this storm… even my emotions.",
                    "[ " + player + " ]  \"Then I'll be the fire that melts it.\""
            };

            // LEVEL 4 – Zara (Desert)
            case 4 -> new String[]{
                    "The desert stretches endlessly. No shade. No mercy.",
                    "Every step you take sinks you deeper into your grave.",
                    "The sandstorm rises… and it will blind you before it buries you.",
                    "[ " + player + " ]  \"Then I'll walk straight through your storm.\""
            };

            // LEVEL 5 – Quinver (Space)
            case 5 -> new String[]{
                    "Look around you… there is nothing. No ground. No sky. Only void.",
                    "Out here, existence itself bends to my will.",
                    "You have nowhere left to stand… nowhere left to run.",
                    "[ " + player + " ]  \"Then I'll fight, even in the void.\""
            };

            default -> new String[]{
                    "A challenger approaches.",
                    "[ " + player + " ]  \"Let's finish this.\""
            };
        };
    }

    protected void goToFight() {
        JPanel scene = switch (levelNumber) {
            case 1 -> new LevelOneFightingScene(frame, selectedCharacter);
            case 2 -> new LevelTwoFightingScene(frame, selectedCharacter);
            case 3 -> new LevelThreeFightingScene(frame, selectedCharacter);
            case 4 -> new LevelFourFightingScene(frame, selectedCharacter);
            case 5 -> new LevelFiveFightingScene(frame, selectedCharacter);
            default -> new LevelOneFightingScene(frame, selectedCharacter);
        };
        frame.setContentPane(scene);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        if (background != null) {
            g2.drawImage(background, 0, 0, w, h, this);
            g2.setColor(new Color(0, 0, 0, 120));
            g2.fillRect(0, 0, w, h);
        }

        int boxX = 170;
        int boxY = h - 200;
        int boxW = w - 220;

        if (narratorSprite != null)
            g2.drawImage(narratorSprite, 20, boxY - 80, this);

        g2.setColor(PANEL_BG);
        g2.fillRoundRect(boxX, boxY, boxW, 150, 10, 10);
        g2.setColor(BORDER_COLOR);
        g2.drawRoundRect(boxX, boxY, boxW, 150, 10, 10);

        String currentLine = dialogues[Math.min(dialogueIndex, dialogues.length - 1)];
        boolean isPlayer = currentLine.startsWith("[ ");

        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        g2.setColor(isPlayer ? new Color(100, 200, 255) : TEXT_YELLOW);
        g2.drawString(isPlayer ? selectedCharacter.toUpperCase() : bossName,
                boxX + 15, boxY + 20);

        g2.setFont(new Font("Serif", Font.PLAIN, 14));
        g2.setColor(TEXT_WHITE);
        drawWrapped(g2, currentLine, boxX + 15, boxY + 45, boxW - 30, 20);


        String hint = "[ Click anywhere to begin the battle ]";

        g2.setFont(new Font("Consolas", Font.PLAIN, 11));
        g2.setColor(TEXT_DIM);

        FontMetrics fm = g2.getFontMetrics();
        int hintX = boxX + boxW - fm.stringWidth(hint) - 10;
        int hintY = boxY + 140;

        g2.drawString(hint, hintX, hintY);
    }

    private void drawWrapped(Graphics2D g2, String text, int x, int y, int maxW, int lineH) {

        String display = text.startsWith("[ ") ? text.replaceFirst("\\[ .+ ]\\s+", "") : text;

        FontMetrics fm = g2.getFontMetrics();
        StringBuilder line = new StringBuilder();
        int curY = y;

        for (String word : display.split(" ")) {
            String test = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(test) > maxW) {
                g2.drawString(line.toString(), x, curY);
                curY += lineH;
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(test);
            }
        }
        if (line.length() > 0) g2.drawString(line.toString(), x, curY);
    }
}
