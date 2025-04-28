package com.example.minhasfinancas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minhasfinancas.R;
import com.example.minhasfinancas.adapter.GastoAdapter;
import com.example.minhasfinancas.model.Gasto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvGastos;
    private Button btnNovoGasto, btnResumo;
    private List<Gasto> listaGastos = new ArrayList<>();
    private GastoAdapter adapter;

    // Atividade que vai receber o resultado do CadastroActivity
    private final ActivityResultLauncher<Intent> cadastroLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String descricao = result.getData().getStringExtra("descricao");
                    double valor = result.getData().getDoubleExtra("valor", 0);
                    String categoria = result.getData().getStringExtra("categoria");
                    String data = result.getData().getStringExtra("data");

                    Gasto novoGasto = new Gasto(descricao, valor, categoria, data);
                    listaGastos.add(novoGasto);
                    adapter.notifyItemInserted(listaGastos.size() - 1);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGastos = findViewById(R.id.rvGastos);
        btnNovoGasto = findViewById(R.id.btnNovoGasto);
        btnResumo = findViewById(R.id.btnResumo);

        // Lista inicial (exemplo)
        listaGastos.add(new Gasto("Supermercado", 150.00, "Alimentação", "01/04/2025"));
        listaGastos.add(new Gasto("Uber", 30.50, "Transporte", "02/04/2025"));
        listaGastos.add(new Gasto("Cinema", 45.00, "Lazer", "05/04/2025"));

        // Configura adapter
        adapter = new GastoAdapter(listaGastos);
        rvGastos.setLayoutManager(new LinearLayoutManager(this));
        rvGastos.setAdapter(adapter);

        // Botão para novo gasto
        btnNovoGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                cadastroLauncher.launch(intent);
            }
        });

// No método onClick do botão "Resumo"
        btnResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResumoActivity.class);
                intent.putExtra("listaGastos", (ArrayList<Gasto>) listaGastos); // Passando a lista de gastos
                startActivity(intent);
            }
        });

    }
}
