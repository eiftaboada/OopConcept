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

    public Encounter(JFrame frame, String selectedCharacter, int levelNumber) {
        this.frame = frame;
        this.selectedCharacter = selectedCharacter;
        this.levelNumber = levelNumber;

        bossName = resolveBossName(levelNumber);
        dialogues = resolveDialogues(levelNumber, selectedCharacter);

        setLayout(null);

        URL bgURL = getClass().getResource(resolveBgPath(levelNumber));
        if (bgURL != null) background = new ImageIcon(bgURL).getImage();

        URL narURL = getClass().getResource("/images/narrator.png");
        if (narURL != null) narratorSprite = new ImageIcon(narURL).getImage();

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
            case 1 -> "Raven (The Blood Knight)";
            case 2 -> "Maro (The Corrupted Knight)";
            case 3 -> "Anivia (The Dragon Knight)";
            case 4 -> "Zara (The God's Knight)";
            case 5 -> "Quinver (The Final Judgement)";
            default -> "";
        };
    }

    private static String resolveBgPath(int level) {
        return switch (level) {
            case 1 -> "/images/LevelOneBg.jpg";
            case 2 -> "/images/LevelTwoBg.png";
            case 3 -> "/images/LevelThreeBg.png";
            case 4 -> "/images/LevelFourBg.png";
            case 5 -> "/images/LevelFiveBg.jpg";
            default -> "/images/LevelOneBg.jpg";
        };
    }

    private static String[] resolveDialogues(int level, String character) {
        String player = (character != null) ? character : "Hero";

        return switch (level) {
            //raven (underground)
            case 1 -> new String[]{
                    "You feel it, don't you? The weight of the earth above you… pressing down.",
                    "This underground tomb has swallowed warriors greater than you.",
                    "Down here, there is no escape… only silence and death.",
                    "[ " + player + " ]  \"Then I'll carve my way back to the surface.\""
            };
            //maro (ocean)
            case 2 -> new String[]{
                    "The ocean surrounds you. Every drop answers to me.",
                    "You cannot run. You cannot breathe. The depths will claim you.",
                    "Even now, the tides are closing in.",
                    "[ " + player + " ]  \"Then I'll rise above your waves.\""
            };
            //anivia (snow)
            case 3 -> new String[]{
                    "Cold… endless cold. It seeps into your bones, doesn't it?",
                    "Out here, warmth is a memory… and hope freezes just as quickly.",
                    "I lost everything to this storm… even my emotions.",
                    "[ " + player + " ]  \"Then I'll be the fire that melts it.\""
            };
            //zara (desert)
            case 4 -> new String[]{
                    "The desert stretches endlessly. No shade. No mercy.",
                    "Every step you take sinks you deeper into your grave.",
                    "The sandstorm rises… and it will blind you before it buries you.",
                    "[ " + player + " ]  \"Then I'll walk straight through your storm.\""
            };
            //quinver (space)
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
            g2.setColor(new Color(0, 0, 0, 140));
            g2.fillRect(0, 0, w, h);
        }

        int boxMargin = (int)(w * 0.05);
        int boxWidth = w - boxMargin * 2;
        int boxHeight = (int)(h * 0.22);
        int boxX = boxMargin;
        int boxY = h - boxHeight - (int)(h * 0.05);

        int spriteW = (int)(w * 0.10);
        int spriteH = (int)(h * 0.20);

        int spriteX = boxX + 10;
        int spriteY = boxY - spriteH + 38;

        if (narratorSprite != null) {
            g2.drawImage(narratorSprite, spriteX, spriteY, spriteW, spriteH, this);
        }

        g2.setColor(new Color(5, 10, 18, 200));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setColor(new Color(40, 70, 110));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);

        int padding = (int)(w * 0.025);

        int titleY = boxY + padding + (int)(h * 0.012);
        int textStartY = titleY + (int)(h * 0.045);

        int textX = boxX + padding;

        String currentLine = dialogues[Math.min(dialogueIndex, dialogues.length - 1)];
        boolean isPlayer = currentLine.startsWith("[ ");

        int titleSize = Math.max(20, (int)(h * 0.04));
        int textSize = Math.max(18, (int)(h * 0.033));
        int hintSize = Math.max(12, (int)(h * 0.022));

        g2.setFont(new Font("Serif", Font.PLAIN, titleSize));
        g2.setColor(new Color(230, 200, 60));

        String title = (isPlayer ? selectedCharacter.toUpperCase() : bossName);
        g2.drawString(title, textX, titleY);

        g2.setFont(new Font("Serif", Font.PLAIN, textSize));
        g2.setColor(new Color(210, 220, 235));

        drawWrapped(g2, currentLine,
                textX,
                textStartY,
                boxWidth - padding * 2,
                (int)(h * 0.05)
        );

        String hint = "[ Click anywhere to continue ]";

        g2.setFont(new Font("Consolas", Font.PLAIN, hintSize));
        g2.setColor(new Color(120, 140, 160));

        FontMetrics fm = g2.getFontMetrics();
        int hintX = boxX + boxWidth - fm.stringWidth(hint) - padding;
        int hintY = boxY + boxHeight - padding;

        g2.drawString(hint, hintX, hintY);
    }

    private void drawWrapped(Graphics2D g2, String text, int x, int y, int maxW, int lineH) {

        String display = text.startsWith("[ ")
                ? text.replaceFirst("\\[ .+ ]\\s+", "")
                : text;

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

        if (line.length() > 0) {
            g2.drawString(line.toString(), x, curY);
        }
    }
}
