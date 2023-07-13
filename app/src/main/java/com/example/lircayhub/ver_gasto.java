package com.example.lircayhub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

public class ver_gasto extends AppCompatActivity {

    private TextView registrosTextView;
    private GastoHelper gastoHelper;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_gasto);

        gastoHelper = new GastoHelper(this);
        registrosTextView = findViewById(R.id.registrosTextView);
        searchView = findViewById(R.id.searchView);

        mostrarRegistros();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarRegistros(newText);
                return true;
            }
        });
    }

    private void mostrarRegistros() {
        SQLiteDatabase db = gastoHelper.getReadableDatabase();

        String[] projection = {
                GastoHelper.COLUMN_PRESUPUESTO,
                GastoHelper.COLUMN_CATEGORIA
        };

        String sortOrder = GastoHelper.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                GastoHelper.TABLE_GASTOS,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        StringBuilder registrosBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            double presupuesto = cursor.getDouble(cursor.getColumnIndexOrThrow(GastoHelper.COLUMN_PRESUPUESTO));
            String categoria = cursor.getString(cursor.getColumnIndexOrThrow(GastoHelper.COLUMN_CATEGORIA));

            String registro = getString(R.string.budget) + ": " + presupuesto + "\n" + getString(R.string.category) + ": " + categoria + "\n\n";
            registrosBuilder.append(registro);
        }

        cursor.close();

        registrosTextView.setText(registrosBuilder.toString());
    }

    private void buscarRegistros(String query) {
        SQLiteDatabase db = gastoHelper.getReadableDatabase();

        String[] projection = {
                GastoHelper.COLUMN_PRESUPUESTO,
                GastoHelper.COLUMN_CATEGORIA
        };

        String selection = GastoHelper.COLUMN_CATEGORIA + " LIKE ?";
        String[] selectionArgs = { "%" + query + "%" };

        try {
            double amount = Double.parseDouble(query);
            selection = GastoHelper.COLUMN_CATEGORIA + " LIKE ? OR " + GastoHelper.COLUMN_PRESUPUESTO + " = ?";
            selectionArgs = new String[] { "%" + query + "%", String.valueOf(amount) };
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String sortOrder = GastoHelper.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                GastoHelper.TABLE_GASTOS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        StringBuilder registrosBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            double presupuesto = cursor.getDouble(cursor.getColumnIndexOrThrow(GastoHelper.COLUMN_PRESUPUESTO));
            String categoria = cursor.getString(cursor.getColumnIndexOrThrow(GastoHelper.COLUMN_CATEGORIA));

            String registro = getString(R.string.budget) + ": " + presupuesto + "\n" + getString(R.string.category) + ": " + categoria + "\n\n";
            registrosBuilder.append(registro);
        }

        cursor.close();

        registrosTextView.setText(registrosBuilder.toString());
    }
}
