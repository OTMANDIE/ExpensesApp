package com.example.expensesapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesapp.R
import com.example.expensesapp.model.Expense
import com.example.expensesapp.viewmodel.ExpenseViewModel
import com.example.expensesapp.workmanager.ReminderWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ExpenseAdapter.OnDeleteClickListener {
    private lateinit var monthlyIncomeEditText: EditText
    private lateinit var totalTextView: TextView
    private lateinit var transactionsRecyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ReminderWorker.scheduleReminder(this)


        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        monthlyIncomeEditText = findViewById(R.id.monthlyIncomeEditText)
        totalTextView = findViewById(R.id.totalTextView)
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView)

        adapter = ExpenseAdapter(emptyList(), this)
        transactionsRecyclerView.adapter = adapter
        transactionsRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.expenses.observe(this, { expenses ->
            adapter.updateData(expenses)
        })

        val addTransactionButton: FloatingActionButton = findViewById(R.id.addTransactionButton)

        addTransactionButton.setOnClickListener {
            startActivityForResult(Intent(this, AddExpenseActivity::class.java), 1)
        }

        monthlyIncomeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val income = monthlyIncomeEditText.text.toString().toDoubleOrNull() ?: 0.0
                viewModel.setMonthlyIncome(income)
            }
        }

        viewModel.totalExpenses.observe(this, { total ->
            totalTextView.text = "Total: $$total"
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val expense = data?.getDoubleExtra(AddExpenseActivity.EXTRA_EXPENSE_AMOUNT, 0.0) ?: 0.0
            val name = data?.getStringExtra(AddExpenseActivity.EXTRA_EXPENSE_NAME) ?: ""
            val date = data?.getStringExtra(AddExpenseActivity.EXTRA_EXPENSE_DATE) ?: ""
            viewModel.addExpense(Expense(name, expense, date))
        }
    }

    override fun onDeleteClick(expense: Expense) {
        viewModel.removeExpense(expense)
    }
}
