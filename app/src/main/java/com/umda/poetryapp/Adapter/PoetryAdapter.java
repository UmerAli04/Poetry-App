package com.umda.poetryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.umda.poetryapp.Api.ApiClient;
import com.umda.poetryapp.Api.ApiInterface;
import com.umda.poetryapp.Models.PoetryModel;
import com.umda.poetryapp.R;
import com.umda.poetryapp.Response.DeletePoetryResponse;
import com.umda.poetryapp.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {
    Context context;
    List<PoetryModel> poetryModels;
    ApiInterface apiInterface;

    // Constructor to initialize the context and data list
    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        // Create Retrofit object and initialize the API interface
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView and create a ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder based on the position in the data list
        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.poetry_date.setText(poetryModels.get(position).getDate_time());
        // Set click listeners for the delete button to delete the poetry from the API
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePoetry(poetryModels.get(position).getId() + "", position);
            }
        });
        // Set click listeners for the update button to update the poetry in the API
        holder.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, poetryModels.get(position).getId() + "\n" + poetryModels.get(position).getPoetry_data(), Toast.LENGTH_SHORT).show();
                // Start the UpdatePoetry activity and pass the poetry data to it for updating in the API
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id", poetryModels.get(position).getId());
                intent.putExtra("p_data", poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the data list
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poetName, poetry, poetry_date;
        AppCompatButton update_btn, delete_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            poetName = itemView.findViewById(R.id.poetname_textview);
            poetry = itemView.findViewById(R.id.poetry_textview);
            poetry_date = itemView.findViewById(R.id.poetry_date_textview);

            update_btn = itemView.findViewById(R.id.update_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }

    // Create method to delete poetry from the API and update the UI accordingly
    private void deletePoetry(String id, int position) {
        apiInterface.deletePoetry(id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if (response.body().getStatus().equals("1")) {
                            poetryModels.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, poetryModels.size());
//                            notifyDataSetChanged();
                        }
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