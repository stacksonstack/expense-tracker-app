package com.example.expense_tracker

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val expenseArray = ArrayList<Double>()
    private val incomeArray = ArrayList<Double>()
    private val expenseView= ArrayList<String>()
    private val incomeView = ArrayList<String>()
    private val historyView = ArrayList<String>()

    private var balance = 2000.0
    private var isExpense = false
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.expenseToggle->
                    if (checked) {
                    isExpense = true
                    }
                R.id.incomeToggle ->
                    if (checked) isExpense = false
            }
        }
    }
    fun sendToAbout(view: View) {
        val message = "About Our Project"
        val intent =
            Intent(this, AboutActivity::class.java).apply {
            putExtra("EXTRA_M", message)
        }
        startActivity(intent)
    }

    fun sendToHistory(view: View) {
        val message = "History"
        val intent = Intent(this, HistoryActivity::class.java).apply {
            putExtra("EXTRA_HISTORY", message)
            putStringArrayListExtra("EXTRA_ARRAY", historyView)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentBalance: TextView = findViewById(R.id.currentBalance)
        val expenseList: ListView = findViewById(R.id.expenseList)
        val incomeList: ListView = findViewById(R.id.incomeList)
        val expenseAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, expenseView)
        val incomeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, incomeView)
        expenseList.adapter = expenseAdapter
        incomeList.adapter = incomeAdapter
        val submit: Button = findViewById(R.id.submit)
        val description: EditText = findViewById(R.id.desc)
        val money: EditText = findViewById(R.id.money)
        val balanceButton: Button = findViewById(R.id.balanceButton)
        val userBalance: EditText = findViewById(R.id.updateBalance)
        val totalExpense: TextView = findViewById(R.id.totalExpense)
        val totalIncome: TextView = findViewById(R.id.totalIncome)

        currentBalance.text = "Current Balance: 2000"

        fun reduceArray(arr: MutableList<Double>): Double {
             return arr.fold( 0.0 , {  acc, next -> acc + next })


        }

        fun addToHistory(desc: String, money: Double, type: String): String {
            return "Description: $desc, Money: $money, type: $type Balance: $balance"
        }

        fun deleteToHistory(desc: String, id: String): String{
            if(id === "EXPENSE") {return "Deleted: $desc, Balance: $balance Expense Amount: ${reduceArray(expenseArray)}" }
            return "Deleted $desc, Balance: $balance Income Amount: ${reduceArray(incomeArray)}"
        }

         fun updateBalance(incomeInput: Double): Double {
             balance += incomeInput
             return balance
        }
        fun makeDescription(numInput : Double, desc : String): String {
            return "${desc}: $numInput"

        }

        fun addToArray(doubleInput: Double, desc: String): Boolean {
            if(isExpense) {
                historyView.add(addToHistory(desc, doubleInput, "Income"))
                expenseArray.add(doubleInput * -1)
                return expenseView.add(makeDescription(doubleInput, desc))
            }
            historyView.add(addToHistory(desc, doubleInput, "Expense"))
            incomeArray.add(doubleInput)
            return incomeView.add(makeDescription(doubleInput, desc))

        }

        fun determineMoney(numInput: String): Double {
            val doubleInput = numInput.toDouble()
            if(isExpense) return doubleInput * -1
            return doubleInput
        }


        fun checkTextView(id: String, amount: Double): String {
            if(id === "EXPENSE") {
                totalExpense.text = "Total Expenses: $amount"
                return "Total Expense $Double"
            }
            totalIncome.text = "Total Income: $amount"
            return "Total Income $Double"
        }
        fun determineBalance(id: String, amount: Double): Double {
            if(id === "EXPENSE") {
                return updateBalance(amount)
            }
            return updateBalance(amount * -1)
        }
        fun deleteTransaction(position: Int, numArray: MutableList<Double>, viewArray: MutableList<String>, id: String, adapter: ArrayAdapter<String>) {
            val item: String = viewArray[position]
            val deletedAmount: Double = numArray[position]
            var amount: Double = 0.0
            viewArray.removeAt(position)
            numArray.removeAt(position)
            if(numArray.size != 0) {
                amount = reduceArray(numArray)
            }
            checkTextView(id, amount)
            currentBalance.text = "Current Balance: ${determineBalance(id, deletedAmount)}"

            historyView.add(deleteToHistory(item, id))

            adapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "You deleted : $item", Toast.LENGTH_SHORT).show()

        }
        fun setNewBalance(newBalance: String): String {
            balance = newBalance.toDouble()
            return newBalance
        }
        balanceButton.setOnClickListener() {
            val newBalance = userBalance.text.toString()
            currentBalance.text = "Current Balance: ${setNewBalance(newBalance)}"
        }
        submit.setOnClickListener() {
            val description = description.text.toString()
            val money = determineMoney(money.text.toString())
            val incomeAmount = reduceArray(incomeArray)
            val expenseAmount = reduceArray(expenseArray)

            totalExpense.text = "Total Expenses: $expenseAmount"
            totalIncome.text = "Total Income: $incomeAmount"
            currentBalance.text = "Current Balance: ${updateBalance(money)}"
            addToArray(money, description)



            expenseAdapter.notifyDataSetChanged()
            incomeAdapter.notifyDataSetChanged()
        }
        expenseList.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            deleteTransaction(position, expenseArray, expenseView, "EXPENSE", expenseAdapter)
        }

        incomeList.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            deleteTransaction(position, incomeArray, incomeView, "INCOME", incomeAdapter)

    }
}
}
