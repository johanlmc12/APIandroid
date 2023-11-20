package com.example.ucevaapp20232;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ucevaapp20232.db.DataBase;
import com.example.ucevaapp20232.db.Usuario;

import java.util.List;

public class Administrador extends AppCompatActivity {
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        dataBase = new DataBase(this);

        cargarDatosEnLaTabla();
    }

    private void cargarDatosEnLaTabla() {
        DataBase db = new DataBase(this);
        List<Usuario> listaUsuarios = db.obtenerTodosLosUsuarios();

        TableLayout tabla = findViewById(R.id.table);
        tabla.removeAllViews();

        for (Usuario usuario : listaUsuarios) {
            TableRow fila = new TableRow(this);

            TextView tvId = new TextView(this);
            tvId.setText(String.valueOf(usuario.getId()));
            fila.addView(tvId);

            TextView tvEmail = new TextView(this);
            tvEmail.setText(usuario.getEmail());
            fila.addView(tvEmail);

            TextView tvPassword = new TextView(this);
            tvPassword.setText(usuario.getPassword());
            fila.addView(tvPassword);

            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(v -> {
                db.eliminarUsuario(usuario.getId());
                cargarDatosEnLaTabla();
            });
            fila.addView(btnEliminar);

            tabla.addView(fila);
        }
        Button buttonVolver = findViewById(R.id.buttonVolver);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Administrador.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }



}
