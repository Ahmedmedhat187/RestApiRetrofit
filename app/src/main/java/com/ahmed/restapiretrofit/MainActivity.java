package com.ahmed.restapiretrofit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements PostAdapter.ItemClickListener{

    private static final String TAG = "MainActivity";
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static String EXTRA_BODY = "body";
    public static String EXTRA_ID = "id";
    Retrofit mRetrofit;
    ApiInterface mApiInterface;
    Call<List<Post>> callpost;

    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    PostAdapter mPostAdapter;
    Button btnPosts , btnPost;
    Spinner mSpinnerUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading Posts...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        mSpinnerUserId  = findViewById(R.id.spinner_userid);

        btnPosts = findViewById(R.id.btn_posts);
        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPosts();
            }});
        btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPostsOfUser();
            }});

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mPostAdapter = new PostAdapter(this , this);
        mRecyclerView.setAdapter(mPostAdapter);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiInterface = mRetrofit.create(ApiInterface.class);

    }


    public void getPosts(){
        progressDialog.show();
        callpost  = mApiInterface.getPosts();
        callpost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.dismiss();
                mPostAdapter.setPostList(response.body());
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getPostsOfUser(){
        progressDialog.show();
        String userId = mSpinnerUserId.getSelectedItem().toString();
        Map<String , String> parameters = new HashMap<>();
        parameters.put("userId" , userId);
        parameters.put("_sort" , "id");
        parameters.put("_order" , "asc");

        callpost  = mApiInterface.getPosts(parameters);
        callpost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.dismiss();
                mPostAdapter.setPostList(response.body());
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(this , BodyActivity.class);
        intent.putExtra(EXTRA_BODY , mPostAdapter.getPostList().get(itemId).getTopic());
        intent.putExtra(EXTRA_ID , mPostAdapter.getPostList().get(itemId).getId().toString());
        Log.e(TAG ,  mPostAdapter.getPostList().get(itemId).getId());
        startActivity(intent);
    }


}
