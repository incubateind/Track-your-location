package com.example.mapsincubate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    public database(Context context){
        super(context,"contacts",null,2);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String table="create table record(id integer primary key,name text,mobile text)";
        sqLiteDatabase.execSQL(table);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){

    }


    public void add_record(String nm,String mb){
        SQLiteDatabase call=this.getWritableDatabase();
        ContentValues collection=new ContentValues();
        collection.put("Name",nm);
        collection.put("Mobile",mb);

        call.insert("record",null,collection);
        call.close();

    }
    public Cursor get_record(){
        SQLiteDatabase call=this.getWritableDatabase();

        String all="select* from record";

        Cursor cursor=call.rawQuery(all,null);
        return cursor;
    }

//    public void update_record(int rl,String nm,int mb,String st){
//        SQLiteDatabase call=this.getWritableDatabase();
//        String update="update record set name='"+nm+"',mobile="+mb+",stream='"+st+"' where roll="+rl+"";
//        call.execSQL(update);
//
//
//
//    }

//    public void del_record(int rl){
//
//        SQLiteDatabase call=this.getWritableDatabase();
//        call.execSQL("delete from record where roll="+rl+"");
//    }


    public void del_record_all(){

        SQLiteDatabase call=this.getWritableDatabase();
        call.execSQL("delete from record");
    }
}
