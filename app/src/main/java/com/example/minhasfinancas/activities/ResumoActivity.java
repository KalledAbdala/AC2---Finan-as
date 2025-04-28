package com.example.minhasfinancas.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minhasfinancas.R;
import com.example.minhasfinancas.model.Gasto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumoActivity extends AppCompatActivity {

    private TextView tvTotalGastos, tvTotalPorCategoria, tvCategoriaMaiorGasto;
    private List<Gasto> listaGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        // Referências das views
        tvTotalGastos = findViewById(R.id.tvTotalGastos);
        tvTotalPorCategoria = findViewById(R.id.tvTotalPorCategoria);
        tvCategoriaMaiorGasto = findViewById(R.id.tvCategoriaMaiorGasto);

        // Receber a lista de gastos da MainActivity
        listaGastos = (List<Gasto>) getIntent().getSerializableExtra("listaGastos");

        if (listaGastos != null && !listaGastos.isEmpty()) {
            // Iniciar o cálculo em segundo plano
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Calcular tudo
                    double totalGastos = 0;
                    Map<String, Double> gastosPorCategoria = new HashMap<>();
                    String categoriaMaiorGasto = "";
                    double maiorGasto = 0;

                    for (Gasto gasto : listaGastos) {
                        // Somar o gasto total
                        totalGastos += gasto.getValor();

                        // Somar os gastos por categoria
                        gastosPorCategoria.put(gasto.getCategoria(),
                                gastosPorCategoria.getOrDefault(gasto.getCategoria(), 0.0) + gasto.getValor());

                        // Verificar a categoria com maior gasto
                        if (gasto.getValor() > maiorGasto) {
                            maiorGasto = gasto.getValor();
                            categoriaMaiorGasto = gasto.getCategoria();
                        }
                    }

                    // Criar um objeto Resultados para armazenar os dados calculados
                    Resultados resultados = new Resultados(totalGastos, gastosPorCategoria, categoriaMaiorGasto, maiorGasto);

                    // Exibir os resultados no UI (precisa rodar no thread principal)
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Mostrar o total de gastos
                            tvTotalGastos.setText("Total de Gastos: R$ " + resultados.getTotalGastos());

                            // Mostrar o total por categoria
                            StringBuilder sbCategorias = new StringBuilder();
                            for (Map.Entry<String, Double> entry : resultados.getGastosPorCategoria().entrySet()) {
                                sbCategorias.append(entry.getKey()).append(": R$ ").append(entry.getValue()).append("\n");
                            }
                            tvTotalPorCategoria.setText(sbCategorias.toString());

                            // Mostrar a categoria com maior gasto
                            tvCategoriaMaiorGasto.setText("Categoria com maior gasto: " + resultados.getCategoriaMaiorGasto() + " (R$ " + resultados.getMaiorGasto() + ")");
                        }
                    });

                }
            }).start();
        } else {
            Toast.makeText(this, "Não há gastos registrados.", Toast.LENGTH_SHORT).show();
        }
    }

    // Classe interna para manter os resultados
    private static class Resultados {
        private final double totalGastos;
        private final Map<String, Double> gastosPorCategoria;
        private final String categoriaMaiorGasto;
        private final double maiorGasto;

        public Resultados(double totalGastos, Map<String, Double> gastosPorCategoria, String categoriaMaiorGasto, double maiorGasto) {
            this.totalGastos = totalGastos;
            this.gastosPorCategoria = gastosPorCategoria;
            this.categoriaMaiorGasto = categoriaMaiorGasto;
            this.maiorGasto = maiorGasto;
        }

        public double getTotalGastos() {
            return totalGastos;
        }

        public Map<String, Double> getGastosPorCategoria() {
            return gastosPorCategoria;
        }

        public String getCategoriaMaiorGasto() {
            return categoriaMaiorGasto;
        }

        public double getMaiorGasto() {
            return maiorGasto;
        }
    }
}
