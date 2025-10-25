package main.uade.edu.ar.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StyleUtils {
    
    // Paleta de colores moderna
    public static final Color PRIMARY_BLUE = new Color(59, 130, 246);      // #3B82F6
    public static final Color PRIMARY_DARK = new Color(37, 99, 235);       // #2563EB
    public static final Color SECONDARY_GRAY = new Color(107, 114, 128);  // #6B7280
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);     // #22C55E
    public static final Color WARNING_ORANGE = new Color(249, 115, 22);  // #F97316
    public static final Color DANGER_RED = new Color(239, 68, 68);        // #EF4444
    public static final Color LIGHT_GRAY = new Color(243, 244, 246);      // #F3F4F6
    public static final Color WHITE = Color.WHITE;
    public static final Color DARK_TEXT = new Color(31, 41, 55);         // #1F2937
    
    // Fuentes
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Bordes redondeados
    public static final Border ROUNDED_BORDER = new LineBorder(PRIMARY_BLUE, 2, true);
    public static final Border LIGHT_BORDER = new LineBorder(LIGHT_GRAY, 1, true);
    public static final Border EMPTY_BORDER = new EmptyBorder(10, 15, 10, 15);
    
    // Método para crear botones modernos
    public static JButton createModernButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(backgroundColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(backgroundColor.brighter());
                } else {
                    g2.setColor(backgroundColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Fuente más pequeña
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 32)); // Ancho suficiente para textos descriptivos
        button.setMargin(new Insets(4, 8, 4, 8)); // Márgenes más pequeños
        
        return button;
    }
    
    // Método para crear botones de navegación
    public static JButton createNavButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(PRIMARY_DARK);
                } else if (getModel().isRollover()) {
                    g2.setColor(PRIMARY_BLUE.brighter());
                } else {
                    g2.setColor(PRIMARY_BLUE);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(WHITE);
        button.setFont(BUTTON_FONT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(190, 45));
        button.setMargin(new Insets(8, 25, 8, 25));
        
        return button;
    }
    
    // Método para crear títulos modernos
    public static JLabel createTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(TITLE_FONT);
        title.setForeground(DARK_TEXT);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        return title;
    }
    
    // Método para crear subtítulos
    public static JLabel createSubtitle(String text) {
        JLabel subtitle = new JLabel(text);
        subtitle.setFont(SUBTITLE_FONT);
        subtitle.setForeground(SECONDARY_GRAY);
        subtitle.setBorder(new EmptyBorder(10, 0, 15, 0));
        return subtitle;
    }
    
    // Método para crear paneles con estilo
    public static JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }
    
    // Método para crear tablas con estilo
    public static void styleTable(JTable table) {
        table.setFont(TEXT_FONT);
        table.setRowHeight(35);
        table.setSelectionBackground(PRIMARY_BLUE);
        table.setSelectionForeground(WHITE);
        table.setGridColor(LIGHT_GRAY);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        // Estilo del header
        table.getTableHeader().setFont(BUTTON_FONT);
        table.getTableHeader().setBackground(PRIMARY_BLUE);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setReorderingAllowed(false);
    }
    
    // Método para crear botones de acción
    public static JButton createActionButton(String text, String action) {
        Color bgColor;
        switch (action.toLowerCase()) {
            case "add":
            case "agregar":
                bgColor = SUCCESS_GREEN;
                break;
            case "edit":
            case "editar":
                bgColor = PRIMARY_BLUE;
                break;
            case "delete":
            case "eliminar":
                bgColor = DANGER_RED;
                break;
            case "warning":
            case "advertencia":
                bgColor = WARNING_ORANGE;
                break;
            default:
                bgColor = SECONDARY_GRAY;
        }
        
        return createModernButton(text, bgColor, WHITE);
    }
    
    // Método para aplicar Look & Feel moderno
    public static void setModernLookAndFeel() {
        try {
            // Personalizar colores del sistema
            UIManager.put("Button.background", PRIMARY_BLUE);
            UIManager.put("Button.foreground", WHITE);
            UIManager.put("Button.font", BUTTON_FONT);
            
            UIManager.put("Table.background", WHITE);
            UIManager.put("Table.foreground", DARK_TEXT);
            UIManager.put("Table.selectionBackground", PRIMARY_BLUE);
            UIManager.put("Table.selectionForeground", WHITE);
            
            UIManager.put("TableHeader.background", PRIMARY_BLUE);
            UIManager.put("TableHeader.foreground", WHITE);
            UIManager.put("TableHeader.font", BUTTON_FONT);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
