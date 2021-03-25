import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.drinkify.model.Fav
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable


class AppDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "DrinkDatabase"

        private val TABLE_FAVORITES = "FavTable"

        private val KEY_ID = "_id"
        private val DRINK_ID = "drinkId"
        private val DRINK_NAME = "drinkName"
        private val DRINK_IMG = "drinkImage"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_FAV_TABLE = ("CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DRINK_ID + " TEXT,"
                + DRINK_NAME + " TEXT," + DRINK_IMG + " TEXT" + ", UNIQUE(" + DRINK_ID + "))")
        db?.execSQL(CREATE_FAV_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        onCreate(db)
    }
    fun addFavDrink(favDrink: Fav): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DRINK_ID, favDrink.idDrink)
        contentValues.put(DRINK_NAME, favDrink.strDrink)

        val success = db.insert(TABLE_FAVORITES, null, contentValues)

        db.close()
        return success
    }

    @SuppressLint("Recycle")
    fun viewFav(): ArrayList<Fav> {

        val favList: ArrayList<Fav> = ArrayList<Fav>()

        val selectQuery = "SELECT  * FROM $TABLE_FAVORITES"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var drinkId: String
        var drinkName: String

        if (cursor.moveToFirst()) {
            do {
                drinkId = cursor.getString(cursor.getColumnIndex(DRINK_ID))
                drinkName = cursor.getString(cursor.getColumnIndex(DRINK_NAME))

                val fav = Fav(idDrink = drinkId, strDrink = drinkName)
                favList.add(fav)

            } while (cursor.moveToNext())
        }
        return favList
    }

    fun deleteFav(fav: Fav): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DRINK_ID, fav.idDrink)
        val success = db.delete(TABLE_FAVORITES, DRINK_ID + "=" + fav.idDrink, null)

        db.close()
        return success
    }
}
