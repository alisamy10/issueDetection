package com.example.issuedetection.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDataBase {

    private static FirebaseDatabase myDataBase;
    public static final String ISUE_REF="issue";

    public static FirebaseDatabase getInsatnce(){

        if(myDataBase==null)
            myDataBase=FirebaseDatabase.getInstance();
        return  myDataBase;

    }



    public static DatabaseReference getNoteReference(){
        return getInsatnce().getReference(ISUE_REF);
    }



}
