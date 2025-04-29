package com.example.minhasfinancas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minhasfinancas.R;
import com.example.minhasfinancas.database.GastoDatabase;
import com.example.minhasfinancas.model.Gasto;
import android.content.Intent;


public class CadastroActivity extends AppCompatActivity {

    private EditText descricaoEditText, valorEditText, dataEditText;
    private Spinner categoriaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        descricaoEditText = findViewById(R.id.etDescricao);
        valorEditText = findViewById(R.id.etValor);
        dataEditText = findViewById(R.id.etData);
        categoriaSpinner = findViewById(R.id.spinnerCategoria);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);  // Usando o array de categorias do strings.xml
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adapter);
    }

    public void salvarGasto(View view) {
        String descricao = descricaoEditText.getText().toString();
        String valorTexto = valorEditText.getText().toString();
        String data = dataEditText.getText().toString();
        String categoria = categoriaSpinner.getSelectedItem().toString();

        if (descricao.isEmpty() || valorTexto.isEmpty() || data.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorTexto);

        Gasto novoGasto = new Gasto(descricao, valor, categoria, data);

        new Thread(() -> {
            GastoDatabase.getInstance(CadastroActivity.this).gastoDao().inserirGastoComTransacao(novoGasto);

            runOnUiThread(() -> {
                Intent resultadoIntent = new Intent();
                resultadoIntent.putExtra("descricao", descricao);
                resultadoIntent.putExtra("valor", valor);
                resultadoIntent.putExtra("categoria", categoria);
                resultadoIntent.putExtra("data", data);

                setResult(RESULT_OK, resultadoIntent);
                Toast.makeText(CadastroActivity.this, "Gasto salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
