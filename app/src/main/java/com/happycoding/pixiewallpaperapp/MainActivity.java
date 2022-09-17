package com.happycoding.pixiewallpaperapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageModel>modelArrayList;
    private RecyclerView recyclerView;
    private Adapter adapter;

     Button btn_nature,btn_trend,btn_dog,btn_sky,btn_texture,btn_Ocean,btn_flower;
     EditText editText;
     ImageButton search;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        recyclerView=findViewById(R.id.recyclerView);
        editText=findViewById(R.id.etSearchBar);
        search=findViewById(R.id.searchButton);
        btn_Ocean=findViewById(R.id.btn_Ocean);
        btn_dog=findViewById(R.id.btn_dog);
        btn_sky=findViewById(R.id.btn_sky);
        btn_flower=findViewById(R.id.btn_flower);
        btn_texture=findViewById(R.id.btn_texture);
        btn_nature=findViewById(R.id.btn_nature);
        btn_trend=findViewById(R.id.btn_trend);








        modelArrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapter=new Adapter(getApplicationContext(),modelArrayList);
        recyclerView.setAdapter(adapter);

        findPhotos();

        btn_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="nature";
                getSearchImage(query);
            }
        });
        btn_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhotos();
            }
        });
        btn_texture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="texture";
                getSearchImage(query);
            }
        });
        btn_Ocean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="Ocean";
                getSearchImage(query);
            }
        });
        btn_dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="dog";
                getSearchImage(query);
            }
        });
        btn_sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="sky";
                getSearchImage(query);
            }
        });
        btn_flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="flowers";
                getSearchImage(query);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=editText.getText().toString().trim().toLowerCase();
                if(query.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else {
                    getSearchImage(query);
                }
            }
        });
    }

    private void getSearchImage(String query) {

        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                modelArrayList.clear();
                if(response.isSuccessful()){
                    modelArrayList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not able to fetch", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });

    }

    private void findPhotos() {

        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                if(response.isSuccessful()){
                    modelArrayList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not able to fetch", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });

    }
}
