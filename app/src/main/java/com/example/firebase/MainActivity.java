package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.adapters.MovieAdapter;
import com.example.firebase.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FloatingActionButton fabLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        rvMovies = findViewById(R.id.rvMovies);
        fabLogout = findViewById(R.id.fabLogout);

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, movie -> {
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(movieAdapter);

        fabLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        fetchMovies();
    }

    private void fetchMovies() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                        
                        if (movieList.isEmpty()) {
                            // Seed some data if collection is empty
                            seedMovies();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error getting movies: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void seedMovies() {
        List<Movie> seeds = new ArrayList<>();
        seeds.add(new Movie("1", "Inception", "A thief who steals corporate secrets through the use of dream-sharing technology.", "", "Sci-Fi", 148));
        seeds.add(new Movie("2", "Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", "", "Sci-Fi", 169));
        seeds.add(new Movie("3", "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.", "", "Action", 152));

        for (Movie movie : seeds) {
            db.collection("movies").document(movie.id).set(movie);
        }
        fetchMovies(); // Fetch again after seeding
    }
}