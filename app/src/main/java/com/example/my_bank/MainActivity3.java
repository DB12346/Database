package com.example.my_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity3 extends AppCompatActivity {
EditText usser_id,pass,email,phone;
Button next;
String id,password,email_id,no;
String output="100";
        String in;
    private Socket socket            = null;
    private DataInputStream input   = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        usser_id=findViewById(R.id.usid);
        pass=findViewById(R.id.editTextTextPassword);
        email=findViewById(R.id.editTextTextEmailAddress);
        phone=findViewById(R.id.editTextPhone);
        next=findViewById(R.id.button4);
       next.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               if(output.equals("-1"))
               {
                   Toast toast = Toast.makeText(getApplicationContext(), "user id already taken", Toast.LENGTH_SHORT);
                   toast.show();
               }
               if(output.equals("1"))
               {
                   Toast toast = Toast.makeText(getApplicationContext(), "thank you for registration", Toast.LENGTH_SHORT);
                   toast.show();
               }
               id=usser_id.getText().toString();
               password=pass.getText().toString();
               email_id=email.getText().toString() ;
               no=phone.getText().toString();
                in=id+'/'+password+'/'+email_id+'/'+no;


               Thread thread = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try  {
                           String address="192.168.0.106";
                           int port=49199;
                           output=connect_with_data_base(address,port,in);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }

               });
               //  System.out.println(ser+"outside_the_func");
               thread.start();



           }
       });
    }

    String  connect_with_data_base(String address,int port,String input)
    {
        System.out.println(address);
        System.out.println(port);
        String func_out="-1";
     //   String input=id+"/"+pass;
        DataOutputStream out=null ;
        try
        {
            System.out.println("in the try");
            socket = new Socket(address, port);
            System.out.println("Connected");
            // takes input from terminal
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println("in the Unknown");
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println("in the IOEX");
            System.out.println(i);
        }
        try
        {
            out.writeUTF(input);
        }
        catch(IOException i) {
            System.out.println(i);
        }
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            InputStream is =socket.getInputStream();
            pw.println();
            pw.flush();
            byte[] buffer = new byte[1024];
            int read;
            while((read = is.read(buffer)) != -1) {
                func_out = new String(buffer, 0, read);
                System.out.print(func_out);
                System.out.flush();
                break;
            };
            // System.out.println("came_out_side");
            socket.close();

        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i) {
            System.out.println(i);
        }
        return func_out;
    }

}