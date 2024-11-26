package danif.android.app_registro_rb.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "RegistrosCuentasBasicas")
data class Registros(
    @PrimaryKey(autoGenerate = true) var id:Long? = null,
    var precioRegistro:String,
    var fecha:String,
    var tipoRegistro:String,
)