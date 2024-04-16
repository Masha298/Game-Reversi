import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
public class Draw extends JComponent{
    public static final int COLS = 8;//колонки
    public static final int IMAGE_SIZE = 90;//размер колонки
    public static final int FIELD_EMPTY=0;//в ячейке пусто
    public static final int FIELD_WHITE=10;//в ячейке белый
    public static final int FIELD_BLACK=20;//в ячейке черный
    public static final int FIELD_BLUE=1;//в ячейке голубой
    Draw() {
       enableEvents(AWTEvent.MOUSE_EVENT_MASK);//подключение реакции на мышь
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        DrawGrid(g);//отрисовка линий
        DrawCheck(g);//показываем счет
        drawMove(g);//показываем чей ход
        DrawChip(g);//рисуем фишки
    }
    @Override
    public void processMouseEvent(MouseEvent mouseEvent){//клик мыши
        super.processMouseEvent(mouseEvent);
        Game.Search();//поиск возможных ходов
        if(Game.isXturn==true && Reversi.pressbutton==1){
            Bot();
        }
        Game.Search();//поиск возможных ходов
        if(mouseEvent.getButton()==MouseEvent.BUTTON1){//нажатие левой клавиши
            if(Game.isXturn==false||Reversi.pressbutton==2){
                Player(mouseEvent);
            }}
        }
    public void Player(MouseEvent mouseEvent) {//
        int x = mouseEvent.getX();//координата х
        int y = mouseEvent.getY();//координата у
        //переводим координаты в индексы массива
        int i = (int)((float)x/IMAGE_SIZE);
        int j = (int)((float)y/IMAGE_SIZE);
        //проверка что в ячейке стоит голубая фишка (это означает что туда можно сходить)
        if (Game.field[i][j]==FIELD_BLUE){
            Game.State(i,j,0);//переворачиваем фишки
            Game.field[i][j] = Game.isXturn?FIELD_WHITE:FIELD_BLACK;//проверка чей ход
            Game.isXturn=!Game.isXturn;//меняем ход
            CheckWin();//проверка на победу
            repaint();//перерисовка вызовет метод paintComponent
    }}
    public void Bot() {//
        int i;
        int j;
        do {
            i = rnd(0, 7);
            j = rnd(0, 7);
        }
        while (Game.field[i][j]!=Draw.FIELD_BLUE);
        Game.State(i,j,0);
        Game.field[i][j]=Draw.FIELD_WHITE;
        CheckWin();//проверка на победу
        Game.isXturn=!Game.isXturn;//меняем ход
        repaint();//перерисовка вызовет метод paintComponent
        }
    private static int rnd(int min, int max) {
        max-=min;
        return(int)(Math.random()*++max)+min;
    }
    public static void Check() {//проверка на наличие ходов
        int m=0;//счетчик
        for (int i = 0; i < Draw.COLS; i++) {//проверка для каждой клетки
            for (int j = 0; j < Draw.COLS; j++) {
                if (Game.field[i][j] == Draw.FIELD_BLUE) {//если есть ходы
                    m++;
                }
            }
        }
        if (m == 0) {//если ходов нет
            Game.isXturn = !Game.isXturn;//меняем ход
        }
    }
    public void Win(){//победа
        int white = Game.CheckWhite();
        int black = Game.CheckBlack();
        if (white>black){//если белых больше
            JOptionPane.showMessageDialog(this,"победа белых","победа",JOptionPane.INFORMATION_MESSAGE);
        }
        if (white<black){//если черных больше
            JOptionPane.showMessageDialog(this,"победа черных","победа",JOptionPane.INFORMATION_MESSAGE);
        }
        if (white==black){//если счет равный
            JOptionPane.showMessageDialog(this,"ничья","ничья",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void CheckWin(){//проверка победы
        int white = Game.CheckWhite();
        int black = Game.CheckBlack();
        int p=0;
        for(int i=0;i<COLS;i++){//проверка для каждой клетки
            for(int j=0;j<COLS;j++) {
                if (Game.field[i][j]!=FIELD_BLUE&&Game.field[i][j]!=FIELD_EMPTY) {
                    p++;//проверка сколько клеток заполнено
                }}}
        if (p==64){Win();}//если все клетки заполнены обьявляем победу
        if(white==0||black==0){Win();}//если не осталось фишек одного цвета обьявляем победу

    }
    void drawMove(Graphics g) {//показываем чей ход
        int k = Game.isXturn ? FIELD_WHITE : FIELD_BLACK;
        if (k == FIELD_WHITE) {//если ход белого
            g.drawImage(getImage("ход белого"), COLS * IMAGE_SIZE + 120, 70, this);
        }//добавляем соответствующую картинку
        if (k == FIELD_BLACK) {//если ход черного
            g.drawImage(getImage("ход черного"), COLS * IMAGE_SIZE + 120, 70, this);
        }//добавляем соответствующую картинку
}
    private void DrawCheck(Graphics g){//показываем счет
        Font font = new Font("TimesRoman", Font.PLAIN, 30);//добавляем шрифт
        setFont(font);
        int white = Game.CheckWhite();//подсчитываем кол-во фишек белого цвета
        int black = Game.CheckBlack();//подсчитываем кол-во фишек черного цвета
        g.drawString("белый: "+white, IMAGE_SIZE*COLS+100, 30);
        g.drawString("черный: "+black, IMAGE_SIZE*COLS+250, 30);
    }
    private void DrawChip(Graphics g){//отрисовка фишек
        for(int i=0;i<COLS;i++){//проверяем каждую клетку матрицы
            for(int j=0;j<COLS;j++) {
                if(Game.field[i][j] == FIELD_WHITE){
                    DrawWhite(i,j,g);//рисуем белую фишку
                }
                else if(Game.field[i][j] == FIELD_BLACK){
                    DrawBlack(i,j,g);//рисуем черную фишку
                }
                else if(Game.field[i][j] == FIELD_BLUE){
                    DrawBlue(i,j,g);//рисуем голубую фишку
                }}}
    }
    private void DrawGrid(Graphics g){//отрисовка линий
        for(int i=0;i<=COLS;i++){
            g.drawLine(0,IMAGE_SIZE*i, COLS*IMAGE_SIZE,IMAGE_SIZE*i);//горизонтальная линия
            g.drawLine(IMAGE_SIZE*i,0, IMAGE_SIZE*i,COLS*IMAGE_SIZE);//вертикальная линия
        }
    }
    private Image getImage(String name) {//добавление картинок
        String filename = name.toLowerCase() + ".png";//добавляем формат
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
    private void DrawWhite( int x, int y, Graphics g){//нарисовать белую фишку
        g.drawImage(getImage("б"),x*IMAGE_SIZE,y*IMAGE_SIZE,this);//добавление картинки
    }
    private void DrawBlue( int x, int y, Graphics g){//нарисовать голубую фишку
        g.drawImage(getImage("г"),x*IMAGE_SIZE,y*IMAGE_SIZE,this);//добавление картинки
    }
    private void DrawBlack(int x, int y, Graphics g){//нарисовать черную фишку
        g.drawImage(getImage("ч"),x*IMAGE_SIZE,y*IMAGE_SIZE,this);//добавление картинки
    }
}










