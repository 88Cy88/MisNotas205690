
package sauceda.carlos.misnotas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream

class AgregarNotaActivity : AppCompatActivity() {
    private lateinit var  btn_guardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        btn_guardar = findViewById(R.id.btn_guardar)
        btn_guardar.setOnClickListener{
        guardar_nota()
        }

    }

    fun guardar_nota(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            !=PackageManager.PERMISSION_DENIED
        ) {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 235)

        }else{
        guardar()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            235-> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    guardar()
                }else{
                    Toast.makeText(this,"Error: permisos denegados", Toast.LENGTH_SHORT).show()
                }

            }
            }

        }

    public fun guardar(){
        var titulo : String = findViewById<View?>(R.id.et_titulo).toString()
        var cuerpo : String = findViewById<View?>(R.id.et_contenido).toString()

        if (titulo=="" || cuerpo==""){
        Toast.makeText(this,"Error: campos vacios",Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo = File(ubicacion(),titulo+".txt")
                val fos= FileOutputStream(archivo)
                fos.write(cuerpo.toByteArray())
                fos.close()
                Toast.makeText(this,"Se a guardado el archivo en la carpeta publica", Toast.LENGTH_SHORT).show()
            }catch (e: java.lang.Exception){
                Toast.makeText(this,"Error: no se guardo el archivo",Toast.LENGTH_SHORT).show()
            }

        }
        finish()
    }

    public fun ubicacion():String{
        val carpeta = File(getExternalFilesDir(null),"notas")
        if (!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }



    }

