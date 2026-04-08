package com.example.firebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.models.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

public class BookingActivity extends AppCompatActivity {

    private TextView tvMovie;
    private Spinner spTheater, spShowtime;
    private EditText etSeat;
    private Button btnConfirm;
    private String movieId, movieTitle;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        movieId = getIntent().getStringExtra("movieId");
        movieTitle = getIntent().getStringExtra("movieTitle");

        tvMovie = findViewById(R.id.tvBookingMovie);
        spTheater = findViewById(R.id.spTheater);
        spShowtime = findViewById(R.id.spShowtime);
        etSeat = findViewById(R.id.etSeat);
        btnConfirm = findViewById(R.id.btnConfirmBooking);

        tvMovie.setText("Booking for: " + movieTitle);

        // Mock data for spinners
        String[] theaters = {"CGV Vincom", "Lotte Cinema", "BHD Star"};
        String[] showtimes = {"10:00 AM", "01:30 PM", "04:45 PM", "08:00 PM"};

        ArrayAdapter<String> theaterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theaters);
        theaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTheater.setAdapter(theaterAdapter);

        ArrayAdapter<String> showtimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, showtimes);
        showtimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spShowtime.setAdapter(showtimeAdapter);

        btnConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        String seat = etSeat.getText().toString().trim();
        if (TextUtils.isEmpty(seat)) {
            etSeat.setError("Enter seat number");
            return;
        }

        String theater = spTheater.getSelectedItem().toString();
        String showtimeStr = spShowtime.getSelectedItem().toString();
        String userId = mAuth.getCurrentUser().getUid();
        String ticketId = UUID.randomUUID().toString();

        Ticket ticket = new Ticket(
                ticketId,
                userId,
                "showtime_id_placeholder",
                movieTitle,
                theater,
                new Date(), // Use current date for simplicity in this exercise
                seat,
                75000.0
        );

        db.collection("tickets").document(ticketId).set(ticket)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(BookingActivity.this, "Ticket booked and saved to Firebase!", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookingActivity.this, "Failed to book ticket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}