package com.example.calcolatrice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Dichiarazione variabili ed elementi grafici

    // Pulsanti valore
    Button button0; Button button1; Button button2;
    Button button3; Button button4; Button button5;
    Button button6; Button button7; Button button8;
    Button button9;

    // Pulsanti operazioni
    Button buttonClear; Button buttonDivide; Button buttonMultiply;
    Button buttonSubtract; Button buttonAdd; Button buttonEquals;

    // Text view risultato
    TextView risultato;

    String primoOperando = "";
    String secondoOperando = "";
    String operazione = "";
    boolean primacifra = true;
    boolean operazioneSelezionata = false;
    boolean ugualePremuto = false;
    boolean primoOperandoInserito = false;
    boolean operazioneInCorso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Collega oggetto java con risorsa grafica
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        buttonClear = findViewById(R.id.buttonClear);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonEquals = findViewById(R.id.buttonEquals);

        risultato = findViewById(R.id.risultato);

        setNumberButtonListeners();
        setOperationButtonListeners();
    }

    private void setNumberButtonListeners() {
        button0.setOnClickListener(v -> InserimentoCifra("0"));
        button1.setOnClickListener(v -> InserimentoCifra("1"));
        button2.setOnClickListener(v -> InserimentoCifra("2"));
        button3.setOnClickListener(v -> InserimentoCifra("3"));
        button4.setOnClickListener(v -> InserimentoCifra("4"));
        button5.setOnClickListener(v -> InserimentoCifra("5"));
        button6.setOnClickListener(v -> InserimentoCifra("6"));
        button7.setOnClickListener(v -> InserimentoCifra("7"));
        button8.setOnClickListener(v -> InserimentoCifra("8"));
        button9.setOnClickListener(v -> InserimentoCifra("9"));
    }

    private void setOperationButtonListeners() {
        buttonClear.setOnClickListener(v -> Clear());
        buttonDivide.setOnClickListener(v -> InserimentoOperatore("/") );
        buttonMultiply.setOnClickListener(v -> InserimentoOperatore("*") );
        buttonSubtract.setOnClickListener(v -> InserimentoOperatore("-") );
        buttonAdd.setOnClickListener(v -> InserimentoOperatore("+") );
        buttonEquals.setOnClickListener(v -> calculate());
    }

    private void InserimentoCifra(String cifra) {
        if (!primoOperandoInserito) {
            if (!(primacifra && cifra.equals("0"))) {
                if (primacifra) risultato.setText("");
                primacifra = false;
                primoOperando += cifra;
                risultato.append(cifra);
            }
        } else {
            if (!(primacifra && cifra.equals("0"))) {
                primacifra = false;
                secondoOperando += cifra;
                risultato.append(cifra);
            }
        }
    }

    private void InserimentoOperatore(String operatore) {
        if (primoOperando.length() > 0 && !operazioneInCorso) {
            primoOperandoInserito = true;
            operazione = operatore;
            operazioneInCorso = true;
            primacifra = true;
            risultato.append(" " + operatore + " ");
        }
    }

    private void Clear() {
        risultato.setText("0");
        primoOperando = "";
        secondoOperando = "";
        operazione = "";
        primacifra = true;
        operazioneSelezionata = false;
        ugualePremuto = false;
        primoOperandoInserito = false;
        operazioneInCorso = false;
    }
    private void calculate() {
        if (!primoOperando.isEmpty() && !secondoOperando.isEmpty() && !operazione.isEmpty()) {
            int num1 = Integer.parseInt(primoOperando);
            int num2 = Integer.parseInt(secondoOperando);
            int risultatoOperazione = 0;

            switch (operazione) {
                case "+": risultatoOperazione = num1 + num2; break;
                case "-": risultatoOperazione = num1 - num2; break;
                case "*": risultatoOperazione = num1 * num2; break;
                case "/":
                    if (num2 != 0) {
                        risultatoOperazione = num1 / num2;
                    } else {
                        risultato.setText("Errore");
                        return;
                    }
                    break;
            }

            risultato.setText(String.valueOf(risultatoOperazione));
            primoOperando = String.valueOf(risultatoOperazione);
            secondoOperando = "";
            operazione = "";
            operazioneInCorso = false;
            primoOperandoInserito = false;
        }
    }
}