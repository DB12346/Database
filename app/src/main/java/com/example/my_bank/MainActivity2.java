package com.example.my_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


  //class connect_to_server extends AsyncTask<String,>

public class MainActivity2 extends AppCompatActivity {

    private Socket socket            = null;
    private DataInputStream input   = null;
    //private DataOutputStream out     = null;
    public String id,pass;
    String ser="100";
    //String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


           setContentView(R.layout.activity_main2);


        Button enter=findViewById(R.id.button3);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  System.out.println(" most_ou"+ser);
                EditText iti = findViewById(R.id.ID);
                id = iti.getText().toString();
                EditText new_i = findViewById(R.id.password);
                pass = new_i.getText().toString();


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            String address="192.168.0.106";
                            int port=49199;
                             ser=connect_with_data_base(address,port,id,pass);
                             if(ser.length()>10)
                             {
                                 Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
                                 intent.putExtra("message",ser);
                                 startActivity(intent);
                             }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
              //  System.out.println(ser+"outside_the_func");
                thread.start();

                if(ser.equals("-1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "please_register", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(ser.equals("2")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), ser, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
   String  connect_with_data_base(String address,int port,String id,String pass)
    {
        System.out.println(address);
        System.out.println(port);
        String output="-1";
        String input=id+"/"+pass;
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
                 output = new String(buffer, 0, read);
                System.out.print(output);
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
        return output;
    }

}