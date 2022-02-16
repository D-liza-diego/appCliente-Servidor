package com.example.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {
    private static final String ruta="http://192.168.1.33/PHP%20ANDROID/registro.php";
    private Map<String,String> parametros;
    public LoginRequest( String celular, String dni, Response.Listener<String> listener){
        super (Request.Method.POST,ruta, listener,null);
        parametros= new HashMap<>();
        parametros.put("dni", dni+"");
        parametros.put("celular", celular+"");


    }

    @Override
    protected Map<String, String> getParams()  {
        return parametros;

    }
}
