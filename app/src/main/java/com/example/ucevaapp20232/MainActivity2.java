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
import com.example.ucevaapp20232.db.Metodo_MD5;

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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registrarUsuario() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity2.this, "Por favor ingrese el email y la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordCifrada = Metodo_MD5.cifrarMD5(password);
        DataBase db = new DataBase(this);

        if (email.equals("jordan-admin@gmail.com") && password.equals("Admin123")) {
            Intent intent = new Intent(MainActivity2.this, Administrador.class);
            startActivity(intent);
            return;
        }

        boolean result = db.agregarUsuario(email, passwordCifrada);

        if (result) {
            Toast.makeText(MainActivity2.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity2.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }
}