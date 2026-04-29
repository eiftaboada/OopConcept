package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterStory extends JPanel {

    private JFrame frame;
    private Image background;
    private String characterName;

    private JLabel storyLabel;

    private String[] story;
    private int index = 0;

    private JButton next, ready, back;

    public CharacterStory(JFrame frame, Image bg, String characterName) {
        this.frame = frame;
        this.background = bg;
        this.characterName = characterName;

        setLayout(null);

        story = getStory(characterName);

        // text
        storyLabel = new JLabel("", SwingConstants.CENTER);
        storyLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
        storyLabel.setForeground(Color.WHITE);
        add(storyLabel);

        updateText();

        // buttons
        next = createButton("NEXT ▶");
        ready = createButton("READY");
        back = createButton("◀ BACK");

        add(next);
        add(ready);
        add(back);

        next.addActionListener(e -> {
            index++;
            if (index < story.length) {
                updateText();
            } else {
                goToGame();
            }
        });

        ready.addActionListener(e -> goToGame());
        back.addActionListener(e -> goBack());

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposition();
            }
        });

        SwingUtilities.invokeLater(this::reposition);
    }

    private void reposition() {

        int w = getWidth();
        int h = getHeight();

        if (w <= 0 || h <= 0) return;

        // STORY BOX CENTERED
        storyLabel.setBounds(60, h / 2 - 80, w - 120, 140);

        // BUTTONS POSITIONED RELATIVE TO SCREEN
        next.setBounds(w - 220, h - 80, 140, 40);
        ready.setBounds(80, h - 80, 140, 40);
        back.setBounds(80, 40, 140, 40);
    }

  //stylish sa button
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btn.setFocusPainted(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }


    private void updateText() {
        storyLabel.setText("<html><div style='text-align:center; width:100%;'>"
                + story[index] + "</div></html>");
    }

    // ================= GAME =================
    private void goToGame() {

        System.out.println("GO TO GAME CLICKED");

        LevelOneFightingScene level = new LevelOneFightingScene(frame, characterName);

        frame.getContentPane().removeAll();   // IMPORTANT
        frame.setContentPane(level);

        frame.revalidate();
        frame.repaint();
    }

    // ================= BACK =================
    private void goBack() {

        Image bg = new ImageIcon(
                getClass().getResource("/images/backgroundIntro.jpg")
        ).getImage();

        frame.setContentPane(new CharacterSelection(frame, bg));
        frame.revalidate();
        frame.repaint();
    }

    // ================= STORY =================
    private String[] getStory(String name) {

        return switch (name) {

            case "Noah" -> new String[]{
                    " ⚔ NOAH (Sword) Passive: Last Stand – Low HP increases damage and defense. At critical HP, gains lifesteal.",
                    "BACKGROUND STORY: He comes from the Underground… closest to the Core, closest to collapse. He saw everything fall apart. The guardian he once trusted became the King of Death. Since that day, he chose to fight. Not for fame… not for power… But because no one else would.",
                    "\"If I don’t stand now… nothing will remain.\""
            };

            case "Ashe" -> new String[]{
                    "🏹 ASHE (Bow) Passive: Survival Instinct – More damage at range, faster attack when alone.",
                    "BACKGROUND STORY: She survived the Desert… where nothing lives for long. Water is rare. Trust is rarer. When the world broke, people didn’t unite… they fought to survive. She learned quickly— hesitate, and you die. She doesn’t believe in heroes. But she knows one thing…",
                    "\"If the Core isn’t restored… no one survives.\""
            };

            case "Quinn" -> new String[]{
                    "🪄 QUINN (Wand) Passive: Forbidden Insight – Marks enemies, increasing damage taken and revealing weakness.",
                    "BACKGROUND STORY: She wandered the Space region… where silence speaks louder than sound. Among the ruins, she found fragments of knowledge. Records of a world that shouldn’t exist anymore. The Core didn’t just break… something interfered. While others fight to survive— she searches for truth.",
                    "\"This world didn’t fall by accident… and I will prove it.\""
            };

            default -> new String[]{"Unknown hero..."};
        };
    }

    // ================= BACKGROUND =================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(30, getHeight()/2 - 100, getWidth()-60, 180, 20, 20);

        g.setColor(Color.WHITE);
        g.drawRoundRect(30, getHeight()/2 - 100, getWidth()-60, 180, 20, 20);
    }
}
