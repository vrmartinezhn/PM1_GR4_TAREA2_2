package com.example.pm1_gr4_tarea2_2.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.pm1_gr4_tarea2_2.model.Post;


public interface JsonPlaceholderApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int id);

}
