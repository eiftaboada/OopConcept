package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Intro {

    private Image background;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Intro().createUI());
    }

    public void createUI() {
        JFrame frame = new JFrame("C.O.R.E");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        background = new ImageIcon(
                getClass().getResource("/images/backgroundIntro.jpg")
        ).getImage();

        frame.setContentPane(new TitlePanel(frame, background));
        frame.setVisible(true);
    }

    // ================= TITLE PANEL =================
    class TitlePanel extends JPanel {

        private Image backgroundImage;
        private JButton startButton, descButton, quitButton;
        private JFrame frame;

        public TitlePanel(JFrame frame, Image bg) {
            this.frame = frame;
            this.backgroundImage = bg;
            setLayout(null);

            startButton = createButton("START");
            descButton = createButton("DESCRIPTION");
            quitButton = createButton("QUIT");

            add(startButton);
            add(descButton);
            add(quitButton);

            centerButtons();

            startButton.addActionListener(e -> goToLoading());
            descButton.addActionListener(e -> goToDescription());
            quitButton.addActionListener(e -> System.exit(0));

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    centerButtons();
                }
            });
        }

        private JButton createButton(String text) {
            JButton btn = new JButton(text);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Monospaced", Font.BOLD, 28));
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            btn.setSize(300, 60);

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

        private void centerButtons() {
            int w = getWidth();
            int h = getHeight();

            int x = (w - 300) / 2;
            int y = (h - 200) / 2;

            startButton.setLocation(x, y);
            descButton.setLocation(x, y + 80);
            quitButton.setLocation(x, y + 160);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 80));
            FontMetrics fm = g.getFontMetrics();

            String title = "C.O.R.E";
            int x = (getWidth() - fm.stringWidth(title)) / 2;
            int y = 150;

            g.drawString(title, x, y);
        }

        private void goToLoading() {
            frame.setContentPane(new LoadingPanel(frame, backgroundImage));
            frame.revalidate();
        }

        private void goToDescription() {
            frame.setContentPane(new DescriptionPanel(frame));
            frame.revalidate();
        }
    }

    // ================= LOADING PANEL =================
    class LoadingPanel extends JPanel implements ActionListener {

        private int progress = 0;
        private Timer timer;
        private JFrame frame;
        private Image backgroundImage;

        public LoadingPanel(JFrame frame, Image bg) {
            this.frame = frame;
            this.backgroundImage = bg;

            timer = new Timer(50, this);
            timer.start();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 30));

            String text = "Loading... " + progress + "%";
            int x = (getWidth() - g.getFontMetrics().stringWidth(text)) / 2;
            int y = getHeight() / 2;

            g.drawString(text, x, y);

            int barWidth = 300;
            int barX = (getWidth() - barWidth) / 2;
            int barY = y + 40;

            g.drawRect(barX, barY, barWidth, 20);
            g.fillRect(barX, barY, (barWidth * progress) / 100, 20);
        }

        public void actionPerformed(ActionEvent e) {
            progress++;

            if (progress >= 100) {
                timer.stop();
                frame.setContentPane(new StoryPanel(frame,backgroundImage));
                frame.revalidate();
            }

            repaint();
        }
    }

    // ================= STORY PANEL =================
    class StoryPanel extends JPanel {

        private Image backgroundImage;
        private JLabel phraseLabel;
        private int index = 0;

        private JButton nextBtn;
        private JButton skipBtn;


        String[] phrases = {
                "Long ago, the world was one…",
                "Balanced by a single source of power— The Core.",
                "It gave life to every land…",
                "But something went wrong.",
                "The Core… shattered.",
                "The journey begins… now."
        };

        public StoryPanel(JFrame frame, Image bg) {

            this.backgroundImage = bg;
            setLayout(null);



            phraseLabel = new JLabel("", SwingConstants.CENTER);
            phraseLabel.setForeground(Color.WHITE);
            phraseLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
            phraseLabel.setVerticalAlignment(SwingConstants.CENTER);
            phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);

            nextBtn = new JButton("NEXT ▶");
            skipBtn = new JButton("SKIP");

            styleButton(nextBtn);
            styleButton(skipBtn);

            add(phraseLabel);
            add(nextBtn);
            add(skipBtn);

            updateText();

            nextBtn.addActionListener(e -> {
                index++;
                if (index < phrases.length) {
                    updateText();
                } else {
                    goToNextScreen(frame);
                }
            });

            skipBtn.addActionListener(e -> goToNextScreen(frame));

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    reposition();
                }
            });

            SwingUtilities.invokeLater(this::reposition);
        }

        private void styleButton(JButton btn) {
            btn.setFont(new Font("Monospaced", Font.BOLD, 16));
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            btn.setFocusPainted(false);
        }

        private void updateText() {
            phraseLabel.setText("<html><div style='text-align:center;'>" + phrases[index] + "</div></html>");
        }

        private void reposition() {

            int w = getWidth();
            int h = getHeight();
            int size = 220;

            if (w <= 0 || h <= 0) return;

            phraseLabel.setBounds(60, h - 160, w - 120, 80);

            nextBtn.setBounds(w - 160, h - 80, 140, 40);
            skipBtn.setBounds(20, h - 80, 140, 40);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRoundRect(30, getHeight() - 170, getWidth() - 60, 110, 20, 20);

            g.setColor(Color.WHITE);
            g.drawRoundRect(30, getHeight() - 170, getWidth() - 60, 110, 20, 20);
        }

        private void goToNextScreen(JFrame frame) {

            Image bg = new ImageIcon(
                    getClass().getResource("/images/backgroundIntro.jpg")
            ).getImage();

            frame.setContentPane(new CharacterSelection(frame, bg));
            frame.revalidate();
            frame.repaint();
        }
    }

    //suggest ems kaning description ilisig how to play na lang

    // ================= DESCRIPTION PANEL =================
    class DescriptionPanel extends JPanel {

        private JFrame frame;

        public DescriptionPanel(JFrame frame) {
            this.frame = frame;
            setLayout(null);
            setBackground(Color.BLACK);

            JTextArea text = new JTextArea(
                    "C.O.R.E \n\n" +
                            "Restore the Core by defeating 5 corrupted rulers.\n\n" +
                            "Characters:\n" +
                            "- Noah (Sword)\n" +
                            "- Ashe (Bow)\n" +
                            "- Quinn (Wand)\n\n" +
                            "Defeat bosses across:\n" +
                            "Snow, Desert, Ocean, Space, Underground."
            );

            text.setFont(new Font("Monospaced", Font.PLAIN, 16));
            text.setForeground(Color.WHITE);
            text.setBackground(Color.BLACK);
            text.setBounds(100, 100, 700, 300);
            text.setEditable(false);

            JButton back = new JButton("BACK");
            back.setBounds(30, 30, 100, 40);
            back.addActionListener(e -> {
                frame.setContentPane(new TitlePanel(frame, background));
                frame.revalidate();
            });

            add(text);
            add(back);
        }
    }
}

//kruk kruk inamerlz
