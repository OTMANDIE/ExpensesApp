package com.example.expensesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensesapp.model.Expense

class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    private val _totalExpenses = MutableLiveData<Double>()
    val totalExpenses: LiveData<Double> = _totalExpenses

    init {
        _expenses.value = mutableListOf()
        _totalExpenses.value = 0.0
    }

    fun addExpense(expense: Expense) {
        val currentExpenses = _expenses.value.orEmpty().toMutableList()
        currentExpenses.add(expense)
        _expenses.value = currentExpenses
        _totalExpenses.value = _totalExpenses.value?.minus(expense.amount) ?: expense.amount
    }

    fun removeExpense(expense: Expense) {
        val currentExpenses = _expenses.value.orEmpty().toMutableList()
        currentExpenses.remove(expense)
        _expenses.value = currentExpenses
        _totalExpenses.value = _totalExpenses.value?.plus(expense.amount) ?: 0.0
    }

    fun updateData(expenses: List<Expense>) {
        _expenses.value = expenses
    }

    fun setMonthlyIncome(income: Double) {
        val currentExpenses = _totalExpenses.value ?: 0.0
        _totalExpenses.value = income - currentExpenses
    }

}
