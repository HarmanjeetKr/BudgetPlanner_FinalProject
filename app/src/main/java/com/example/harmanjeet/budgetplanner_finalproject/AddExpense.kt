package com.example.harmanjeet.budgetplanner_finalproject

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.add_expense_income.*

class AddExpense:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_expense_income)
    }

    public fun addRecord(view: View) {
        var values = ContentValues()
        if (!(editmth.selectedItem.toString().isEmpty())
            && (!(editRnt.text.toString().isEmpty()))
            && (!(editInsurance.text.toString().isEmpty()))
            && (!(editgros.text.toString().isEmpty()))
            && (!(editpersonal.text.toString().isEmpty()))
            && (!(editClth.text.toString().isEmpty()))
            && (!(editTrspt.text.toString().isEmpty()))
            && (!(editPhn.text.toString().isEmpty()))
            && (!(editgas.text.toString().isEmpty()))
            && (!(editBills.text.toString().isEmpty()))) {
            values.put(BudgetContentProvider.MONTH, editmth.selectedItem.toString())
            values.put(BudgetContentProvider.MORTGAGE,editRnt.text.toString())
            values.put(BudgetContentProvider.INSURANCE,editInsurance.text.toString())
            values.put(BudgetContentProvider.GROCERIES,editgros.text.toString())
            values.put(BudgetContentProvider.PERSONAL,editpersonal.text.toString())
            values.put(BudgetContentProvider.CLOTHING,editClth.text.toString())
            values.put(BudgetContentProvider.TRANSPORT,editTrspt.text.toString())
            values.put(BudgetContentProvider.GAS,editgas.text.toString())
            values.put(BudgetContentProvider.PHONE,editPhn.text.toString())
            values.put(BudgetContentProvider.UTILITYBILL,editBills.text.toString())


            contentResolver.insert(BudgetContentProvider.CONTENT_URI,values)
            Toast.makeText(baseContext, "Record Inserted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(baseContext, "Please enter the records first", Toast.LENGTH_LONG).show()
        }
    }

    public fun showAllRecords(view: View) {

        val URL = "content://com.example.harmanjeet.provider/expenses"
        var expense = Uri.parse(URL)
        Log.i("Harman", expense.toString())
        Log.i("Harman", URL.toString())
        var c = contentResolver.query(expense, null, null, null, "month")

        var result = "Content Provider Results:"
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show()
        } else {
            do {
                result += "\n Month: ${c.getString(c.getColumnIndex(BudgetContentProvider.MONTH))}" +
                        ", Mortgage: ${c.getString(c.getColumnIndex(BudgetContentProvider.MORTGAGE))}" +
                        ", Insurance: ${c.getString(c.getColumnIndex(BudgetContentProvider.INSURANCE))}" +
                        ", Groceries: ${c.getString(c.getColumnIndex(BudgetContentProvider.GROCERIES))}" +
                        ", Personal Grooming: ${c.getString(c.getColumnIndex(BudgetContentProvider.PERSONAL))}" +
                        ", Clothing: ${c.getString(c.getColumnIndex(BudgetContentProvider.CLOTHING))}" +
                        ",Transport: ${c.getString(c.getColumnIndex(BudgetContentProvider.TRANSPORT))}" +
                        ", Gas: ${c.getString(c.getColumnIndex(BudgetContentProvider.GAS))}" +
                        ", Phone Bill: ${c.getString(c.getColumnIndex(BudgetContentProvider.PHONE))}" +
                        ", Utility Bills: ${c.getString(c.getColumnIndex(BudgetContentProvider.UTILITYBILL))}"
            } while (c.moveToNext())
            if (!result.isEmpty())
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "No Records present", Toast.LENGTH_LONG).show()
        }
    }

    public fun deleteAllRecords(view: View) {
        //delete all the records and the table of the database provider
        val URL = "content://com.example.harmanjeet.provider/expenses"
        var friends = Uri.parse(URL)
        var count = contentResolver.delete(friends, null, null)
        var countNum = "$count records are deleted."
        Toast.makeText(baseContext, countNum, Toast.LENGTH_LONG).show()
    }

}