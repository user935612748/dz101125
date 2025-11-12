package com.example.dz101125;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private TextView display;
    private String currentInput = "0";
    private boolean resetOnNextInput = false;
    
    private static final String KEY_CURRENT_INPUT = "currentInput";
    private static final String KEY_RESET_ON_NEXT_INPUT = "resetOnNextInput";
    private static final int MAX_INPUT_LENGTH = 15;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState != null) {
            currentInput = savedInstanceState.getString(KEY_CURRENT_INPUT, "0");
            resetOnNextInput = savedInstanceState.getBoolean(KEY_RESET_ON_NEXT_INPUT, false);
        }
        
        display = findViewById(R.id.display);
        updateDisplay();
        
        setupNumberButtons();
        setupFunctionButtons();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_INPUT, currentInput);
        outState.putBoolean(KEY_RESET_ON_NEXT_INPUT, resetOnNextInput);
    }
    
    private void setupNumberButtons() {
        int[] numberButtonIds = {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9, R.id.button_decimal
        };
        
        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNumberButtonClick(((Button) v).getText().toString());
                }
            });
        }
    }
    
    private void setupFunctionButtons() {
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "0";
                resetOnNextInput = false;
                updateDisplay();
            }
        });
        
        findViewById(R.id.button_clear_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "0";
                updateDisplay();
            }
        });
        
        findViewById(R.id.button_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 1) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                } else {
                    currentInput = "0";
                }
                updateDisplay();
            }
        });
        
        findViewById(R.id.button_plus_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.equals("0")) {
                    if (currentInput.startsWith("-")) {
                        currentInput = currentInput.substring(1);
                    } else {
                        currentInput = "-" + currentInput;
                    }
                    updateDisplay();
                }
            }
        });
        
        setupOperatorButtons();
    }
    
    private void setupOperatorButtons() {
        int[] operatorButtonIds = {
            R.id.button_plus, R.id.button_minus, R.id.button_multiply,
            R.id.button_divide, R.id.button_equals, R.id.button_percent,
            R.id.button_sqrt, R.id.button_square, R.id.button_reciprocal
        };
        
        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetOnNextInput = true;
                }
            });
        }
    }
    
    private void onNumberButtonClick(String input) {
        if (currentInput.length() >= MAX_INPUT_LENGTH && !resetOnNextInput) {
            return;
        }

        if (resetOnNextInput || currentInput.equals("0")) {
            currentInput = input;
            resetOnNextInput = false;
        } else {
            if (input.equals(".") && currentInput.contains(".")) {
                return;
            }
            currentInput += input;
        }
        updateDisplay();
    }
    
    private void updateDisplay() {
        display.setText(currentInput);
    }
}