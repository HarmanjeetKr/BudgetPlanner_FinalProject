package com.example.harmanjeet.budgetplanner_finalproject

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils

class BudgetContentProvider: ContentProvider() {
    companion object {
        val ID: String = "id"
//        val PROVIDER_NAME: String = "com.example.harmanjeet.BudgetContentProvider"
        val PROVIDER_NAME: String = "com.example.harmanjeet.provider"
        val MONTH: String = "month"
        val MORTGAGE: String = "mortgage"
        val INSURANCE: String = "insurance"
        val GROCERIES: String = "groceries"
        val PERSONAL: String = "personal"
        val CLOTHING: String = "clothing"
        val TRANSPORT: String = "transport"
        val GAS: String = "gas"
        val PHONE: String = "phone"
        val UTILITYBILL: String = "utilitybill"
        val CONTENT_URI: Uri = Uri.parse("content://$PROVIDER_NAME/expenses")
    }

    // integer values used in content URI
    val EXPENSE: Int = 1
    val EXPENSE_ID: Int = 2

    // projection map for a query
    private val mExpenseMap = HashMap<String, String>()

    // maps content URI "patterns" to the integer values that were set above
    var mUriMatcher: UriMatcher? = null

    // mDatabase declarations
    private var myDatabase: SQLiteDatabase? = null
    val DATABASE_NAME = "ExpenseDirectory"
    val TABLE_NAME = "Expense"
    val DATABASE_VERSION = 1
    val CREATE_TABLE = " CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "month TEXT NOT NULL, " +
            "mortgage INTEGER NOT NULL, " +
            "insurance INTEGER NOT NULL, " +
            "groceries INTEGER NOT NULL, " +
            "personal INTEGER NOT NULL, " +
            "clothing INTEGER NOT NULL, " +
            "transport INTEGER NOT NULL, " +
            "gas INTEGER NOT NULL, " +
            "phone INTEGER NOT NULL, " +
            "utilitybill INTEGER NOT NULL) ;"

    init {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher?.addURI(PROVIDER_NAME, "expenses", EXPENSE)
        mUriMatcher?.addURI(PROVIDER_NAME, "expenses /#", EXPENSE_ID)
    }

    override fun onCreate(): Boolean {
        var context = context
        var mDbHelper = DBHelper(context)

        // permissions to be writable
        myDatabase = mDbHelper.writableDatabase
        return myDatabase != null
    }

    override fun query(uri: Uri?,
                       projection: Array<out String>?,
                       selection: String?,
                       selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor {
        var queryBuilder = SQLiteQueryBuilder()
        // the TABLE_NAME to query on
        queryBuilder.tables = TABLE_NAME
        when (mUriMatcher?.match(uri)) {
            EXPENSE -> queryBuilder.setProjectionMap(mExpenseMap)
            EXPENSE_ID -> queryBuilder.appendWhere(ID + " =" + uri?.getLastPathSegment())
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }
        var sort_order = MONTH


        if (!TextUtils.isEmpty(sortOrder)) {
            // No sorting-> sort on names by default
            sort_order = sortOrder.toString()
        }
        val cursor = queryBuilder.query(myDatabase,
            projection, selection, selectionArgs,
            null, null, sort_order)
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        val row = myDatabase?.insert(TABLE_NAME, "", values)
        // If record is added successfully
        if (row != null) {
            if (row > 0) {
                var newUri = ContentUris.withAppendedId(CONTENT_URI, row)
                context.getContentResolver().notifyChange(newUri, null)
                return newUri
            }
        }
        throw SQLException("Fail to add a new record into" + uri)
    }

    override fun update(uri: Uri?,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        var count = 0
        when (mUriMatcher?.match(uri)) {
            EXPENSE -> count = myDatabase?.update(TABLE_NAME, values, selection, selectionArgs)!!
            EXPENSE_ID -> {
                var whereClause = ""
                if (!TextUtils.isEmpty(selection)) {
                    whereClause = " AND($selection)"
                }
                count = myDatabase?.update(TABLE_NAME, values, "$ID = ${uri?.lastPathSegment} $whereClause", selectionArgs)!!
            }
            else -> throw IllegalArgumentException("Unsupported URI " + uri)
        }
        context.contentResolver.notifyChange(uri, null);
        return count;
    }


    override fun delete(uri: Uri?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        var count = 0
        when (mUriMatcher?.match(uri)) {
            EXPENSE -> count = myDatabase?.delete(TABLE_NAME, selection, selectionArgs)!!
            EXPENSE_ID -> {
                var whereClause = ""
                if (!TextUtils.isEmpty(selection)) {
                    whereClause = " AND($selection)"
                }
                count = myDatabase?.delete(TABLE_NAME, "$ID = ${uri?.lastPathSegment} $whereClause", selectionArgs)!!
            }
            else -> throw IllegalArgumentException("Unsupported URI " + uri);
        }
        context.contentResolver.notifyChange(uri, null);
        return count;
    }

    override fun getType(uri: Uri?): String {
        when (mUriMatcher?.match(uri)) {
            EXPENSE -> return "vnd.android.cursor.dir/vnd.example.nicknames"
            EXPENSE_ID -> return "vnd.android.cursor.item/vnd.example.nicknames"
            else -> throw IllegalArgumentException("Unsupported URI:" + uri)
        }
    }

    private inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_TABLE);
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db)
        }
    }
}