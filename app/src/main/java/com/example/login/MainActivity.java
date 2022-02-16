package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText celularL, dniL;
    String str_celular, str_dni;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        celularL=findViewById(R.id.celularlogin);
        dniL=findViewById(R.id.dnilogin);
        btnlogin=findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String celular=celularL.getText().toString();
                final String dni=dniL.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("por favor espere");
                progressDialog.show();

                Response.Listener<String> respuesta= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok= jsonRespuesta.getBoolean("success");
                            if (ok==true){


                                String nombre= jsonRespuesta.getString("nombre");
                                String celular= jsonRespuesta.getString("celular");
                                String dni=jsonRespuesta.getString("dni");

                                Intent mapa=new Intent(MainActivity.this,mapa.class);
                                mapa.putExtra("nombre",nombre);
                                mapa.putExtra("celular",celular);
                                mapa.putExtra("dni",dni);


                                Toast.makeText(MainActivity.this, "Bienevido", Toast.LENGTH_SHORT).show();
                                startActivity( mapa);


                            }else
                            {
                                progressDialog.dismiss();
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                                alerta.setMessage("Ha ocurrido un error al iniciar sesion")
                                        .setNegativeButton("Reintentar",null)
                                        .create()
                                        .show();
                            }

                        }catch (JSONException e)
                        {
                            e.getMessage();

                        }
                    }
                };
                LoginRequest r= new LoginRequest(celular,dni,respuesta);
                RequestQueue cola= Volley.newRequestQueue(MainActivity.this);
                cola.add(r);
            }
        });




    }

    public void registrar(View view)
    {
        startActivity(new Intent(getApplicationContext(),registro.class));
        finish();
    }

}