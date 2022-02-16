package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    EditText nombre, correo, celular, dni;
    String str_name, str_correo,str_celular, str_dni;
    String url="http://192.168.1.33/PHP%20ANDROID/registrar.php";
    Button btnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);

            nombre= findViewById(R.id.nombreRegistro);
            correo=findViewById(R.id.correoRegistro);
            celular=findViewById(R.id.celularRegistro);
            dni=findViewById(R.id.dniRegistro);
            btnregistrar=findViewById(R.id.button);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });

    }
    public void movetoLogin(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void Register(View view)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this );
        progressDialog.setMessage("por favor espere");

        if(nombre.getText().toString().equals(""))
        {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        }
        else if(correo.getText().toString().equals(""))
        {
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();
        }
        else if(celular.getText().toString().equals(""))
        {
            Toast.makeText(this, "Ingrese celular", Toast.LENGTH_SHORT).show();
        }
        else if(dni.getText().toString().equals(""))
        {
            Toast.makeText(this, "Ingrese dni", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.show();
            str_name=nombre.getText().toString().trim();
            str_correo=correo.getText().toString().trim();
            str_celular=celular.getText().toString().trim();
            str_dni=dni.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(registro.this, response, Toast.LENGTH_SHORT).show();}
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(registro.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show(); }
                 }

            )   {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("dni",str_dni);
                    params.put("nombre",str_name);
                    params.put("celular",str_celular);
                    params.put("correo",str_correo);
                    return params; }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(registro.this);
            requestQueue.add(request);
            }


        }
}