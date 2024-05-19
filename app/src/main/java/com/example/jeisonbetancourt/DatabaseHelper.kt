import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_database.db"
        private const val DATABASE_VERSION = 1

        // Definir la tabla de usuarios y las columnas
        const val TABLE_NAME = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "nombre_usuario"
        const val COLUMN_PASSWORD = "contraseña"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear la tabla de usuarios
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Manejar la actualización de la base de datos si cambia la versión
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
