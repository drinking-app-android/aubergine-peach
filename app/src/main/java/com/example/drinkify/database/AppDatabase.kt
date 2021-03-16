import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.drinkify.model.Fav

//creating the database logic, extending the SQLiteOpenHelper base class
class AppDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "DrinkDatabase"

        private val TABLE_FAVORITES = "FavTable"

        private val KEY_ID = "_id"
        private val DRINK_ID = "drinkId"
        private val DRINK_NAME = "drinkName"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_FAV_TABLE = ("CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DRINK_ID + "TEXT," + DRINK_NAME + " TEXT" + ")")
        db?.execSQL(CREATE_FAV_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        onCreate(db)
    }
    /**
     * Function to insert data
     */
    fun addFavDrink(favDrink: Fav): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DRINK_ID, favDrink.idDrink) // Fav model class id
        contentValues.put(DRINK_NAME, favDrink.strDrink) // Fav model class Name

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_FAVORITES, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

}
