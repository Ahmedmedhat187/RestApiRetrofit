package com.ahmed.restapiretrofit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BodyActivity extends AppCompatActivity {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    Retrofit mRetrofit;
    ApiInterface mApiInterface;
    Call<List<Comment>> callComment;

    RecyclerView mRecyclerView;
    CommentAdapter mCommentAdapter;
    CardView cardComments;

    ProgressDialog progressDialog;
    TextView tvBody , tvShowComments;
    String body = "";
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading Comments...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        Intent intent = getIntent();
        if(intent != null ){
            body = intent.getStringExtra(MainActivity.EXTRA_BODY);
            id = intent.getStringExtra(MainActivity.EXTRA_ID );
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiInterface = mRetrofit.create(ApiInterface.class);

        tvBody = findViewById(R.id.tv_body);
        tvBody.setText(Html.fromHtml("<b> Post: </b>" + body));
        tvShowComments = findViewById(R.id.tv_show_comments);
        tvShowComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComments(id);
            }});

        cardComments  = findViewById(R.id.card_comments);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mCommentAdapter = new CommentAdapter(this );
        mRecyclerView.setAdapter(mCommentAdapter);
    }




    public void getComments(String id){
        progressDialog.show();
        callComment  = mApiInterface.getComments(id);
        callComment.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(BodyActivity.this, "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.dismiss();
                cardComments.setVisibility(View.VISIBLE);
                mCommentAdapter.setCommentLists(response.body());
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BodyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
