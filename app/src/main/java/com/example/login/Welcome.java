package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final TextView mensaje1= (TextView)findViewById(R.id.textView3);
        final TextView mensaje2= (TextView)findViewById(R.id.textView4);
        final TextView mensajecel= (TextView)findViewById(R.id.textView5);

        Intent i = this.getIntent();
        String nombre= i.getStringExtra("nombre");
        String celular= i.getStringExtra("celular");
        mensaje1.setText(mensaje1.getText()+"Hola "+nombre+" gracias por ayudarnos a reportar incidentes delictivos");
        mensaje2.setText(mensaje2.getText()+" "+nombre);
        mensajecel.setText(mensajecel.getText()+"+51 "+celular);



    }




}