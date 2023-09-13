package com.example.tic_tac_toe;

public class GameInfo {
    //определяет какой ход
    public static boolean isTurn = true;
    public static String firstSymbol = "X"; //первый символ
    public static String secondSymbol = "O"; //второй символ
    public static int[][] winCombination = //комбинации выйгрышные
            {
                    {0,1,2}, {3,4,5}, {6,7,8}, //проверки
                    {0,3,6}, {1,4,7}, {2,5,8},
                    {0,4,8},{2,4,6}
            };
}
