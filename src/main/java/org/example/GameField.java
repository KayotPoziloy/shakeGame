package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Основной класс игры, в которой происходят все действия игры
 */
public class GameField extends JPanel implements ActionListener {
    /**
     * Размер поля
     */
    private final int SIZE = 320;
    /**
     * Размер ячейки змейки и поедаемой ячейки
     */
    private final int DOT_SIZE = 16;
    /**
     * Количество игровых ячеек на поле
     * (размер поля на размер одной ячейки)
     */
    private final int ALL_DOTS = 400;
    /**
     * Картинка одной клетки
     */
    private Image dot;
    /**
     * Картинка яблока
     */
    private Image apple;
    /**
     * Картинка клетки поля
     */
    private Image field;
    /**
     * Координата яблока по X и Y
     */
    private int appleX;
    private int appleY;
    /**
     * Массивы расположения змейки
     */
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    /**
     * Размер змейки в ячейках
     */
    private int dots;
    /**
     * Таймер
     */
    private Timer timer;
    /**
     * Направление движения змейки
     */
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    /**
     * Параметр, задающий, змейка в игре или нет
     */
    private boolean inGame = true;

    /**
     * Конструктор класса
     */
    public GameField() {
        setBackground(Color.gray);
        loadImages();
        initGame();
        addKeyListener(new KeyListener());
        setFocusable(true);
    }

    /**
     * Метод для загрузки картинок
     */
    public void loadImages() {
        ImageIcon imageIconApple = new ImageIcon("C:\\Users\\yakho\\OneDrive\\Рабочий стол\\shakeGame\\src\\main\\java\\org\\example\\apple.png");
        apple = imageIconApple.getImage();
        ImageIcon imageIconDot = new ImageIcon("C:\\Users\\yakho\\OneDrive\\Рабочий стол\\shakeGame\\src\\main\\java\\org\\example\\snake.png");
        dot = imageIconDot.getImage();
        ImageIcon imageIconField = new ImageIcon("C:\\Users\\yakho\\OneDrive\\Рабочий стол\\shakeGame\\src\\main\\java\\org\\example\\field.png");
        field = imageIconField.getImage();
    }

    /**
     * Метод для инициализации начала игры
     */
    public void initGame() {
        dots = 3; // Начальное количество точек
        // Начальное значение координат
        for (int i = 0; i < dots; i++) {
            x[i] = DOT_SIZE*3 - i*DOT_SIZE;
            y[i] = DOT_SIZE;
        }
        timer = new Timer(100, this);
        timer.start();
        createApple();
    }

    /**
     * Метод, создающий яблоко в рандомной ячейке
     */
    public void createApple() {
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    /**
     * Отрисовка компонентов поля
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < SIZE + DOT_SIZE; i += DOT_SIZE) {
            for (int j = 0; j < SIZE + DOT_SIZE; j += DOT_SIZE) {
                g.drawImage(field, i, j, this);
            }
        }
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            Font font = new Font("Arial", Font.BOLD, 32);
            g.setFont(font);
            String gameOver = "Конец";
            String hahaha = "ХАХАХАХ";
            g.setColor(Color.BLACK);
            g.drawString(gameOver, SIZE/2 - 50, SIZE/2);
            g.drawString(hahaha, SIZE/2 - 70, SIZE/2 + 50);
        }
        g.drawLine(SIZE + DOT_SIZE, 0, SIZE + DOT_SIZE,  SIZE + DOT_SIZE);
        g.drawLine(0, SIZE + DOT_SIZE, SIZE + DOT_SIZE, SIZE + DOT_SIZE);
    }

    /**
     * Метод, двигающий змейку
     */
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    /**
     * Проверяет, не съедено ли яблоко
     */
    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    /**
     * Проверяет, нет ли столкновения
     */
    public void checkCollisions() {
        // Столкновение с собой
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        // Выход за границы
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    /**
     * Метод, вызывающийся каждые 250 млс в таймере
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    /**
     * Считывание с клавиатуры
     */
    class KeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down ) {
                up = true;
                left = false;
                right = false;
            }

            if (key == KeyEvent.VK_DOWN && !up ) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}