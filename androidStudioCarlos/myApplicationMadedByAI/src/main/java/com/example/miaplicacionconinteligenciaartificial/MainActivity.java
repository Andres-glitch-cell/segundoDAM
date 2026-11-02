package com.example.miaplicacionconinteligenciaartificial;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etDescription, etAmount;
    private Button btnAddExpense;
    private TextView tvTotalAmount, tvAiAdvice, tvExpenseList;

    private double totalSpent = 0.0;
    private final List<String> expenseHistory = new ArrayList<>();

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

        etDescription = findViewById(R.id.etDescription);
        etAmount = findViewById(R.id.etAmount);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvAiAdvice = findViewById(R.id.tvAiAdvice);
        tvExpenseList = findViewById(R.id.tvExpenseList);

        btnAddExpense.setOnClickListener(v -> addExpense());
    }

    private void addExpense() {
        String desc = etDescription.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();

        if (desc.isEmpty()) {
            etDescription.setError("Enter description");
            return;
        }

        if (amountStr.isEmpty()) {
            etAmount.setError("Enter amount");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            etAmount.setError("Invalid amount");
            return;
        }

        if (amount <= 0) {
            etAmount.setError("Amount must be greater than 0");
            return;
        }

        totalSpent += amount;
        String expenseEntry = String.format(Locale.getDefault(), "• %s: $%.2f", desc, amount);
        expenseHistory.add(0, expenseEntry); // Add to top

        tvTotalAmount.setText(String.format(Locale.getDefault(), "$%.2f", totalSpent));
        updateExpenseListDisplay();
        updateAiAdvice(amount);

        // Clear inputs
        etDescription.setText("");
        etAmount.setText("");
        etDescription.clearFocus();
        etAmount.clearFocus();

        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show();
    }

    private void updateExpenseListDisplay() {
        if (expenseHistory.isEmpty()) {
            tvExpenseList.setText("No expenses recorded yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String item : expenseHistory) {
                sb.append(item).append("\n");
            }
            tvExpenseList.setText(sb.toString().trim());
        }
    }

    private void updateAiAdvice(double lastAmount) {
        String advice;
        if (totalSpent > 150.0) {
            advice = String.format(Locale.getDefault(), "⚠️ AI Warning: Total spending ($%.2f) is getting high! Consider cutting back on non-essential purchases.", totalSpent);
        } else if (lastAmount > 50.0) {
            advice = String.format(Locale.getDefault(), "💡 AI Insight: That was a relatively large single expense ($%.2f). Make sure it fits your weekly budget!", lastAmount);
        } else {
            advice = String.format(Locale.getDefault(), "✨ AI Insight: Great job keeping expenses low! Total spent so far is $%.2f.", totalSpent);
        }
        tvAiAdvice.setText(advice);
    }
}
