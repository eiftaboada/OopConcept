package game.gui;

import game.characters.*;
import game.bosses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LevelOneFightingScene extends JPanel {

    private JFrame frame;
    private String selectedCharacter;

    private game.characters.Character playerChar;
    private Boss boss;
    private Image background;

    private JTextArea battleText;

    private JPanel topPanel;
    private JPanel bottomUI;

    private JLabel playerLabel;
    private JLabel enemy;
    private JLabel playerName;
    private JLabel enemyName;

    private JProgressBar playerHP;
    private JProgressBar enemyHP;

    private JButton attackBtn, skill1Btn, skill2Btn, skill3Btn;

    public LevelOneFightingScene(JFrame frame, String selectedCharacter) {
        this.frame = frame;
        this.selectedCharacter = selectedCharacter;

        setLayout(null);

        //connecting sa character
        if (selectedCharacter.equalsIgnoreCase("Noah")) {
            playerChar = new Noah();
        } else if (selectedCharacter.equalsIgnoreCase("Ashe")) {
            playerChar = new Ashe();
        } else {
            playerChar = new Quinn();
        }

        boss = new Raven(1);

        //pamaymay na background
        java.net.URL bgURL = getClass().getResource("/images/LevelOneBg.jpg");
        if (bgURL != null) {
            background = new ImageIcon(bgURL).getImage();
        }

        topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 160));
        add(topPanel);

        JLabel levelLabel = new JLabel("LEVEL 1 - RAVEN BOSS", SwingConstants.CENTER);
        levelLabel.setForeground(Color.YELLOW);
        levelLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        topPanel.add(levelLabel);

        //sa description ni
        battleText = new JTextArea();
        battleText.setEditable(false);
        battleText.setBackground(new Color(10, 10, 10, 220));
        battleText.setForeground(new Color(0, 255, 200));
        battleText.setFont(new Font("Consolas", Font.BOLD, 13));
        battleText.setLineWrap(true);
        battleText.setWrapStyleWord(true);
        battleText.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        battleText.setMargin(new Insets(5, 5, 5, 5));

        topPanel.add(battleText);

        // players characters
        playerLabel = new JLabel();
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL playerURL = getClass().getResource("/images/" + selectedCharacter + ".png");
        if (playerURL != null) {
            ImageIcon icon = new ImageIcon(playerURL);
            playerLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        }
        add(playerLabel);

        playerName = new JLabel(selectedCharacter, SwingConstants.CENTER);
        playerName.setForeground(Color.WHITE);
        playerName.setFont(new Font("Monospaced", Font.BOLD, 16));
        add(playerName);

        playerHP = new JProgressBar(0, playerChar.getMaxHP());
        playerHP.setValue(playerChar.getHP());
        playerHP.setForeground(Color.GREEN);
        playerHP.setBackground(Color.DARK_GRAY);
        add(playerHP);

        // boss raven
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
        enemyName.setFont(new Font("Monospaced", Font.BOLD, 16));
        add(enemyName);

        enemyHP = new JProgressBar(0, boss.getBossMaxHP());
        enemyHP.setValue(boss.getBossHP());
        enemyHP.setForeground(Color.RED);
        enemyHP.setBackground(Color.DARK_GRAY);
        add(enemyHP);

        // designi ninyu tarung button bi gikapooi najud ko ani ahahahahahaah
        bottomUI = new JPanel(null);
        bottomUI.setBackground(new Color(0, 0, 0, 180));
        add(bottomUI);

        attackBtn = createButton("ATTACK");
        skill1Btn = createButton("SKILL 1");
        skill2Btn = createButton("SKILL 2");
        skill3Btn = createButton("SKILL 3");

        bottomUI.add(attackBtn);
        bottomUI.add(skill1Btn);
        bottomUI.add(skill2Btn);
        bottomUI.add(skill3Btn);

        // kani sad ayaw nani hilabtiii
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
        addLog("⚔ BATTLE STARTED AGAINST RAVEN!");
    }

    // turn system
    private void playerTurn(int skill) {

        if (skill == 2 && playerChar.getSkill2CD() > 0) {
            addLog("⛔ Skill 2 cooldown!");
            return;
        }

        if (skill == 3 && playerChar.getSkill3CD() > 0) {
            addLog("⛔ Skill 3 cooldown!");
            return;
        }

        float dmgToBoss = playerChar.attack(skill);

        String skillName = "ATTACK";
        if (skill == 1) skillName = "Basic Attack";
        if (skill == 2) skillName = "Skill 1";
        if (skill == 3) skillName = "Skill 2";

        addLog("────────────────────");
        addLog("⚔ PLAYER TURN");
        addLog("🟢 " + skillName);
        addLog("💥 Damage: " + (int)dmgToBoss);

        boss.setBossHP(boss.getBossHP() - (int)dmgToBoss);

        playerChar.Cooldown();

        updateBars();
        updateButtons();

        // store after boss defeated
        if (boss.getBossHP() <= 0) {
            addLog("RAVEN DEFEATED!");
            disableButtons();

            // Short delay so player can see the victory message before switching
            Timer delay = new Timer(1500, e -> goToStore());
            delay.setRepeats(false);
            delay.start();
            return;
        }

        float dmgToPlayer = boss.attack();

        addLog("RAVEN TURN");
        addLog("Damage: " + (int)dmgToPlayer);

        playerChar.takeDamage(dmgToPlayer);

        boss.bossUpdate();

        updateBars();

        if (playerChar.getHP() <= 0) {
            addLog("YOU DIED!");
            disableButtons();
        }
    }

    // go to store after winning
    protected void goToStore() {
        Image introBg = new ImageIcon(
                getClass().getResource("/images/backgroundIntro.jpg")
        ).getImage();

        // ✅ NEXT LEVEL = 2
        frame.setContentPane(new Store(frame, introBg, selectedCharacter, 2));
        frame.revalidate();
        frame.repaint();
    }

    private void addLog(String text) {
        battleText.append(text + "\n");
        battleText.setCaretPosition(battleText.getDocument().getLength());
    }

    private void updateButtons() {
        skill1Btn.setEnabled(true);
        skill2Btn.setEnabled(playerChar.getSkill2CD() == 0);
        skill3Btn.setEnabled(playerChar.getSkill3CD() == 0);
    }

    private void disableButtons() {
        attackBtn.setEnabled(false);
        skill1Btn.setEnabled(false);
        skill2Btn.setEnabled(false);
        skill3Btn.setEnabled(false);
    }

    //hp
    private void updateBars() {
        playerHP.setMaximum(playerChar.getMaxHP());
        playerHP.setValue(Math.max(playerChar.getHP(), 0));

        enemyHP.setMaximum(boss.getBossMaxHP());
        enemyHP.setValue(Math.max(boss.getBossHP(), 0));
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(20, 20, 20));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
        return btn;
    }

    private void updateLayout() {

        int w = getWidth();
        int h = getHeight();

        topPanel.setBounds(0, 0, w, 130);
        topPanel.getComponent(0).setBounds(w / 2 - 200, 5, 400, 25);
        battleText.setBounds(20, 35, w - 40, 85);

        playerLabel.setBounds(100, h / 3, 180, 180);
        playerHP.setBounds(100, h / 3 - 20, 180, 15);
        playerName.setBounds(100, h / 3 + 185, 180, 20);

        enemy.setBounds(w - 300, h / 3, 200, 200);
        enemyHP.setBounds(w - 300, h / 3 - 20, 200, 15);
        enemyName.setBounds(w - 300, h / 3 + 185, 200, 20);
        enemyName.setHorizontalAlignment(SwingConstants.CENTER);

        bottomUI.setBounds(0, h - 120, w, 120);

        int btnW = w / 5;
        int startX = w / 2 - (btnW * 2);

        attackBtn.setBounds(startX, 30, btnW - 10, 40);
        skill1Btn.setBounds(startX + btnW, 30, btnW - 10, 40);
        skill2Btn.setBounds(startX + btnW * 2, 30, btnW - 10, 40);
        skill3Btn.setBounds(startX + btnW * 3, 30, btnW - 10, 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
