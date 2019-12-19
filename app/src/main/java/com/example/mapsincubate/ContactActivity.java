package com.example.mapsincubate;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    static final int PICK_CONTACT=1;
    database db;
    ListView l;
    ArrayList<String>m,n;
    int tap=0;
    GetLoader gl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        l=(ListView)findViewById(R.id.lv);

        n = new ArrayList<>();
        m = new ArrayList<>();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contacts");


        db = new database(getApplicationContext());
//


        try {
            Cursor record = db.get_record();
            if (record.getCount() == 0) {
                Toast.makeText(this, "No Contacts", Toast.LENGTH_SHORT).show();
            }






            while (record.moveToNext()) {


                n.add(record.getString(1));
                m.add(record.getString(2));


                GetLoader gl = new GetLoader(ContactActivity.this,n,m);
                l.setAdapter(gl);
            }
        }catch (Exception ty){
            Toast.makeText(getApplicationContext(),"ErrorDisplay "+ty,Toast.LENGTH_SHORT).show();
        }




    }

    public boolean  onCreateOptionsMenu(Menu menu) {
        menu.add("Add Contact");
        menu.add("Remove All Contacts");
        menu.add("Close");



        // getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String title = (String)item.getTitle();
        //int id= item.getItemId();
        if(title.equals("Add Contact")){
            tap+=1;
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, PICK_CONTACT);
        }
        if(title.equals("Remove All Contacts")){

            final AlertDialog.Builder builder=new AlertDialog.Builder(ContactActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Delete All Contacts?");
            builder.setMessage("Are you sure you want to Delete all the Contacts from the List?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    builder.setCancelable(true);
                    try{

                        db.del_record_all();
                        Toast.makeText(ContactActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                        n=new ArrayList<>();
                        m=new ArrayList<>();
                        l.setAdapter(null);
                        tap=0;

                    }catch (Exception td){
                        Toast.makeText(ContactActivity.this,"Error"+td,Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                builder.setCancelable(true);
                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();
        }

        if(title.equals("Close")){
            Intent i = new Intent(ContactActivity.this,MainActivity.class);
            startActivity(i);
        }
        // if(id==R.id.m4){
        //    Toast.makeText(MainActivity.this,"Call menu-4",Toast.LENGTH_SHORT).show();
        // }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null ,nm,mb;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        int  phoneName =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        name=cursor.getString(phoneName);
                        Toast.makeText(ContactActivity.this,"Name "+name+"\nPhone "+phoneNo,Toast.LENGTH_SHORT).show();
                        nm=name;
                        mb=phoneNo;

//                        db = new database(ContactActivity.this);
                        try {


                            db.add_record(nm,mb);
                            Toast.makeText(ContactActivity.this,"Entry Successful",Toast.LENGTH_SHORT).show();
                        }catch(Exception t){
                            Toast.makeText(ContactActivity.this,"Error"+t,Toast.LENGTH_SHORT).show();
                        }

                        db = new database(ContactActivity.this);
//


                        try {
                            Cursor record = db.get_record();
                            if (record.getCount() == 0) {
                                Toast.makeText(this, "Record not found", Toast.LENGTH_SHORT).show();
                            }


                                gl=null;
//                                l.setAdapter(null);
                                n=new ArrayList<>();
                                m=new ArrayList<>();





                            while (record.moveToNext()) {


                                n.add(record.getString(1));
                                m.add(record.getString(2));


                                GetLoader gl = new GetLoader(ContactActivity.this,n,m);
                                l.setAdapter(gl);
                            }
                        }catch (Exception ty){
                            Toast.makeText(getApplicationContext(),"ErrorDisplay "+ty,Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

}
