package game.gui;

import game.characters.*;
import game.bosses.*;
import game.characters.Character;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class LevelTwoFightingScene extends JPanel {

    private JFrame frame;
    private String selectedCharacter;

    private Character playerChar;
    private Boss boss;
    private Image background;

  
    private JPanel topPanel;
    private JPanel arenaPanel;
    private JPanel bottomUI;


    private JLabel playerLabel;
    private JLabel playerName;
    private JProgressBar playerHP;
    private JProgressBar playerMP;
    private JLabel playerHpText;  
    private JLabel playerMpText;  

    private JLabel enemy;
    private JLabel enemyName;
    private JProgressBar enemyHP;
    private JProgressBar enemyMP;
    private JLabel enemyHpText;
    private JLabel enemyMpText;

    private JTextPane battleLog;
    private StyledDocument logDoc;


    private JButton attackBtn, skill1Btn, skill2Btn, skill3Btn;

    private JLabel skill2InfoLabel, skill3InfoLabel;

    private static final Color BG_DARK      = new Color(8,  11, 18);
    private static final Color PANEL_BG     = new Color(12, 16, 24, 220);
    private static final Color BORDER_COLOR = new Color(26, 42, 60);
    private static final Color HP_GREEN     = new Color(60, 200, 100);
    private static final Color HP_YELLOW    = new Color(220, 170, 30);
    private static final Color HP_RED       = new Color(220, 60,  60);
    private static final Color MP_BLUE      = new Color(40, 120, 220);
    private static final Color BOSS_HP      = new Color(200, 50,  50);
    private static final Color BOSS_MP      = new Color(160, 40, 100);
    private static final Color TEXT_CYAN    = new Color(80, 210, 200);
    private static final Color TEXT_WHITE   = new Color(200, 215, 230);
    private static final Color TEXT_YELLOW  = new Color(230, 200, 60);
    private static final Color TEXT_DIM     = new Color(60,  90, 110);


    private static final Color LOG_SYSTEM   = new Color(60,  100, 120);
    private static final Color LOG_DIVIDER  = new Color(30,  50,  65);
    private static final Color LOG_PLAYER   = new Color(80,  220, 140);
    private static final Color LOG_ENEMY    = new Color(220, 80,  80);
    private static final Color LOG_DAMAGE   = new Color(230, 195, 55);
    private static final Color LOG_HEAL     = new Color(80,  200, 120);
    private static final Color LOG_MANA     = new Color(80,  150, 230);
    private static final Color LOG_VICTORY  = new Color(255, 215, 0);
    private static final Color LOG_DEFEAT   = new Color(200, 60,  60);

    public LevelTwoFightingScene(JFrame frame, String selectedCharacter) {
        this.frame             = frame;
        this.selectedCharacter = selectedCharacter;

        setLayout(null);
        setBackground(BG_DARK);

       
        if (selectedCharacter.equalsIgnoreCase("Noah")) {
            playerChar = new Noah();
        } else if (selectedCharacter.equalsIgnoreCase("Ashe")) {
            playerChar = new Ashe();
        } else {
            playerChar = new Quinn();
        }

        boss = new Raven(1);

        URL bgURL = getClass().getResource("/images/LevelTwoBg.png");
        if (bgURL != null) background = new ImageIcon(bgURL).getImage();

        buildTopPanel();
        buildArena();
        buildBottomUI();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) { updateLayout(); }
        });

        updateLayout();
        updateBars();
        updateButtons();
        logDivider();
        logMsg("  BATTLE STARTED  —  " + selectedCharacter.toUpperCase() + " vs MARO", LOG_SYSTEM);
        logDivider();
    }

    private void buildTopPanel() {
        topPanel = new JPanel(null);
        topPanel.setBackground(new Color(8, 12, 20, 230));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        add(topPanel);

        JLabel levelLabel = new JLabel("LEVEL 2  ─  MARO BOSS", SwingConstants.CENTER);
        levelLabel.setForeground(TEXT_YELLOW);
        levelLabel.setFont(monoFont(14, Font.BOLD));
        levelLabel.setName("levelLabel");
        topPanel.add(levelLabel);

      
        battleLog = new JTextPane();
        battleLog.setEditable(false);
        battleLog.setBackground(new Color(6, 9, 14));
        battleLog.setForeground(TEXT_CYAN);
        battleLog.setFont(monoFont(11, Font.PLAIN));
        battleLog.setMargin(new Insets(6, 10, 6, 10));
        battleLog.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        logDoc = battleLog.getStyledDocument();

        JScrollPane logScroll = new JScrollPane(battleLog);
        logScroll.setBackground(new Color(6, 9, 14));
        logScroll.setBorder(BorderFactory.createEmptyBorder());
        logScroll.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
        logScroll.getVerticalScrollBar().setBackground(new Color(15, 20, 30));
        logScroll.setName("logScroll");
        topPanel.add(logScroll);
    }

    private void buildArena() {
        arenaPanel = new JPanel(null);
        arenaPanel.setOpaque(false);
        add(arenaPanel);


      
        playerLabel = buildSpriteLabel(selectedCharacter, 170, 170);
        arenaPanel.add(playerLabel);

        playerName = new JLabel(selectedCharacter.toUpperCase(), SwingConstants.CENTER);
        playerName.setForeground(TEXT_WHITE);
        playerName.setFont(new Font("Rajdhani", Font.BOLD, 15));
        arenaPanel.add(playerName);

        playerHP = buildBar(0, playerChar.getMaxHP(), HP_GREEN);
        arenaPanel.add(playerHP);

        playerChar.setMP(playerChar.getMaxMP()); // force 100% MP at battle start
        playerMP = buildBar(0, playerChar.getMaxMP(), MP_BLUE);
        arenaPanel.add(playerMP);

        playerHpText = buildStatLabel("");
        arenaPanel.add(playerHpText);

        playerMpText = buildStatLabel("");
        arenaPanel.add(playerMpText);


      
        enemy = buildSpriteLabel("Maro", 190, 190);
        arenaPanel.add(enemy);

        enemyName = new JLabel("MARO", SwingConstants.CENTER);
        enemyName.setForeground(new Color(220, 120, 120));
        enemyName.setFont(new Font("Rajdhani", Font.BOLD, 15));
        arenaPanel.add(enemyName);

        enemyHP = buildBar(0, boss.getBossMaxHP(), BOSS_HP);
        arenaPanel.add(enemyHP);

        enemyMP = buildBar(0, 100, BOSS_MP);
        arenaPanel.add(enemyMP);

        enemyHpText = buildStatLabel("");
        arenaPanel.add(enemyHpText);

        enemyMpText = buildStatLabel("");
        arenaPanel.add(enemyMpText);
    }

    private void buildBottomUI() {
        bottomUI = new JPanel(null);
        bottomUI.setBackground(new Color(6, 9, 15, 230));
        bottomUI.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
        add(bottomUI);

        attackBtn = createSkillButton("ATTACK",  "Basic Attack", "0 MP",        new Color(20, 60, 25));
        skill1Btn = createSkillButton("SKILL 1", getSkill1Name(), "-5 MP",       new Color(15, 30, 55));
        skill2Btn = createSkillButton("SKILL 2", getSkill2Name(), "-15 MP • CD 2",new Color(15, 25, 55));
        skill3Btn = createSkillButton("SKILL 3", getSkill3Name(), "-20 MP • CD 3",new Color(25, 15, 55));

        skill2InfoLabel = (JLabel) skill2Btn.getClientProperty("infoLabel");
        skill3InfoLabel = (JLabel) skill3Btn.getClientProperty("infoLabel");

        bottomUI.add(attackBtn);
        bottomUI.add(skill1Btn);
        bottomUI.add(skill2Btn);
        bottomUI.add(skill3Btn);

     
        attackBtn.addActionListener(e -> playerTurn(0));
        skill1Btn.addActionListener(e -> playerTurn(1));
        skill2Btn.addActionListener(e -> playerTurn(2));
        skill3Btn.addActionListener(e -> playerTurn(3));
    }

    // ═══════════ BATTLE LOGIC ══════════════  

    private void playerTurn(int skill) {
        if (skill == 2 && playerChar.getSkill2CD() > 0) {
            logMsg("  Skill 2 is on cooldown! (" + playerChar.getSkill2CD() + " turns left)", LOG_SYSTEM);
            return;
        }
        if (skill == 3 && playerChar.getSkill3CD() > 0) {
            logMsg("  Skill 3 is on cooldown! (" + playerChar.getSkill3CD() + " turns left)", LOG_SYSTEM);
            return;
        }

        // mana costs: basic=0, skill1=-5, skill2=-15, skill3=-20
        int manaCost = switch (skill) {
            case 1 -> 5;
            case 2 -> 15;
            case 3 -> 20;
            default -> 0;
        };

      
        logDivider();
        logMsg("  PLAYER TURN", LOG_PLAYER);

        String skillName = "";
        float dmgToBoss = 0;

        switch (skill) {
            case 0 -> {
                skillName = "Basic Attack";
                dmgToBoss = playerChar.attack(0);
            }
            case 1 -> {
                skillName = getSkill1Name();
                dmgToBoss = playerChar.attack(1);
            }
            case 2 -> {
                skillName = getSkill2Name();
                if(playerChar.getMP() >= 15){
                    dmgToBoss = playerChar.attack(2);
                } else {
                    logMsg("  Not enough MP for " + skillName + "! (Need 15)", LOG_SYSTEM);
                    return;
                }
            }
            case 3 -> {
                skillName = getSkill3Name();
                if(playerChar.getMP() >= 20){
                    dmgToBoss = playerChar.attack(3);
                } else {
                    logMsg("  Not enough MP for " + skillName + "! (Need 20)", LOG_SYSTEM);
                    return;
                }
            }

        }

        // deduct mana
        playerChar.setMP(playerChar.getMP() - manaCost);
        if (manaCost > 0) {
            logMsg("  Used: " + skillName + "  (-" + manaCost + " MP)", LOG_PLAYER);
        } else {
            logMsg("  Used: " + skillName + "  (0 MP)", LOG_PLAYER);
        }
        logMsg("  Dealt " + (int) dmgToBoss + " damage to RAVEN", LOG_DAMAGE);

        boss.setBossHP(boss.getBossHP() - (int) dmgToBoss);
        playerChar.Cooldown();

        updateBars();
        updateButtons();

        if (boss.getBossHP() <= 0) {
            logDivider();
            logMsg("  MARO DEFEATED! Heading to the store...", LOG_VICTORY);
            logDivider();
            disableButtons();
            Timer delay = new Timer(1600, e -> goToStore());
            delay.setRepeats(false);
            delay.start();
            return;
        }

        
        logDivider();
        logMsg("  MARO'S TURN", LOG_ENEMY);

        float dmgToPlayer = boss.attack();
        logMsg("  MARO attacks for " + (int) dmgToPlayer + " damage!", LOG_DAMAGE);

        playerChar.takeDamage(dmgToPlayer);

        int mpRegen = 20 + (int)(Math.random() * 8);
        int newMP = Math.min(playerChar.getMaxMP(), playerChar.getMP() + mpRegen);
        playerChar.setMP(newMP);
        logMsg("  MP regenerated: +" + mpRegen, LOG_MANA);

        updateBars();

        if (playerChar.getHP() <= 0) {
            logDivider();
            logMsg("  YOU HAVE BEEN DEFEATED.", LOG_DEFEAT);
            logDivider();
            disableButtons();
        }
    }

    
    //Start of logic sa game
    private void updateBars() {
        int pHP    = Math.max(playerChar.getHP(), 0);
        int pMaxHP = playerChar.getMaxHP();
        int pMP    = Math.max(playerChar.getMP(), 0);
        int pMaxMP = playerChar.getMaxMP();
        int bHP    = Math.max(boss.getBossHP(), 0);
        int bMaxHP = boss.getBossMaxHP();

      
        float hpRatio = (float) pHP / pMaxHP;
        Color hpColor = hpRatio > 0.5f ? HP_GREEN : hpRatio > 0.25f ? HP_YELLOW : HP_RED;

        
        playerHP.setMaximum(pMaxHP);
        playerHP.setValue(pHP);
        playerHP.setForeground(hpColor);

        playerMP.setMaximum(pMaxMP);
        playerMP.setValue(pMP);

        int mpPct = pMaxMP > 0 ? (int)((pMP / (float) pMaxMP) * 100) : 0;
        playerHpText.setText(String.format("HP  %d / %d", pHP, pMaxHP));
        playerMpText.setText(String.format("MP  %d / %d  (%d%%)", pMP, pMaxMP, mpPct));

      
        enemyHP.setMaximum(bMaxHP);
        enemyHP.setValue(bHP); 

        if (boss.getBossHP() > 0) {
            int bossMP = (int)(60 + Math.random() * 30);
            enemyMP.setValue(Math.max(0, Math.min(100, bossMP)));
            enemyMpText.setText(String.format("MP  %d / 100", bossMP));
        } else {
           
            enemyMP.setValue(0);
            enemyMpText.setText("DEFEATED");
            enemyMpText.setForeground(LOG_DEFEAT);  
        }

        enemyHpText.setText(String.format("HP  %d / %d", bHP, bMaxHP));

        repaint();
    }

    private void updateButtons() {
        boolean cd2 = playerChar.getSkill2CD() > 0;
        boolean cd3 = playerChar.getSkill3CD() > 0;

        skill2Btn.setEnabled(!cd2);
        skill3Btn.setEnabled(!cd3);

        if (skill2InfoLabel != null) {
            skill2InfoLabel.setText(cd2 ? "CD: " + playerChar.getSkill2CD() + " turns" : "-15 MP • CD 2");
            skill2InfoLabel.setForeground(cd2 ? HP_RED : new Color(80, 140, 180));
        }
        if (skill3InfoLabel != null) {
            skill3InfoLabel.setText(cd3 ? "CD: " + playerChar.getSkill3CD() + " turns" : "-20 MP • CD 3");
            skill3InfoLabel.setForeground(cd3 ? HP_RED : new Color(80, 140, 180));
        }
    }

    private void disableButtons() {
        attackBtn.setEnabled(false);
        skill1Btn.setEnabled(false);
        skill2Btn.setEnabled(false);
        skill3Btn.setEnabled(false);
    }

  

    private void logMsg(String text, Color color) {
        try {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, color);
            StyleConstants.setFontFamily(attrs, "Consolas");
            StyleConstants.setFontSize(attrs, 12);
            logDoc.insertString(logDoc.getLength(), text + "\n", attrs);
            battleLog.setCaretPosition(logDoc.getLength());
        } catch (BadLocationException ignored) {}
    }

    private void logDivider() {
        logMsg("  ─────────────────────────────────────", LOG_DIVIDER);
    }

   

    private JLabel buildSpriteLabel(String name, int w, int h) {
        JLabel lbl = new JLabel();
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        URL url = getClass().getResource("/images/" + name + ".png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            lbl.setIcon(new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        } else {
        
            lbl.setText("[" + name + "]");
            lbl.setForeground(TEXT_DIM);
        }
        return lbl;
    }

    private JProgressBar buildBar(int min, int max, Color fg) {
        JProgressBar bar = new JProgressBar(min, max);
        bar.setValue(max);
        bar.setForeground(fg);
        bar.setBackground(new Color(15, 22, 32));
        bar.setBorderPainted(false);
        bar.setStringPainted(false);
        return bar;
    }

    private JLabel buildStatLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setForeground(TEXT_DIM);
        lbl.setFont(monoFont(10, Font.PLAIN));
        return lbl;
    }

    private JButton createSkillButton(String title, String subtitle, String cost, Color bgTint) {
       
        JLabel infoLabel = new JLabel(cost, SwingConstants.CENTER);
        infoLabel.setForeground(new Color(80, 140, 180));
        infoLabel.setFont(monoFont(9, Font.PLAIN));

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
              
                Color bg = isEnabled()
                        ? (getModel().isPressed() ? bgTint.darker()
                           : getModel().isRollover() ? bgTint.brighter()
                             : bgTint)
                        : new Color(15, 15, 20);
                g.setColor(bg);
                g.fillRect(0, 0, getWidth(), getHeight());

                
                g.setFont(monoFont(11, Font.BOLD));
                g.setColor(isEnabled() ? TEXT_WHITE : new Color(60, 70, 80));
                FontMetrics fm = g.getFontMetrics();
                int titleY = getHeight() / 2 - 4;
                g.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, titleY);

                
                g.setFont(monoFont(9, Font.PLAIN));
                g.setColor(isEnabled() ? infoLabel.getForeground() : new Color(50, 60, 70));
                FontMetrics fm2 = g.getFontMetrics();
                String info = infoLabel.getText();
                g.drawString(info, (getWidth() - fm2.stringWidth(info)) / 2, titleY + 16);
            }
        };

        btn.putClientProperty("infoLabel", infoLabel);
        btn.setOpaque(true);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // repaint on hover so rollover color kicks in
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.repaint(); }
            public void mouseExited(MouseEvent e)  { btn.repaint(); }
        });

        return btn;
    }

    private Font monoFont(int size, int style) {
        return new Font("Consolas", style, size);
    }



    private String getSkill1Name() {
        if (playerChar instanceof Noah)  return "Thunderbolt";
        if (playerChar instanceof Ashe)  return "Frost Arrow";
        return "Shadow Strike";
    }

    private String getSkill2Name() {
        if (playerChar instanceof Noah)  return "Chain Lightning";
        if (playerChar instanceof Ashe)  return "Blizzard";
        return "Shadowstep";
    }

    private String getSkill3Name() {
        if (playerChar instanceof Noah)  return "Storm Surge";
        if (playerChar instanceof Ashe)  return "Glacial Nova";
        return "Death Mark";
    }

  

    protected void goToStore() {
        Image introBg = new ImageIcon(
                getClass().getResource("/images/backgroundIntro.jpg")
        ).getImage();
        frame.setContentPane(new Store(frame, introBg, selectedCharacter, 3));
        frame.revalidate();
        frame.repaint();
    }

    // ════════ LAYOUT ═══════════

    private void updateLayout() {
        int w = getWidth();
        int h = getHeight();

        
        int topH = 160;
        topPanel.setBounds(0, 0, w, topH);

        Component levelLabel = topPanel.getComponent(0);
        levelLabel.setBounds(w / 2 - 200, 6, 400, 22);

        Component logScroll = topPanel.getComponent(1);
        logScroll.setBounds(12, 32, w - 24, topH - 42);

        
        int arenaY = topH;
        int arenaH = h - topH - 110;  // 110 = bottom UI height
        arenaPanel.setBounds(0, arenaY, w, arenaH);

   
        int colW   = 190;
        int pX     = 60;
        int midY   = arenaH / 2 - 85;

        playerLabel.setBounds(pX, midY, 170, 170);
        playerName .setBounds(pX, midY + 175, 170, 18);
        playerHP   .setBounds(pX, midY - 30,  170, 10);
        playerMP   .setBounds(pX, midY - 16,  170, 10);
        playerHpText.setBounds(pX, midY - 46, 170, 14);
        playerMpText.setBounds(pX, midY - 58, 200, 14);

      
        int bX = w - 60 - 190;
        enemy     .setBounds(bX, midY - 10, 190, 190);
        enemyName .setBounds(bX, midY + 185, 190, 18);
        enemyHP   .setBounds(bX, midY - 30,  190, 10);
        enemyMP   .setBounds(bX, midY - 16,  190, 10);
        enemyHpText.setBounds(bX, midY - 46, 190, 14);
        enemyMpText.setBounds(bX, midY - 58, 190, 14);

        
        int botY  = h - 110;
        bottomUI.setBounds(0, botY, w, 110);

        int btnW   = (w - 80) / 4;
        int btnH   = 64;
        int btnTop = 22;
        int startX = 20;

        attackBtn.setBounds(startX,                 btnTop, btnW - 8, btnH);
        skill1Btn.setBounds(startX + btnW,           btnTop, btnW - 8, btnH);
        skill2Btn.setBounds(startX + btnW * 2,       btnTop, btnW - 8, btnH);
        skill3Btn.setBounds(startX + btnW * 3,       btnTop, btnW - 8, btnH);
    }

  

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        
        if (background != null) {
            g2.drawImage(background, 0, 0, w, h, this);
            
            g2.setColor(new Color(0, 0, 0, 140));
            g2.fillRect(0, 0, w, h);
        } else {
            g2.setColor(BG_DARK);
            g2.fillRect(0, 0, w, h);
        }

        
        int groundY = getHeight() - 110 - 20;
        g2.setColor(new Color(30, 55, 80, 80));
        g2.fillRoundRect(40, groundY, w - 80, 2, 2, 2);
    }
}
