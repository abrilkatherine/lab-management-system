package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import main.uade.edu.ar.util.StyleUtils;

public class ButtonRenderer extends DefaultTableCellRenderer {
    private String actionText;
    private Color actionColor;
    
    public ButtonRenderer(String actionText, Color actionColor) {
        this.actionText = actionText;
        this.actionColor = actionColor;
        setHorizontalAlignment(JLabel.CENTER);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        JLabel label = new JLabel(actionText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo sutil solo en hover
                if (getMousePosition() != null) {
                    g2.setColor(actionColor.brighter().brighter());
                    g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 4, 4);
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        label.setForeground(actionColor);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Fuente más grande
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        
        // Estilo más sutil
        if (isSelected) {
            label.setBackground(StyleUtils.LIGHT_GRAY);
            label.setOpaque(true);
        } else {
            label.setBackground(StyleUtils.WHITE);
            label.setOpaque(false);
        }
        
        return label;
    }
}
