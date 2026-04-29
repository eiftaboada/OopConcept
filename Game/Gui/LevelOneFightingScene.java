package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LevelOneFightingScene extends JPanel {

    private JFrame frame;
    private String selectedCharacter;
    private Image background;
    private JTextArea battleText;
    private JPanel topPanel;
    private JPanel bottomUI;

    private JLabel player;
    private JLabel enemy;
    private JLabel playerName;
    private JLabel enemyName;

    private JProgressBar playerHP;
    private JProgressBar enemyHP;

    private JButton attackBtn, skill1Btn, skill2Btn, skill3Btn;

    public LevelOneFightingScene(JFrame frame, String selectedCharacter) {
        this.frame = frame;
        this.selectedCharacter = selectedCharacter;
        this.player = player;

        setLayout(null);
      
        // BACKGROUND
       
        java.net.URL bgURL = getClass().getResource("/images/LevelOneBg.jpg");
        if (bgURL != null) {
            background = new ImageIcon(bgURL).getImage();
        }

      
        // TOP PANEL
 
        topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 180));
        add(topPanel);

        JLabel levelLabel = new JLabel("LEVEL 1", SwingConstants.CENTER);
        levelLabel.setForeground(Color.YELLOW);
        levelLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        topPanel.add(levelLabel);

        battleText = new JTextArea("Battle started...");
        battleText.setEditable(false);
        battleText.setBackground(Color.BLACK);
        battleText.setForeground(Color.WHITE);
        battleText.setFont(new Font("Monospaced", Font.PLAIN, 13));
        battleText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        topPanel.add(battleText);

        // PLAYER ni design
   
        player = new JLabel();
        player.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL playerURL = getClass().getResource("/images/" + selectedCharacter + ".png");
        if (playerURL != null) {
            ImageIcon icon = new ImageIcon(playerURL);
            player.setIcon(new ImageIcon(icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        }
        add(player);

        playerName = new JLabel(selectedCharacter, SwingConstants.CENTER);
        playerName.setForeground(Color.WHITE);
        playerName.setFont(new Font("Monospaced", Font.BOLD, 17));
        add(playerName);

        playerHP = new JProgressBar(0, 100);
        playerHP.setValue(100);
        playerHP.setForeground(new Color(220, 20, 60));
        playerHP.setBackground(Color.DARK_GRAY);
        playerHP.setBorderPainted(false);
        add(playerHP);

        // ENEMY ni design
       
        enemy = new JLabel();
        enemy.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL enemyURL = getClass().getResource("/images/Raven.png");
        if (enemyURL != null) {
            ImageIcon icon = new ImageIcon(enemyURL);
            enemy.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        }
        add(enemy);

        enemyName = new JLabel("RAVEN", SwingConstants.CENTER);
        enemyName.setForeground(Color.WHITE);
        enemyName.setFont(new Font("Monospaced", Font.BOLD, 17));
        add(enemyName);

        enemyHP = new JProgressBar(0, 100);
        enemyHP.setValue(100);
        enemyHP.setForeground(new Color(220, 20, 60));
        enemyHP.setBackground(Color.DARK_GRAY);
        enemyHP.setBorderPainted(false);
        add(enemyHP);

        // BOTTOM UI
      
        bottomUI = new JPanel(null);
        bottomUI.setBackground(new Color(0, 0, 0, 200));
        add(bottomUI);

        attackBtn = createButton("ATTACK");
        skill1Btn = createButton("SKILL 1");
        skill2Btn = createButton("SKILL 2");
        skill3Btn = createButton("SKILL 3");

        bottomUI.add(attackBtn);
        bottomUI.add(skill1Btn);
        bottomUI.add(skill2Btn);
        bottomUI.add(skill3Btn);

        // BUTTONs siyaa

        attackBtn.addActionListener(e ->
                battleText.setText(selectedCharacter + " used ATTACK!")
        );

        skill1Btn.addActionListener(e ->
                battleText.setText(selectedCharacter + " used SKILL 1!")
        );

        skill2Btn.addActionListener(e ->
                battleText.setText(selectedCharacter + " used SKILL 2!")
        );

        skill3Btn.addActionListener(e ->
                battleText.setText(selectedCharacter + " used SKILL 3!")
        );

      
        // responsive kunuhay
 
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        updateLayout();
    }

    
    private void updateLayout() {
        int w = getWidth();
        int h = getHeight();

        // TOP
        topPanel.setBounds(0, 0, w, 90);

        ((JLabel) topPanel.getComponent(0)).setBounds(w / 2 - 100, 5, 200, 25);
        battleText.setBounds(w / 2 - 250, 35, 500, 40);
      
        player.setBounds(w / 6, h / 3, 180, 180);
        playerHP.setBounds(w / 6, h / 3 - 25, 180, 14);
        playerName.setBounds(w / 6, h / 3 + 190, 180, 20);

        int enemyX = w - 320;
        int enemyY = 180;


        enemy.setBounds(enemyX, enemyY, 200, 200);

        enemyHP.setBounds(enemyX, enemyY - 25, 200, 14);


        enemyName.setBounds(enemyX, enemyY + 200, 200, 20);
        enemyName.setHorizontalAlignment(SwingConstants.CENTER);
        bottomUI.setBounds(0, h - 150, w, 150);

        int btnW = w / 5;
        int startX = w / 2 - (btnW * 2);

        attackBtn.setBounds(startX, 40, btnW - 10, 50);
        skill1Btn.setBounds(startX + btnW, 40, btnW - 10, 50);
        skill2Btn.setBounds(startX + btnW * 2, 40, btnW - 10, 50);
        skill3Btn.setBounds(startX + btnW * 3, 40, btnW - 10, 50);
    }
  
    // BUTTON STYLE
   
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return btn;
    }

  
    // background

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 90));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
