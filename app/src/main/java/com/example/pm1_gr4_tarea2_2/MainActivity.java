package com.example.pm1_gr4_tarea2_2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm1_gr4_tarea2_2.model.Post;
import com.example.pm1_gr4_tarea2_2.network.JsonPlaceholderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);

        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    // Manejo de error
                    return;
                }

                List<Post> posts = response.body();

                // Aquí llenas tu ListView
                ListView listView = findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        convertPostsToTitles(posts) // metodo auxiliar
                );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    // Obtener el ID real del post según la posición
                    int postId = posts.get(position).getId();

                    // Llamada a la API para obtener un post individual
                    Call<Post> callPost = api.getPost(postId);
                    callPost.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                Post post = response.body();
                                // Mostrar detalle en un Toast (puedes abrir otra Activity si quieres)
                                Toast.makeText(MainActivity.this,
                                        "Título: " + post.getTitle() + "\n\n" + post.getBody(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Manejo de error de red
            }
        });

    }

    // Metodo auxiliar para mostrar solo títulos
    private List<String> convertPostsToTitles(List<Post> posts) {
        List<String> titles = new ArrayList<>();
        for (Post p : posts) {
            titles.add(p.getTitle());
        }
        return titles;
    }

}