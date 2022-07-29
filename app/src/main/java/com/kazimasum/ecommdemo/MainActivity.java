package com.kazimasum.ecommdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    TextView tv;
    EditText loginemail, loginpassword;
    TextView loginreport;
    Button loginbtn, skipbtn;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loginemail = findViewById(R.id.login_email);
        loginpassword = findViewById(R.id.login_password);
        loginreport=findViewById(R.id.login_report);
        loginbtn=(Button) findViewById(R.id.login_submit);
        skipbtn=(Button) findViewById(R.id.login_skip);



        verifyuserexistence();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processlogin(loginemail.getText().toString(), loginpassword.getText().toString());
            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),udashboard.class));
            }
        });

        tv=(TextView)findViewById(R.id.login_tv);
            tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      startActivity(new Intent(getApplicationContext(),register.class));
                      finish();
                    }
                });
    }



    public void processlogin(String loginemail, String loginpassword){
        Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getlogin(loginemail,loginpassword);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("exist"))
                {
                    SharedPreferences sp =getSharedPreferences("credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("mobile", loginemail);
                    editor.putString("password", loginpassword);
                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                    finish();



                }
                if(result.equals("notexist"))
                {
                    loginreport.setText("Invalid Mobile/Password");
                    loginreport.setTextColor(Color.RED);


                }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                loginreport.setText("Something went wrong");
                loginreport.setTextColor(Color.RED);

            }
        });

    }

    public void verifyuserexistence(){
        SharedPreferences sp=getSharedPreferences("credentials", MODE_PRIVATE);
        if(sp.contains("mobile")){
            startActivity(new Intent(getApplicationContext(), dashboard.class));
        }
        else{

        }
    }

}