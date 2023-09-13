package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout board;


    private ArrayList<Button> squares = new ArrayList<>();

    @Override
    // создает поле-экран
    protected void onCreate(Bundle savedInstanceState) {
        //ссылкается на родительский метод
        super.onCreate(savedInstanceState);
        //создает экран
        setContentView(R.layout.activity_game_screen);
        //присваевается к каждой кнопке
        View.OnClickListener listener = (view) -> {
            Button btn = (Button) view;
        //проверят не пуста ли кнопка
            //если не пуста то выходит
            if (!btn.getText().toString().equals("")) return;
            //если пуста то смотрит какой ход и устанавливает соотвествующий символ
            if (GameInfo.isTurn) {
                btn.setText(GameInfo.firstSymbol);

            } else {
                btn.setText(GameInfo.secondSymbol);

            }
            //получение победных позиций
            int[] winPositions = calcWinnPositions();
            if (winPositions != null) {
                Toast.makeText(
                        getApplicationContext(),
                        "winner is " + squares.get(winPositions[0]).getText().toString(),
                        Toast.LENGTH_LONG).show();
                for (int i : winPositions) {
                    squares.get(i).setBackgroundTintList(ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.yellow));
                }
            }

            GameInfo.isTurn = !GameInfo.isTurn;

        };
        board = findViewById(R.id.board); //поиск поля которое находится в activity
        generateBoard(3, 3, board); //генерация поля
        setListenerToSquares(listener); //установка прослушки

        initClearBordBtn();//установка метода очистки
    }
    //метод очищающий кнопки
    private void initClearBordBtn() {
        Button clearBtn = findViewById(R.id.clear_board_value); //ищет кнопку обновить
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override //мтеод который происходит при ножатии на кнопку
            public void onClick(View v) {
                // всплывающее окошко НОВАЯ ИГРА
                Toast.makeText(
                        getApplicationContext(),
                        "Новая игра",
                        Toast.LENGTH_LONG).show();
                for (Button square : squares) { //устанавливает все кнопки серым
                    square.setText("");
                    square.setBackgroundTintList(
                            ContextCompat.getColorStateList(
                                    getApplicationContext(),
                                    R.color.gray_light));
                }
            }
        });
    }
    //устанавливает метод установки значений
    private void setListenerToSquares(View.OnClickListener listener) { //метод который присваетвается к кнопкам
        for (int i = 0; i < squares.size(); i++)
            squares.get(i).setOnClickListener(listener);
    }
    //генерация поля
    public void generateBoard(int rowCount, int columnCount, LinearLayout board) {
        for (int row = 0; row < rowCount; row++) { //цикл генерации строк
            LinearLayout rowContainer = generateRow(columnCount); //пристваивает generateRow

            board.addView(rowContainer); //добавление строки на поле
        }
    }
    //создание строк с кнопками
    private LinearLayout generateRow(int squaresCount) {
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL); //орентация
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, //ширина
                        ViewGroup.LayoutParams.WRAP_CONTENT) //высота
        );
        for (int square = 0; square < squaresCount; square++) { //создание кнопок
            Button button = new Button(getApplicationContext()); //обьявление кнопки
            button.setBackgroundTintList( //установка цвета
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.gray_light));
            button.setWidth(convertToPixel(50)); //ширина
            button.setHeight(convertToPixel(90)); //высота
            rowContainer.addView(button); //добавалние кнопок на строку
            squares.add(button); //добавление кнопки на поле
        }
        return rowContainer; //возвращение строки
    }

    public int convertToPixel(int digit) { //конвертация пикселя
        float density = getApplicationContext()
                .getResources().getDisplayMetrics().density;
        return (int) (digit * density + 0.5);
    }

    public int[] calcWinnPositions() { //выщитывание повбедной позици
        for (int i = 0; i < GameInfo.winCombination.length; i++) { //цикл прохода по всем комбинациям
            boolean findComb = true; //найденная комбинация
            String symbol = squares.get(GameInfo.winCombination[i][0]).getText().toString(); //получение символа из индекса комбинации на поле
            if (!(symbol.equals("X") || symbol.equals("O"))) //проверка кай символ получили
                continue;
            for (int j = 0; j < GameInfo.winCombination[0].length; j++) { //проход по полученной кобинации
                int index = GameInfo.winCombination[i][j]; //получение индекса комбинации
                if (!squares.get(index).getText().toString().equals(symbol)) { //проверка на символи является ли символ x или o
                    findComb = false;
                    break; //если символы не совпадают, то мы получаем не ту комбинацию и переходим к проверке другой комбинации
                }
            }
            if (findComb) return GameInfo.winCombination[i]; //возвращает выйгрышную комбмнацию
        }
        return null; //возврат пустой комбинации
    }
}