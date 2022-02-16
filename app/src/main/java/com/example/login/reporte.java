package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class reporte extends AppCompatActivity {
    ImageView imageView;
    EditText desc;
    TextView coordenada;
    TextView calles;
    Spinner spinner;
    Button btnsubir;

    Bitmap bitmap;
    String url="http://192.168.1.33/PHP%20ANDROID/subirdatos.php";
    String urlimg="http://192.168.1.33/PHP%20ANDROID/subirimg.php";



    String str_dni,str_descripcion, str_categoria, str_longitud,str_latitud, str_imagen;
    Double latitud, longitud;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        imageView = findViewById(R.id.foto);
        coordenada = findViewById(R.id.coordenadas);
        calles = findViewById(R.id.calle);
        btnsubir=findViewById(R.id.button);
        desc=findViewById(R.id.descripcion);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        List<String> lista= Arrays.asList("Robo al paso","Trafico de drogas","Violaciones","Pandillaje");
        spinner=findViewById(R.id.spinner);
        ArrayAdapter adapter= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        //permisos
        if (ActivityCompat.checkSelfPermission(reporte.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getlocation();
        } else {
            ActivityCompat.requestPermissions(reporte.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        Abrircamara();

        btnsubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subir();
            }
        });



    }


    public void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null)
                {

                    try
                    {
                        Geocoder geocoder = new Geocoder(reporte.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1);

                               /* str_latitud=Double.toString(addresses.get(0).getLatitude());
                                str_longitud=Double.toString(addresses.get(0).getLatitude());
                        coordenada.setText(str_latitud+" "+str_longitud);
                        //coordenada.setText(addresses.get(0).getLatitude()+"  "+addresses.get(0).getLongitude());*/
                        calles.setText(addresses.get(0).getAddressLine(0));

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    str_latitud=Double.toString(location.getLatitude());
                    str_longitud=Double.toString(location.getLongitude());
                    coordenada.setText(str_latitud+" "+str_longitud);


                }
            }
        });
    }
    private void Abrircamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // if(intent.resolveActivity(getPackageManager())!=null){
        startActivityForResult(intent, 1);
        //}
    }

    public void subir()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this );
        progressDialog.setMessage("por favor espere");
        progressDialog.show();

        Intent i = this.getIntent();
        String dni= i.getStringExtra("dni");



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mensaje",response);
                String urldescripcion = response;
                progressDialog.dismiss();
                if (response!="error")
                {
                    StringRequest requestimg = new StringRequest(Request.Method.POST, urlimg, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Toast.makeText(reporte.this, response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(reporte.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show(); }
                    }
                    ) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            str_imagen= convertirimg(bitmap);
                            str_descripcion=desc.getText().toString().trim();
                            Map<String,String> params = new HashMap<>();
                            params.put("url",urldescripcion);
                            params.put("foto",str_imagen);

                            return params;
                        }

                    };
                    RequestQueue requestQueueimg = Volley.newRequestQueue(reporte.this);
                    requestQueueimg.add(requestimg);
                }else
                {
                    Toast.makeText(reporte.this, response, Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(reporte.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(reporte.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show(); }
        }

        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {

                str_dni=dni;
                str_categoria=spinner.getSelectedItem().toString();
                str_descripcion=desc.getText().toString().trim();
               // str_imagen= convertirimg(bitmap);


                Map<String,String> params = new HashMap<>();

                params.put("dni",str_dni);
                params.put("categoria",str_categoria);
                params.put("latitud",str_latitud);
                params.put("longitud",str_longitud);
                params.put("descripcion",str_descripcion);
                //params.put("foto",str_imagen);

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(reporte.this);
        requestQueue.add(request);
        Toast.makeText(reporte.this, "Gracias por alertar", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(reporte.this,mapa.class);
        startActivity(intent);
        finish();

    }

    public String convertirimg(Bitmap bitmap)
    {
        ByteArrayOutputStream array= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte [] imagenByte= array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);}



    }



}
