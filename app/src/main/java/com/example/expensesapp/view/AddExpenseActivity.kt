package com.example.expensesapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.expensesapp.R

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var expenseEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var dateEditText: EditText

    companion object {
        const val EXTRA_EXPENSE_AMOUNT = "extra_expense_amount"
        const val EXTRA_EXPENSE_DATE = "extra_expense_date"
        const val EXTRA_EXPENSE_NAME = "extra_expense_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        expenseEditText = findViewById(R.id.expenseEditText)
        nameEditText = findViewById(R.id.nameEditText)
        dateEditText = findViewById(R.id.dateEditText)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val date = dateEditText.text.toString()
            val expense = expenseEditText.text.toString().toDoubleOrNull() ?: 0.0
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_EXPENSE_NAME, name)
            resultIntent.putExtra(EXTRA_EXPENSE_DATE, date)
            resultIntent.putExtra(EXTRA_EXPENSE_AMOUNT, expense)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}
