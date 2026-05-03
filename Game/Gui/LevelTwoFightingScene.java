package game.gui;

import game.characters.*;
import game.bosses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LevelTwoFightingScene extends JPanel {

    private JFrame frame;
    private String selectedCharacter;

    private game.characters.Character playerChar;
    private Boss boss;
    private Image background;

    private JTextArea battleText;

    private JPanel topPanel;
    private JPanel bottomUI;

    private JLabel playerLabel, enemy, playerName, enemyName;
    private JProgressBar playerHP, enemyHP;

    private JButton attackBtn, skill1Btn, skill2Btn, skill3Btn;

    public LevelTwoFightingScene(JFrame frame, String selectedCharacter) {
        this.frame = frame;
        this.selectedCharacter = selectedCharacter;

        setLayout(null);

        // SAME CHARACTER LOGIC
        if (selectedCharacter.equalsIgnoreCase("Noah")) {
            playerChar = new Noah();
        } else if (selectedCharacter.equalsIgnoreCase("Ashe")) {
            playerChar = new Ashe();
        } else {
            playerChar = new Quinn();
        }

        // NEW BOSS (change this depending sa boss class ninyo)
        boss = new Maro(2); // Level 2 version

        // OPTIONAL NEW BACKGROUND
        java.net.URL bgURL = getClass().getResource("/images/LevelTwoBg.png");
        if (bgURL != null) {
            background = new ImageIcon(bgURL).getImage();
        }

        topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 160));
        add(topPanel);

        JLabel levelLabel = new JLabel("LEVEL 2 - MARO BOSS", SwingConstants.CENTER);
        levelLabel.setForeground(Color.ORANGE);
        levelLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        topPanel.add(levelLabel);

        battleText = new JTextArea();
        battleText.setEditable(false);
        battleText.setBackground(new Color(10, 10, 10, 220));
        battleText.setForeground(new Color(255, 200, 0));
        battleText.setFont(new Font("Consolas", Font.BOLD, 13));
        battleText.setLineWrap(true);
        battleText.setWrapStyleWord(true);
        battleText.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        battleText.setMargin(new Insets(5, 5, 5, 5));
        topPanel.add(battleText);


        playerLabel = new JLabel();
        java.net.URL playerURL = getClass().getResource("/images/" + selectedCharacter + ".png");
        if (playerURL != null) {
            ImageIcon icon = new ImageIcon(playerURL);
            playerLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        }
        add(playerLabel);

        playerName = new JLabel(selectedCharacter, SwingConstants.CENTER);
        playerName.setForeground(Color.WHITE);
        add(playerName);

        playerHP = new JProgressBar(0, playerChar.getMaxHP());
        playerHP.setValue(playerChar.getHP());
        add(playerHP);


        enemy = new JLabel();
        java.net.URL enemyURL = getClass().getResource("/images/Maro.png");
        if (enemyURL != null) {
            ImageIcon icon = new ImageIcon(enemyURL);
            enemy.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        }
        add(enemy);

        enemyName = new JLabel("MARO", SwingConstants.CENTER);
        enemyName.setForeground(Color.WHITE);
        add(enemyName);

        enemyHP = new JProgressBar(0, boss.getBossMaxHP());
        enemyHP.setValue(boss.getBossHP());
        enemyHP.setForeground(Color.RED);
        add(enemyHP);


        bottomUI = new JPanel(null);
        add(bottomUI);

        attackBtn = createButton("ATTACK");
        skill1Btn = createButton("SKILL 1");
        skill2Btn = createButton("SKILL 2");
        skill3Btn = createButton("SKILL 3");

        bottomUI.add(attackBtn);
        bottomUI.add(skill1Btn);
        bottomUI.add(skill2Btn);
        bottomUI.add(skill3Btn);

        attackBtn.addActionListener(e -> playerTurn(1));
        skill1Btn.addActionListener(e -> playerTurn(1));
        skill2Btn.addActionListener(e -> playerTurn(2));
        skill3Btn.addActionListener(e -> playerTurn(3));

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        updateLayout();
        addLog("🔥 LEVEL 2 STARTED!");
    }

    private void playerTurn(int skill) {
        float dmg = playerChar.attack(skill);
        boss.setBossHP(boss.getBossHP() - (int)dmg);

        updateBars();

        if (boss.getBossHP() <= 0) {
            addLog("🎉 YOU BEAT LEVEL 2!");
            disableButtons();

            Timer delay = new Timer(1500, e -> goToStore());
            delay.setRepeats(false);
            delay.start();
            return;
        }

        float dmgToPlayer = boss.attack();

        int skillUsed = boss.getBossSkillChosen();

        String skillName = "";
        if (skillUsed == 1) skillName = boss.getBossSkill1();
        if (skillUsed == 2) skillName = boss.getBossSkill2();
        if (skillUsed == 3) skillName = boss.getBossSkill3();

        addLog("🔴 MARO TURN");
        addLog("⚔ " + skillName);
        addLog("💥 Damage: " + (int)dmgToPlayer);

        playerChar.takeDamage(dmgToPlayer);

    // cooldown ni diei
        boss.bossUpdate();
    }

    private void updateBars() {
        playerHP.setValue(Math.max(playerChar.getHP(), 0));
        enemyHP.setValue(Math.max(boss.getBossHP(), 0));
    }

    private void disableButtons() {
        attackBtn.setEnabled(false);
        skill1Btn.setEnabled(false);
        skill2Btn.setEnabled(false);
        skill3Btn.setEnabled(false);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void addLog(String text) {
        battleText.append(text + "\n");
    }

    private void updateLayout() {
        int w = getWidth();
        int h = getHeight();


        topPanel.setBounds(0, 0, w, 140);

        int centerX = w / 2;


        topPanel.getComponent(0).setBounds(centerX - 200, 5, 400, 25);


        battleText.setBounds(20, 35, w - 40, 90);


        int charY = h / 3;

        playerLabel.setBounds(w / 6 - 90, charY, 180, 180);
        playerHP.setBounds(w / 6 - 90, charY - 25, 180, 15);
        playerName.setBounds(w / 6 - 90, charY + 185, 180, 20);

        // enemy layout
        enemy.setBounds((w * 5 / 6) - 100, charY, 200, 200);
        enemyHP.setBounds((w * 5 / 6) - 100, charY - 25, 200, 15);
        enemyName.setBounds((w * 5 / 6) - 100, charY + 185, 200, 20);

        //
        bottomUI.setBounds(0, h - 120, w, 120);

        int btnWidth = 130;
        int btnHeight = 40;
        int gap = 15;

        int totalWidth = (btnWidth * 4) + (gap * 3);
        int startX = (w - totalWidth) / 2;

        attackBtn.setBounds(startX, 30, btnWidth, btnHeight);
        skill1Btn.setBounds(startX + (btnWidth + gap), 30, btnWidth, btnHeight);
        skill2Btn.setBounds(startX + (btnWidth + gap) * 2, 30, btnWidth, btnHeight);
        skill3Btn.setBounds(startX + (btnWidth + gap) * 3, 30, btnWidth, btnHeight);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }


    protected void goToStore() {
        Image introBg = new ImageIcon(
                getClass().getResource("/images/backgroundIntro.jpg")
        ).getImage();

        // fro level d3 ni siyaa wapaman tay panel so just wayt
        frame.setContentPane(new Store(frame, introBg, selectedCharacter, 3));
        frame.revalidate();
        frame.repaint();
    }
}
