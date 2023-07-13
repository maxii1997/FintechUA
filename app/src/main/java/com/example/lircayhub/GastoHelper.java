package com.example.lircayhub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GastoHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gastos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GASTOS = "gastos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRESUPUESTO = "presupuesto";
    public static final String COLUMN_CATEGORIA = "categoria";

    private static final String CREATE_TABLE_GASTOS = "CREATE TABLE " + TABLE_GASTOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PRESUPUESTO + " REAL, "
            + COLUMN_CATEGORIA + " TEXT)";

    public GastoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GASTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si hay cambios en la estructura de la tabla, puedes implementar aquí la lógica de actualización
        // Por ejemplo, eliminar la tabla existente y crear una nueva
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTOS);
        onCreate(db);
    }
}
