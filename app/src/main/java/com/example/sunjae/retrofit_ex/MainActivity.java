package com.example.sunjae.retrofit_ex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    RetrofitService retrofitService;
    TextView tv;
    String url = "http://jsonplaceholder.typicode.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv_httptext);

        // Retrofit 초기화
        retrofit = new Retrofit.Builder().baseUrl(url).build();
        retrofitService = retrofit.create(RetrofitService.class);
        Call<ResponseBody> comment = retrofitService.getComment(1);
        //성공했을시와 실패했을시에 대한 처리
        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    tv.setText(response.body().string().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tv.setText(t.toString());
            }
        });

    }
}

interface RetrofitService{
    @GET("comments")
    //JSON에서 가져올 데이터 메서드
    //@Query("변수이름"), 보낼 변수
    Call<ResponseBody>getComment(@Query("postId") int postId);
}
