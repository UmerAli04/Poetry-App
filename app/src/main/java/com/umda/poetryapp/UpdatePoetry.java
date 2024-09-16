package com.umda.poetryapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.umda.poetryapp.Api.ApiClient;
import com.umda.poetryapp.Api.ApiInterface;
import com.umda.poetryapp.Models.PoetryModel;
import com.umda.poetryapp.Response.DeletePoetryResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText poetry;
    AppCompatButton submitBtn;
    int poetryId;
    String poetryData;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_poetry);
        initialization();
        // Set the click listener for the submit button to update the poetry
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_data = poetry.getText().toString();
                if (p_data.equals("")) {
                    poetry.setError("Field is Empty");
                } else {
                    updatePoetry(p_data, poetryId + "");
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Create Method to initialize the views in the activity & set the toolbar in the activity
    private void initialization() {
        toolbar = findViewById(R.id.update_poetry_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        poetry = findViewById(R.id.update_poetry_edittext);
        submitBtn = findViewById(R.id.update_submit_btn);
        // Get the poetry id & poetry data from the intent
        poetryId = getIntent().getIntExtra("p_id", 0);
        poetryData = getIntent().getStringExtra("p_data");
        poetry.setText(poetryData);
        // Create Retrofit object and initialize the API
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    // Create method to update the poetry
    private void updatePoetry(String pData, String pId) {
        apiInterface.updatePoetry(pData, pId).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(UpdatePoetry.this, "Poetry Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdatePoetry.this, "Poetry not Updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable throwable) {
                Log.e("Error", throwable.getLocalizedMessage());
            }
        });
    }
}