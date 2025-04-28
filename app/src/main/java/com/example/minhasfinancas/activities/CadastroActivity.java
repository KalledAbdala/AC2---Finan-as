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

public class CadastroActivity extends AppCompatActivity {

    private EditText descricaoEditText, valorEditText, dataEditText;
    private Spinner categoriaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializando os campos de entrada
        descricaoEditText = findViewById(R.id.etDescricao);
        valorEditText = findViewById(R.id.etValor);
        dataEditText = findViewById(R.id.etData);
        categoriaSpinner = findViewById(R.id.spinnerCategoria);

        // Configurar o Spinner com as categorias
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);  // Usando o array de categorias do strings.xml
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adapter);
    }

    // Método chamado quando o botão de salvar for clicado
    public void salvarGasto(View view) {
        String descricao = descricaoEditText.getText().toString();
        double valor = Double.parseDouble(valorEditText.getText().toString());
        String categoria = categoriaSpinner.getSelectedItem().toString();
        String data = dataEditText.getText().toString();


        if (descricao.isEmpty() || valor <= 0 || data.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        Gasto novoGasto = new Gasto(descricao, valor, categoria, data);

        new Thread(() -> {
            GastoDatabase.getInstance(CadastroActivity.this).gastoDao().inserirGastoComTransacao(novoGasto);  // Inserindo o gasto no banco de dados
            runOnUiThread(() -> {
                Toast.makeText(CadastroActivity.this, "Gasto salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();  // Fechando a atividade após salvar
            });
        }).start();
    }
}
