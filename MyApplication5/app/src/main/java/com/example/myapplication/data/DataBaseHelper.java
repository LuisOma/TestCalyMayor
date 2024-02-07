package com.example.myapplication.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.network.SanitAbastecimiento;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SanitAbastecimientoDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SANIT_ABASTECIMIENTO = "sanitAbastecimiento";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_SANIT_ABASTECIMIENTO + "("
                + "idAbastecimiento INTEGER PRIMARY KEY,"
                + "tipoAbastecimiento TEXT,"
                + "usuarioCreacion TEXT,"
                + "usuarioModificacion TEXT,"
                + "usuarioEliminacion TEXT,"
                + "fechaCreacion TEXT,"
                + "fechaModificacion TEXT,"
                + "fechaEliminacion TEXT,"
                + "opcion TEXT,"
                + "foto BLOB"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SANIT_ABASTECIMIENTO);
        onCreate(db);
    }

    public void insertSanitAbastecimiento(List<SanitAbastecimiento> sanitAbastecimientos) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (SanitAbastecimiento sanit : sanitAbastecimientos) {
                values.put("idAbastecimiento", sanit.getIdAbastecimiento());
                values.put("tipoAbastecimiento", sanit.getTipoAbastecimiento());
                values.put("usuarioCreacion", sanit.getUsuarioCreacion());
                values.put("usuarioModificacion", sanit.getUsuarioModificacion());
                values.put("usuarioEliminacion", sanit.getUsuarioEliminacion());
                values.put("fechaCreacion", sanit.getFechaCreacion());
                values.put("fechaModificacion", sanit.getFechaModificacion());
                values.put("fechaEliminacion", sanit.getFechaEliminacion());
                values.put("opcion", sanit.getOpcion());
                values.put("foto", sanit.getFoto());

                db.insertWithOnConflict(TABLE_SANIT_ABASTECIMIENTO, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @SuppressLint("Range")
    public List<SanitAbastecimiento> getAllSanitAbastecimientos() {
        List<SanitAbastecimiento> sanitAbastecimientos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SANIT_ABASTECIMIENTO, null);

        if (cursor.moveToFirst()) {
            do {
                SanitAbastecimiento sanit = new SanitAbastecimiento();
                sanit.setIdAbastecimiento(cursor.getInt(cursor.getColumnIndex("idAbastecimiento")));
                sanit.setTipoAbastecimiento(cursor.getString(cursor.getColumnIndex("tipoAbastecimiento")));
                sanit.setUsuarioCreacion(cursor.getString(cursor.getColumnIndex("usuarioCreacion")));
                sanit.setUsuarioModificacion(cursor.getString(cursor.getColumnIndex("usuarioModificacion")));
                sanit.setUsuarioEliminacion(cursor.getString(cursor.getColumnIndex("usuarioEliminacion")));
                sanit.setFechaCreacion(cursor.getString(cursor.getColumnIndex("fechaCreacion")));
                sanit.setFechaModificacion(cursor.getString(cursor.getColumnIndex("fechaModificacion")));
                sanit.setFechaEliminacion(cursor.getString(cursor.getColumnIndex("fechaEliminacion")));
                sanit.setOpcion(cursor.getString(cursor.getColumnIndex("opcion")));
                sanit.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));

                sanitAbastecimientos.add(sanit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sanitAbastecimientos;
    }

    public void updateFoto(int idAbastecimiento, byte[] foto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("foto", foto);

        db.update(TABLE_SANIT_ABASTECIMIENTO, values, "idAbastecimiento = ?", new String[]{String.valueOf(idAbastecimiento)});
    }

    public void updateOpcion(int idAbastecimiento, String opcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("opcion", opcion);

        db.update(TABLE_SANIT_ABASTECIMIENTO, values, "idAbastecimiento = ?", new String[]{String.valueOf(idAbastecimiento)});
    }
}
