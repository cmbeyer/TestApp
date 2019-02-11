package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.*;

public class MainActivity extends AppCompatActivity {


    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private TextView Register;
    private int counter = 5;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        Login = (Button)findViewById(R.id.button_id);
        Info = (TextView)findViewById(R.id.tvInfo);
        Register = (TextView)findViewById(R.id.register);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new AsyncCaller().execute();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });


    }

    private class AsyncCaller extends AsyncTask<Void, Void, Boolean>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con=DriverManager.getConnection("jdbc:mysql://69.142.191.249:3306/WebUsers","root","14752369Cb");
                Statement stmt=con.createStatement();

                ResultSet rs=stmt.executeQuery("Select * from UserName where userName='"+Name.getText().toString()+"' and pass='"+Password.getText().toString()+"'");
                if(rs.next())
                {
                    check=true;
                }
                else
                {
                    check = false;
                }
                validate();
            }catch(Exception e){ System.out.println(e);}

            return check;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
        }

    }
    private void validate(){
        if(check){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            counter--;

            Info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }
}
}
