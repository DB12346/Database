package com.example.my_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
   TextView pass,id,no,email,t1,t2,t3,t4;
   Button send_money;
   String usid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main4);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String temp=" ";
        int count=0;


        pass =  findViewById(R.id.textview9_pass);
        id =findViewById(R.id.user_id);
        no=findViewById(R.id.textView_no);
        email=findViewById(R.id.textView6);
        send_money=findViewById(R.id.send_moneyid);
        for(int i=0;i<message.length();i++)
        {
            if(message.charAt(i)=='/')
            {
                if(count==0)
                {
                    usid=temp;
                    id.setText("ID -"+temp);
                }
                if(count==1)
                {
                    pass.setText("pass- "+temp);
                }
                if(count==2)
                {
                    email.setText("email- "+temp);
                }

                if(count==3)
                {
                    no.setText("Amount-"+temp);
                }
                count++;
                temp=" ";
            }
            else
            temp=temp+message.charAt(i);
        }
        send_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this,sending_money.class);
                intent.putExtra("message",message);
                startActivity(intent);

            }
        });


    }
}