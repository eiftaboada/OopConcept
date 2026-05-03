package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Store extends JPanel {

    private JFrame frame;
    private Image background;
    private int coins = 500;
    private int nextLevel;
    private String selectedCharacter;



    private static final String[][] ITEMS = {
            {"Item 1", "Description of item 1.", "50",  "item1.png"},
            {"Item 2", "Description of item 2.", "100", "item2.png"},
            {"Item 3", "Description of item 3.", "150", "item3.png"},
            {"Item 4", "Description of item 4.", "200", "item4.png"},
    };

    private boolean[] owned = new boolean[ITEMS.length];

    public Store(JFrame frame, Image bg, String selectedCharacter, int nextLevel) {
        this.frame = frame;
        this.background = bg;
        this.selectedCharacter = selectedCharacter;
        this.nextLevel = nextLevel;

        setLayout(null);
        showMainStore();

        JButton proceedBtn = createButton("PROCEED ▶");
        proceedBtn.setName("proceed");
        proceedBtn.setForeground(Color.GREEN);
        proceedBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));


        proceedBtn.addActionListener(e -> goToNextLevel());

        add(proceedBtn);
    }


    private void showMainStore() {

        removeAll();


        JButton backBtn = createButton("◀ BACK");
        backBtn.setName("back");
        backBtn.addActionListener(e -> {
            Image introBg = new ImageIcon(
                    getClass().getResource("/images/backgroundIntro.jpg")).getImage();
            frame.setContentPane(new CharacterSelection(frame, introBg));
            frame.revalidate();
            frame.repaint();
        });
        add(backBtn);


        JLabel title = new JLabel("S H O P", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setName("title");
        add(title);


        JLabel coinsLabel = new JLabel("COINS: " + coins, SwingConstants.RIGHT);
        coinsLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        coinsLabel.setForeground(new Color(255, 215, 0));
        coinsLabel.setName("coins");
        add(coinsLabel);


        JLabel shopImg = new JLabel();
        shopImg.setHorizontalAlignment(SwingConstants.CENTER);
        shopImg.setName("shopimg");

        java.net.URL shopURL = getClass().getResource("/images/shop.png");
        if (shopURL != null) {
            shopImg.setIcon(new ImageIcon(
                    new ImageIcon(shopURL).getImage().getScaledInstance(380, 200, Image.SCALE_SMOOTH)));
        } else {

            shopImg.setText("[ SHOP ]");
            shopImg.setForeground(Color.WHITE);
            shopImg.setFont(new Font("Monospaced", Font.BOLD, 28));
            shopImg.setOpaque(true);
            shopImg.setBackground(new Color(30, 30, 30));
            shopImg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }
        add(shopImg);


        for (int i = 0; i < ITEMS.length; i++) {
            add(createItemCard(i));
        }

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) { layoutMainStore(); }
        });

        revalidate();
        repaint();
        SwingUtilities.invokeLater(this::layoutMainStore);
    }

    private void layoutMainStore() {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;

        for (Component c : getComponents()) {
            if (c.getName() == null) continue;
            switch (c.getName()) {
                case "back"    -> c.setBounds(28, 40, 120, 38);
                case "title"   -> c.setBounds(0, 30, w, 55);
                case "coins"   -> c.setBounds(w - 210, 45, 190, 28);
                case "shopimg" -> c.setBounds((w - 380) / 2, 110, 380, 200);
                case "proceed" -> c.setBounds(w - 180, h - 50, 160, 40);
            }
        }


        int cardW  = 150, cardH = 80;
        int gap    = 30;
        int totalW = 4 * cardW + 3 * gap;
        int startX = (w - totalW) / 2;
        int startY = h - cardH - 60;

        int idx = 0;
        for (Component c : getComponents()) {
            if (c.getName() != null && c.getName().startsWith("item-")) {
                c.setBounds(startX + idx * (cardW + gap), startY, cardW, cardH);
                idx++;
            }
        }
    }


    private JPanel createItemCard(int index) {
        JPanel card = new JPanel(null);
        card.setName("item-" + index);
        card.setBackground(Color.BLACK);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JLabel nameLabel = new JLabel(ITEMS[index][0], SwingConstants.CENTER);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(0, 10, 150, 20);
        card.add(nameLabel);

        JLabel priceLabel = new JLabel(ITEMS[index][2] + " coins", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        priceLabel.setForeground(new Color(255, 215, 0));
        priceLabel.setBounds(0, 38, 150, 20);
        card.add(priceLabel);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
            public void mouseClicked(MouseEvent e) {
                showItemDetail(index);
            }
        });

        return card;
    }


    private void showItemDetail(int index) {
        JPanel detail = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null)
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        detail.setOpaque(false);


        JButton backBtn = createButton("◀ BACK");
        backBtn.setBounds(28, 28, 120, 38);
        backBtn.addActionListener(e -> {
            new Store(frame, background, selectedCharacter, 2);
            frame.revalidate();
            frame.repaint();
        });
        detail.add(backBtn);


        JButton buyBtn = createButton("[ BUY ]");
        buyBtn.setForeground(new Color(100, 255, 100));
        buyBtn.setBorder(BorderFactory.createLineBorder(new Color(100, 255, 100), 2));

        if (owned[index]) {
            buyBtn.setText("[ OWNED ]");
            buyBtn.setEnabled(false);
            buyBtn.setForeground(Color.GRAY);
            buyBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }

        buyBtn.addActionListener(e -> {
            int cost = Integer.parseInt(ITEMS[index][2]);
            if (coins < cost) {
                JOptionPane.showMessageDialog(detail, "Not enough coins.", "Shop", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(detail,
                    "Buy " + ITEMS[index][0] + " for " + cost + " coins?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                coins -= cost;
                owned[index] = true;
                buyBtn.setText("[ OWNED ]");
                buyBtn.setEnabled(false);
                buyBtn.setForeground(Color.GRAY);
                buyBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
        });
        detail.add(buyBtn);


        JLabel itemImg = new JLabel();
        itemImg.setHorizontalAlignment(SwingConstants.CENTER);
        itemImg.setVerticalAlignment(SwingConstants.CENTER);

        java.net.URL imgURL = getClass().getResource("/images/" + ITEMS[index][3]);
        if (imgURL != null) {
            itemImg.setIcon(new ImageIcon(
                    new ImageIcon(imgURL).getImage().getScaledInstance(280, 160, Image.SCALE_SMOOTH)));
        } else {
            itemImg.setText("[ " + ITEMS[index][0] + " ]");
            itemImg.setForeground(Color.WHITE);
            itemImg.setFont(new Font("Monospaced", Font.BOLD, 20));
            itemImg.setOpaque(true);
            itemImg.setBackground(new Color(30, 30, 30));
            itemImg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }
        detail.add(itemImg);


        JPanel descBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.dispose();
            }
        };
        descBox.setOpaque(false);


        JLabel itemName = new JLabel(ITEMS[index][0], SwingConstants.CENTER);
        itemName.setFont(new Font("Monospaced", Font.BOLD, 18));
        itemName.setForeground(Color.CYAN);
        itemName.setBorder(BorderFactory.createEmptyBorder(12, 0, 4, 0));


        JLabel costLabel = new JLabel("Cost: " + ITEMS[index][2] + " coins", SwingConstants.CENTER);
        costLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        costLabel.setForeground(new Color(255, 215, 0));
        costLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));


        JTextArea descArea = new JTextArea(ITEMS[index][1]);
        descArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        descArea.setForeground(new Color(200, 200, 200));
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));

        JPanel descTop = new JPanel(new BorderLayout());
        descTop.setOpaque(false);
        descTop.add(itemName,  BorderLayout.NORTH);
        descTop.add(costLabel, BorderLayout.SOUTH);

        descBox.add(descTop,   BorderLayout.NORTH);
        descBox.add(descArea,  BorderLayout.CENTER);

        detail.add(descBox);


        detail.addHierarchyListener(ev -> {
            if ((ev.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && detail.isShowing()) {
                layoutDetail(detail, buyBtn, itemImg, descBox);
            }
        });

        detail.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layoutDetail(detail, buyBtn, itemImg, descBox);
            }
        });

        frame.setContentPane(detail);
        frame.revalidate();
        frame.repaint();
        SwingUtilities.invokeLater(() -> layoutDetail(detail, buyBtn, itemImg, descBox));
    }

    private void layoutDetail(JPanel detail, JButton buyBtn, JLabel itemImg, JPanel descBox) {
        int w = detail.getWidth();
        int h = detail.getHeight();
        if (w <= 0 || h <= 0) return;


        buyBtn.setBounds(w - 160, 28, 140, 38);


        int imgW = 280, imgH = 160;
        itemImg.setBounds((w - imgW) / 2, 20, imgW, imgH);


        int boxX = 60;
        int boxY = imgH + 50;
        int boxW = w - 120;
        int boxH = h - boxY - 40;
        descBox.setBounds(boxX, boxY, boxW, Math.max(boxH, 100));
    }


    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void goToNextLevel() {
        // e implementan pa
    }
}
