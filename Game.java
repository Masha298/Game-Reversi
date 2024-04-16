public class Game {
    public static boolean isXturn;//определение чей ход
    public static int[][] field;//массив поля
    public static int enemy [][];//массив врагов


    public static void initGame(){//начало игры
        field = new int[Draw.COLS][Draw.COLS];//создание массива
        for(int i = 0; i< Draw.COLS; i++){//перебор всех ячеек матрицы
            for(int j = 0; j< Draw.COLS; j++){
                field[i][j] = Draw.FIELD_EMPTY;}}//очищение массива заполнение его нулями
        field[3][4] = Draw.FIELD_BLACK;//первоначальная расстановка шашек
        field[4][4] = Draw.FIELD_WHITE;
        field[3][3] = Draw.FIELD_WHITE;
        field[4][3] = Draw.FIELD_BLACK;
    }
    public static void Clear(){//очищение от голубых фишек
        for(int i=0;i<Draw.COLS;i++){//проверка для каждой клетки
            for(int j=0;j<Draw.COLS;j++) {
                if (field[i][j]==Draw.FIELD_BLUE) {//если фишка голубая
                    field[i][j]=Draw.FIELD_EMPTY;//заменяем пустой фишкой
                }}}}
    public static int CheckWhite(){//счет
        int white = 0;
        for(int i=0;i<Draw.COLS;i++){//проверка для каждой клетки
            for(int j=0;j<Draw.COLS;j++) {
                if (field[i][j]==Draw.FIELD_WHITE) {
                    white++;//подсчитываем кол-во белых фишек
                }}}
        return white;
    }
    public static int CheckBlack(){//счет
        int black = 0;
        for(int i=0;i<Draw.COLS;i++){//проверка для каждой клетки
            for(int j=0;j<Draw.COLS;j++) {
                if (field[i][j]==Draw.FIELD_BLACK){
                    black++;//подсчитываем кол-во черных фишек
                }}}
        return black;
    }
    public static void Search(){//поиск
        Clear();//удаляем все голубые фишки
        for(int i=0;i<Draw.COLS;i++){//проверка для каждой клетки
            for(int j=0;j<Draw.COLS;j++) {
                if (field[i][j]==Draw.FIELD_EMPTY){//если она пустая
                    //выполняем поиск ходов
                    State(i,j,1);
                    }
            }}
        Draw.Check();
        }
    public static void change(int [][] enemy,int sum){//замена вражеских фишек на своих
        int K = isXturn?Draw.FIELD_WHITE:Draw.FIELD_BLACK;//свои
        int T = !isXturn?Draw.FIELD_WHITE:Draw.FIELD_BLACK;//враг
        for(int i=0;i<sum;i++) {
            int q = enemy[i][0];//получаем все координаты врагов
            int w = enemy[i][1];
            if( field[q][w] == T){
            field[q][w] = K;//меняем врагов на своих
        }}
    }
    public static void changeblue(int x,int y){
                field[x][y] = Draw.FIELD_BLUE;//ставим голубые фишки
    }
    public static void State(int x, int y, int do_capture) {//поиск возможных ходов в каждом направлении
        int K = isXturn?Draw.FIELD_WHITE:Draw.FIELD_BLACK;//свои
        int T = !isXturn?Draw.FIELD_WHITE:Draw.FIELD_BLACK;//враг
        int P=0;
        // влево вниз
        boolean friend = false;
        enemy = new int[Draw.COLS][2];
        int sum=0;
        if (x!=0 && y!=Draw.COLS-1 && field[x-1][y+1]==T){
            for(int i=x-1,j=y+1;i>=0&&j<Draw.COLS;i--,j++){
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                    }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0) {
                change(enemy,sum);
            }
            if(do_capture==1){
                changeblue(x,y);}
        }
        // вправо вверх
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (x!=Draw.COLS-1 && y!=0 && field[x+1][y-1]==T){
            for(int i=x+1,j=y-1;i<Draw.COLS&&j>=0;i++,j--){
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }

        //влево вниз
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (x!=Draw.COLS-1 && y!=Draw.COLS-1 && field[x+1][y+1]==T){
            for(int i=x+1,j=y+1;i<Draw.COLS&&j<Draw.COLS;i++,j++){
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                   }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }
        // влево вверх
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (x!=0 && y!=0 && field[x-1][y-1]==T){
            for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }

        //Вверх
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (y!=0 && field[x][y-1]==T){
            for(int j=y-1;j>=0;j--){
                int i=x;
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                   }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }

        // Вниз
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (y!=Draw.COLS-1 &&field[x][y+1]==T){
            for(int j=y+1;j<Draw.COLS;j++){
                int i=x;
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                    }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }

        // Вправо
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (x!=Draw.COLS-1 && field[x+1][y]==T){
            for(int i=x+1;i<Draw.COLS;i++){
                int j=y;
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                    }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }

        // Влево
        friend = false;
        sum = 0;
        P=0;
        enemy = new int[Draw.COLS][2];
        if (x!=0 && field[x-1][y]==T){
            for(int i=x-1;i>=0;i--){
                int j=y;
                // Если встретилась пустая клетка - выходим сразу
                if ( field[i][j] == 0 ||field[i][j] == 1) {
                    P++;
                    break;}
                // Если встретилась клетка врага - собираем её координаты
                if ( field[i][j] == T&& P==0 ) {
                    enemy[sum][0] = i;//запоминаем его координаты
                    enemy[sum][1] = j;
                    sum++;
                    }
                // Если встретилась своя клетка - запоминаем это
                if ( field[i][j] == K && P==0) {
                    friend = true;
                    break;
                }
            }
        }
        // Если была найдена своя клетка и если собранные клетки врага - красим их в свой цвет
        if ( sum>0 && friend == true && P==0) {
            if(do_capture==0){
                change(enemy,sum);
                }
            if(do_capture==1){
                changeblue(x,y);}
        }
    }}