package com.example.ucevaapp20232.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "DbCodeFixIA.db";
    public static final String TABLE_USUARIOS = "usuarios";
    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        onCreate(sqLiteDatabase);

    }


    public boolean agregarUsuario(String email, String password) {


        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);

            return db.insert(TABLE_USUARIOS, null, contentValues) != -1;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean verificarUsuario(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String passwordCifrado = Metodo_MD5.cifrarMD5(password);

        String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {email, passwordCifrado});

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);

        if (cursor.moveToFirst()) {
            do {
                int indexId = cursor.getColumnIndex("id_usuario");
                int indexEmail = cursor.getColumnIndex("email");
                int indexPassword = cursor.getColumnIndex("password");

                if (indexId != -1 && indexEmail != -1 && indexPassword != -1) {
                    int id = cursor.getInt(indexId);
                    String email = cursor.getString(indexEmail);
                    String password = cursor.getString(indexPassword);
                    listaUsuarios.add(new Usuario(id, email, password));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaUsuarios;
    }


    public int eliminarUsuario(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_USUARIOS, "id_usuario = ?", new String[]{String.valueOf(id)});

        }
    }


