package com.umda.poetryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umda.poetryapp.Adapter.PoetryAdapter;
import com.umda.poetryapp.Api.ApiClient;
import com.umda.poetryapp.Api.ApiInterface;
import com.umda.poetryapp.Models.PoetryModel;
import com.umda.poetryapp.Response.GetPoetryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    ApiInterface apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Call the initialization method & getData method
        initialization();
        getData();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Create Method to initialize the views in the activity
    private void initialization() {
        recyclerView = findViewById(R.id.poetry_recyclerview);
        // Create Retrofit Object and Initialize the api interface
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        // Initialize the toolbar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    // Create Method to set the adapter in the recycler view
    private void setAdapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(this, poetryModels);
        //Set the layout manager for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Set the adapter for the recycler view
        recyclerView.setAdapter(poetryAdapter);
    }

    // Create method to call API and get the data
    private void getData() {
        apiInterface.getPoetry().enqueue(new Callback<GetPoetryResponse>() { // Callback method to get the response & return the data from the API
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {
                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {
                            // Set the adapter with the data from the API & Adding the poetry to the recycler view list
                            setAdapter(response.body().getData());
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable throwable) {
                Log.e("Error", throwable.getLocalizedMessage());
            }
        });
    }

    // Create method to inflate the menu in the toolbar & return true
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Create method to handle the click event on the menu item in the toolbar & return true
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_poetry) {
            Intent intent = new Intent(MainActivity.this, AddPoetry.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}