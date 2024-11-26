package danif.android.app_registro_rb.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Registros::class], version = 1)
abstract class BaseDatos: RoomDatabase() {
    abstract fun registrosDAO():RegistrosDAO
}