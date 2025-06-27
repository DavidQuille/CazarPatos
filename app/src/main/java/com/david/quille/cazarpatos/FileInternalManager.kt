import android.app.Activity
import android.content.Context
import com.david.quille.cazarpatos.FileHandler

class FileInternalManager(val actividad: Activity) : FileHandler {
    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        val texto = datosAGrabar.first + System.lineSeparator() + datosAGrabar.second
        actividad.openFileOutput("fichero.txt", Context.MODE_PRIVATE).bufferedWriter().use { fos ->
            fos.write(texto)
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        return try {
            actividad.openFileInput("fichero.txt").bufferedReader().use {
                val datoLeido = it.readText()
                val textArray = datoLeido.split(System.lineSeparator())
                val email = textArray.getOrNull(0) ?: ""
                val clave = textArray.getOrNull(1) ?: ""
                email to clave
            }
        } catch (e: Exception) {
            "" to ""
        }
    }
}