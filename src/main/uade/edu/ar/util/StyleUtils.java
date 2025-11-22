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
    public static final Color PRIMARY_LIGHT = new Color(147, 197, 253);    // #93C5FD
    public static final Color SECONDARY_GRAY = new Color(107, 114, 128);  // #6B7280
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);     // #22C55E
    public static final Color WARNING_ORANGE = new Color(249, 115, 22);  // #F97316
    public static final Color DANGER_RED = new Color(239, 68, 68);        // #EF4444
    public static final Color LIGHT_GRAY = new Color(243, 244, 246);      // #F3F4F6
    public static final Color VERY_LIGHT_GRAY = new Color(249, 250, 251); // #F9FAFB
    public static final Color WHITE = Color.WHITE;
    public static final Color DARK_TEXT = new Color(31, 41, 55);         // #1F2937
    public static final Color MEDIUM_GRAY = new Color(156, 163, 175);     // #9CA3AF
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 30);      // Sombra sutil
    
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
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                Color buttonColor;
                int shadowOffset = 0;
                
                if (getModel().isPressed()) {
                    buttonColor = backgroundColor.darker();
                    shadowOffset = 1;
                } else if (getModel().isRollover()) {
                    buttonColor = backgroundColor.brighter();
                    shadowOffset = 3;
                } else {
                    buttonColor = backgroundColor;
                    shadowOffset = 2;
                }
                
                // Dibujar sombra
                g2.setColor(new Color(0, 0, 0, 25));
                g2.fillRoundRect(2, shadowOffset, getWidth(), getHeight(), 10, 10);
                
                // Dibujar botón
                g2.setColor(buttonColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        button.setMargin(new Insets(8, 16, 8, 16));
        
        return button;
    }
    
    // Método para crear campos de texto modernos
    public static JTextField createModernTextField(int columns) {
        JTextField field = new JTextField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo blanco
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Borde
                if (hasFocus()) {
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(PRIMARY_BLUE);
                } else {
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(MEDIUM_GRAY);
                }
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        field.setFont(TEXT_FONT);
        field.setForeground(DARK_TEXT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setOpaque(false);
        field.setPreferredSize(new Dimension(0, 42));
        
        return field;
    }
    
    // Método para crear campos de contraseña modernos
    public static JPasswordField createModernPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo blanco
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Borde
                if (hasFocus()) {
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(PRIMARY_BLUE);
                } else {
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(MEDIUM_GRAY);
                }
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        field.setFont(TEXT_FONT);
        field.setForeground(DARK_TEXT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setOpaque(false);
        field.setPreferredSize(new Dimension(0, 42));
        
        return field;
    }
    
    // Método para crear botones de navegación
    public static JButton createNavButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                Color buttonColor;
                int shadowOffset = 0;
                
                if (getModel().isPressed()) {
                    buttonColor = PRIMARY_DARK;
                    shadowOffset = 1;
                } else if (getModel().isRollover()) {
                    buttonColor = PRIMARY_BLUE.brighter();
                    shadowOffset = 3;
                } else {
                    buttonColor = PRIMARY_BLUE;
                    shadowOffset = 2;
                }
                
                // Dibujar sombra
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(2, shadowOffset, getWidth(), getHeight(), 8, 8);
                
                // Dibujar botón
                g2.setColor(buttonColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
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
        
        // Calcular el ancho necesario basándose en el texto
        FontMetrics fm = button.getFontMetrics(BUTTON_FONT);
        int textWidth = fm.stringWidth(text);
        int buttonWidth = Math.max(180, textWidth + 50); // Mínimo 180, o el ancho del texto + padding
        
        button.setPreferredSize(new Dimension(buttonWidth, 45));
        button.setMargin(new Insets(8, 20, 8, 20));
        
        return button;
    }
    
    // Método para crear títulos modernos
    public static JLabel createTitle(String text) {
        JLabel title = new JLabel("<html>" + text + "</html>");
        title.setFont(TITLE_FONT);
        title.setForeground(DARK_TEXT);
        title.setBorder(new EmptyBorder(0, 0, 5, 0));
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setVerticalAlignment(JLabel.TOP);
        // Permitir que el texto se ajuste automáticamente
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return title;
    }
    
    // Método para crear subtítulos
    public static JLabel createSubtitle(String text) {
        JLabel subtitle = new JLabel("<html>" + text + "</html>");
        subtitle.setFont(SUBTITLE_FONT);
        subtitle.setForeground(SECONDARY_GRAY);
        subtitle.setBorder(new EmptyBorder(0, 0, 0, 0));
        subtitle.setHorizontalAlignment(JLabel.LEFT);
        subtitle.setVerticalAlignment(JLabel.TOP);
        // Permitir que el texto se ajuste automáticamente
        subtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
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
