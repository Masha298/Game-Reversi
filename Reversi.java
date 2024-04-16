import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
public class Reversi {
    static int pressbutton=2;
    public static void main(String[] args) {
        JFrame window = new JFrame("Reversi");//главное окно
        //window.setIconImage(getImage("реверси"));//значок игры
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);//кнопка закрывающая окно
        Font font = new Font("TimesRoman", Font.PLAIN, 30);//добавляем шрифт

        JButton button1 = new JButton("бот");//создание кнопки бот
        ActionListener actionListener1 = new TestActionListener1();
        button1.addActionListener(actionListener1);//добавление функции
        button1.setBounds(Draw.COLS* Draw.IMAGE_SIZE+120,250,225, 80);//размер и положение
        window.add(button1);//добавление кнопки на панель
        button1.setFont(font);//применение шрифта

        JButton button2 = new JButton("два игрока");//создание кнопки два игрока
        ActionListener actionListener2 = new TestActionListener2();
        button2.addActionListener(actionListener2);//добавление функции
        button2.setBounds(Draw.COLS* Draw.IMAGE_SIZE+120,370,225, 80);//размер и положение
        window.add(button2);//добавление кнопки на панель
        button2.setFont(font);//применение шрифта

        window.setSize(Draw.COLS* Draw.IMAGE_SIZE+500, Draw.COLS* Draw.IMAGE_SIZE+40);//размер окна
        window.setLocationRelativeTo(null);//окно по центру экрана
        window.setLayout(new BorderLayout());//менеджер компановки
        window.setResizable(false);//изменение размера
        window.setVisible(true);//включение видимости поля
        Game.initGame();//первоначальная расстановка значений в массиве
        Draw draw = new Draw();//создание игрового поля
        window.add(draw);//добавление поля в главное окно
    }

    public static class TestActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pressbutton = 2;
        }
    }
    public static class TestActionListener1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pressbutton = 1;
        }
    }
}
