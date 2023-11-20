package com.example.ucevaapp20232;
import static com.example.ucevaapp20232.db.DataBase.TABLE_USUARIOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ucevaapp20232.db.DataBase;

public class MainActivity2 extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonEliminar = findViewById(R.id.buttonEliminar);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarUsuario();
            }
        });
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Administrador.class);
                startActivity(intent);
            }
        });
    }


    private void registrarUsuario() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        DataBase db = new DataBase(this);
        boolean result = db.agregarUsuario(email, password);

        if (result) {
            Toast.makeText(MainActivity2.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity2.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }
}