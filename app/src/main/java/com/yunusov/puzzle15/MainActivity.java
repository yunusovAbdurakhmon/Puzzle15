package com.yunusov.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons;
    private ArrayList<Integer> numbers;
    private Coordinate emptyButton;
    private int score;
    private TextView scoreText;
    private Chronometer textTimer;
    private ViewGroup layoutWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadButtons();
        loadNumbers();
        loadDataToView();
    }

    private void loadButtons() {
        scoreText = findViewById(R.id.textScore);
        textTimer = findViewById(R.id.textTimer);
        layoutWin = findViewById(R.id.layoutWin);
        ViewGroup group = findViewById(R.id.group);
        int count = group.getChildCount();
        buttons = new Button[4][4];
        for (int i = 0; i < count; i++) {
            Button t = (Button) group.getChildAt(i);
            buttons[i / 4][i % 4] = t;
            t.setOnClickListener(this::click);
            t.setTag(new Coordinate(i / 4, i % 4));
        }
        emptyButton = new Coordinate(3, 3);
    }

    private void click(View view) {
        Button button = (Button) view;
        Coordinate c = (Coordinate) button.getTag();
        int dx = Math.abs(emptyButton.getX() - c.getX());
        int dy = Math.abs(emptyButton.getY() - c.getY());
        if (dx == 0 && dy == 1 || dx == 1 && dy == 0) {
            score++;
            scoreText.setText(score + "");
            button.setBackgroundResource(R.color.colorEmpty);
            Button empty = buttons[emptyButton.getX()][emptyButton.getY()];
            empty.setBackgroundResource(R.drawable.bg_button);
            empty.setText(button.getText());
            button.setText("");
            emptyButton.setY(c.getY());
            emptyButton.setX(c.getX());
            if (isWin()) {
                showWinner();
            }
        }
    }

    private void loadNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }
    }

    private void loadDataToView() {
        Collections.shuffle(numbers);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i * 4 + j < 15) buttons[i][j].setText(numbers.get(i * 4 + j) + "");
            }
        }
        buttons[emptyButton.getX()][emptyButton.getY()].setBackgroundResource(R.drawable.bg_button);
        buttons[3][3].setText("");
        buttons[3][3].setBackgroundResource(R.color.colorEmpty);
        emptyButton.setX(3);
        emptyButton.setY(3);
        score = 0;
        scoreText.setText(score + "");
        textTimer.setBase(SystemClock.elapsedRealtime());
        textTimer.start();
    }


    private boolean isWin() {
        if (emptyButton.getY() != 3 && emptyButton.getX() != 3) return false;
        boolean t = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (4 * i + j < 15) {
                    t &= buttons[i][j].getText().equals(4 * i + j + 1 + "");
                }
            }
        }
        return t;
    }

    public void showWinner() {
        TextView winText = findViewById(R.id.winText);
        winText.setTextSize(30);
        winText.setText("Congratulations! \n \nYou win in " + textTimer.getText() + " min \n and " + scoreText.getText() + " steps.");
        layoutWin.setVisibility(View.VISIBLE);
        textTimer.stop();
    }

    public void backToHome(View view) {
        // closing this activity
        finish();
    }

    public void refresh(View view){
        loadDataToView();
    }

    public void refreshToWinner(View view){
        layoutWin.setVisibility(View.INVISIBLE);
        loadDataToView();
    }


}
