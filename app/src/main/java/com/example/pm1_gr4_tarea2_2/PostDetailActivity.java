package com.example.pm1_gr4_tarea2_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvUserId = findViewById(R.id.tvUserId);
        TextView tvId = findViewById(R.id.tvId);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvBody = findViewById(R.id.tvBody);
        Button btnBack = findViewById(R.id.btnRegresar);

        // Obtener datos del Intent
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");

        // Mostrar datos
        tvUserId.setText("User ID: " + userId);
        tvId.setText("Post ID: " + id);
        tvTitle.setText("Title: " + title);
        tvBody.setText("Body: " + body);

        // Configurar botón de regreso
        btnBack.setOnClickListener(v -> {
            finish(); // Cierra esta Activity y regresa a la anterior
        });
    }
}