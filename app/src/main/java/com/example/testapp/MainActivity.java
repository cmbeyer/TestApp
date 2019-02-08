package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import java.sql.*;

public class MainActivity extends AppCompatActivity  {

    
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
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

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void connectDB(String userName, String userPassword){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con=DriverManager.getConnection("jdbc:mysql://69.142.191.249:3306/WebUsers","root","14752369Cb");
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("Select * from UserName where userName='"+userName+"' and pass='"+userPassword+"'");
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
