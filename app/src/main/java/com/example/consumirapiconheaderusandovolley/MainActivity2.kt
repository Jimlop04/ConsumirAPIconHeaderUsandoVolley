package com.example.consumirapiconheaderusandovolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val txtMostrarUsuario= findViewById<TextView>(R.id.txt_MostrarUsuario);
        val bundle = this.getIntent().getExtras();
        val boton = findViewById<Button>(R.id.btn_ConsumirAPI);

        txtMostrarUsuario.setText(
            "Bienvenido... \n"
                    +"Usuario: " + bundle?.getString("Usuario") +"\n"
                    +"Contraseña: " +bundle?.getString("Clave"));

        boton.setOnClickListener {

            val ListCountry = Volley.newRequestQueue(this)
            val url = "https://api.covid19api.com/countries"
            val textView = findViewById<TextView>(R.id.txt_MostrarInfAPI)

            var ConsultaJSON = object : JsonArrayRequest(
                Method.GET, url,null,
                { respuestaJson ->
                    var listarPaises = ""
                    for (i in 1 until respuestaJson.length()) {
                        val banco: JSONObject = respuestaJson.getJSONObject(i)
                        listarPaises = listarPaises +
                                "\n"+"CODIGO : "+banco.getString("ISO2")+"\n"+
                                "PAIS : "+banco.getString("Country")+"\n"+
                                "CAPITAL : "+banco.getString("Slug")+"\n"+
                                "\n"
                    }
                    textView.text = listarPaises
                },
                { ErrorJson ->
                    textView.text = ErrorJson.toString()
                }  )

            {
                override fun getHeaders(): Map<String, String>? {
                    val headers = HashMap<String, String>()
                    headers.put("X-Request-Id", "a5504a27-f73a-46d9-a1ed-c6c2e0d83113")
                    return headers
                }
            }
            ListCountry.add(ConsultaJSON);
        }

        fun regresarPrincipal(view: View){
            onBackPressed();
        }


    }
}