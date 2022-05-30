package com.example.permisosandroidmensaje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Escribimos el permiso en el manifest
    // <uses-permission android:name="android.permission.SEND_SMS" />
    // La comprobacion del permiso es basica y solo se solicita una vez.

    EditText editTextMensaje, editTextNumero;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarComponentes();

        buscarPermisos();
        listenerBtn();
    }

    private void enlazarComponentes(){

        editTextMensaje = findViewById(R.id.editTextMensaje);
        editTextNumero = findViewById(R.id.editTextNumero);
        btnEnviar = findViewById(R.id.btnEnviar);
    }

    private void buscarPermisos(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            // permiso no aceptado, solicitacion de permiso
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 999);
        }
    }

    private void listenerBtn(){

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNumero.getText().toString().trim().length() < 8 || editTextMensaje.getText().toString().trim().length() < 1){
                    Toast.makeText(MainActivity.this, "Algún campo inválido", Toast.LENGTH_SHORT).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(editTextNumero.getText().toString(), null, editTextMensaje.getText().toString(), null, null);
                    Toast.makeText(MainActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}