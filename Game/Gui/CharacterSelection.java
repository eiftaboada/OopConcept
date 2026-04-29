
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CharacterSelection extends JPanel {
    private JFrame frame;
    private Image background;
    private JPanel selectedPanel = null;
    private String selectedCharacter = null;
    private JButton startBtn;
    private JPanel[] characterPanels;
    private String[] characters = new String[]{"Noah", "Ashe", "Quinn"};

    public CharacterSelection(JFrame frame, Image bg) {
        this.frame = frame;
        this.background = bg;
        this.setLayout((LayoutManager)null);
        this.setOpaque(false);
        final JLabel title = new JLabel("PLEASE CHOOSE ONE CHARACTER", 0);
        title.setFont(new Font("Monospaced", 1, 32));
        title.setForeground(Color.WHITE);
        this.add(title);
        this.characterPanels = new JPanel[this.characters.length];

        for(int i = 0; i < this.characters.length; ++i) {
            JPanel panel = this.createCharacterPanel(this.characters[i]);
            this.characterPanels[i] = panel;
            this.add(panel);
        }

        this.startBtn = new JButton("START");
        this.startBtn.setEnabled(false);
        this.startBtn.setFont(new Font("Monospaced", 1, 16));
        this.startBtn.setBackground(Color.BLACK);
        this.startBtn.setForeground(Color.WHITE);
        this.startBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        this.add(this.startBtn);
        this.startBtn.addActionListener((e) -> this.startGame());
        this.addComponentListener(new ComponentAdapter() {
            {
                Objects.requireNonNull(CharacterSelection.this);
            }

            public void componentResized(ComponentEvent e) {
                CharacterSelection.this.reposition(title);
            }
        });
        SwingUtilities.invokeLater(() -> this.reposition(title));
        SwingUtilities.invokeLater(() -> {
            this.revalidate();
            this.repaint();
        });
    }

    private JPanel createCharacterPanel(final String name) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        JLabel image = this.createImage(name);
        JLabel label = new JLabel(name, 0);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", 1, 14));
        panel.add(image, "Center");
        panel.add(label, "South");
        panel.addMouseListener(new MouseAdapter() {
            {
                Objects.requireNonNull(CharacterSelection.this);
            }

            public void mouseClicked(MouseEvent e) {
                CharacterSelection.this.selectCharacter(panel, name);
            }

            public void mouseEntered(MouseEvent e) {
                if (panel != CharacterSelection.this.selectedPanel) {
                    panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                }

            }

            public void mouseExited(MouseEvent e) {
                if (panel != CharacterSelection.this.selectedPanel) {
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                }

            }
        });
        return panel;
    }

    private JLabel createImage(String name) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(0);
        URL imgURL = this.getClass().getClassLoader().getResource("images/" + name + ".png");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(110, 110, 4);
            label.setIcon(new ImageIcon(img));
        } else {
            label.setText("NO IMG");
            label.setForeground(Color.RED);
        }

        label.setPreferredSize(new Dimension(110, 110));
        return label;
    }

    private void selectCharacter(JPanel panel, String name) {
        if (this.selectedPanel != null) {
            this.selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }

        this.selectedPanel = panel;
        this.selectedCharacter = name;
        panel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        this.startBtn.setEnabled(true);
    }

    private void startGame() {
        if (this.selectedCharacter == null) {
            JOptionPane.showMessageDialog(this, "Select a character first!");
        } else {
            Image bg = (new ImageIcon(this.getClass().getResource("/images/backgroundIntro.jpg"))).getImage();
            this.frame.setContentPane(new CharacterStory(this.frame, bg, this.selectedCharacter));
            this.frame.revalidate();
            this.frame.repaint();
        }
    }

    private void reposition(JLabel title) {
        int w = this.getWidth();
        if (w > 0) {
            title.setBounds(0, 60, w, 40);
            int size = 140;
            int spacing = 50;
            int count = this.characters.length;
            int totalWidth = count * size + (count - 1) * spacing;
            int startX = Math.max((w - totalWidth) / 2, 0);
            int y = 180;

            for(int i = 0; i < this.characterPanels.length; ++i) {
                this.characterPanels[i].setBounds(startX + i * (size + spacing), y, size, 170);
            }

            this.startBtn.setBounds((w - 200) / 2, 420, 200, 50);
            this.repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
