package com.example.login;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.login.databinding.ActivityMapaBinding;

public class mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btncamara;
    Button btnsocial;


    private ActivityMapaBinding binding;
    public String nombre;
    public String dni;
    public String celular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = this.getIntent();
         nombre= i.getStringExtra("nombre");
         dni=i.getStringExtra("dni");
         celular= i.getStringExtra("celular");


        btnsocial=findViewById(R.id.btnsocial);
        btncamara=findViewById(R.id.btncamara);

        btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reporte();
            }
        });


        btnsocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfil();
            }
        });



    }

    public void reporte()
    {

        Intent intent=new Intent(mapa.this,reporte.class);
        intent.putExtra("dni",dni);
        startActivity(intent);


    }

    public void perfil()
    {
        Intent intent=new Intent(mapa.this,Welcome.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("celular",celular);
        startActivity(intent);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
        mMap.setMyLocationEnabled(true);
    }





}