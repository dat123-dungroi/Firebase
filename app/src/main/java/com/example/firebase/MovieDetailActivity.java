package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvGenre, tvDescription;
    private Button btnBook;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        ivPoster = findViewById(R.id.ivDetailPoster);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvGenre = findViewById(R.id.tvDetailGenre);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnBook = findViewById(R.id.btnGoToBooking);

        if (movie != null) {
            tvTitle.setText(movie.title);
            tvGenre.setText(movie.genre);
            tvDescription.setText(movie.description);
            // In a real app, use Glide to load ivPoster
        }

        btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(MovieDetailActivity.this, BookingActivity.class);
            intent.putExtra("movieId", movie.id);
            intent.putExtra("movieTitle", movie.title);
            startActivity(intent);
        });
    }
}