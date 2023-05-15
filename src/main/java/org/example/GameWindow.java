package org.example;

import javax.swing.*;

/**
 * Главное окно игры
 */
public class GameWindow extends JFrame {
    /**
     * Конструктор со всем особенностями поля
     */
    public GameWindow() {
        setTitle("Змейка");
        // Крестик в верхнем углу экрана, при нажатии на который прекращается программа
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Размеры поля
        setSize(400, 400);
        // Место появления окна на экране
//        setLocation(400, 400);
        // Действия игры на поле
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
    }
}
