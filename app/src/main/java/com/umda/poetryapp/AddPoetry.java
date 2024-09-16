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
import com.umda.poetryapp.Response.DeletePoetryResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText poetName, poetry;
    AppCompatButton submit_btn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_poetry);
        // Call the initialization method
        initialization();
        // Set the click listener for the submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poetryData = poetry.getText().toString();
                String poetNameData = poetName.getText().toString();
                if (poetryData.equals("")) {
                    poetry.setError("Field is Empty");
                } else {
                    if (poetNameData.equals("")) {
                        poetName.setError("Field is Empty");
                    } else {
                        callApi(poetryData, poetNameData);
                    }
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Create Method to initialize the views in the activity & set the toolbar as the action bar
    private void initialization() {
        toolbar = findViewById(R.id.add_poetry_toolbar);
        // Set the toolbar as the action bar for the activity & set the back button in the toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        poetry = findViewById(R.id.add_poetry_edittext);
        poetName = findViewById(R.id.add_poet_name_edittext);
        submit_btn = findViewById(R.id.submit_btn);
        // Create Retrofit Object and Initialize the api interface
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    // Create method to call API and add the data to the database
    private void callApi(String poetry, String poet_name) {
        apiInterface.addPoetry(poetry, poet_name).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(AddPoetry.this, "Poetry Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddPoetry.this, "Poetry Not Added Successfully", Toast.LENGTH_SHORT).show();
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