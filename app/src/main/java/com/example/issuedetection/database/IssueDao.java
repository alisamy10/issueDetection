package com.example.issuedetection.database;

import com.example.issuedetection.database.model.IssueModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;

import java.lang.ref.Reference;

public class IssueDao {
    public static void addIsue(IssueModel isue, OnCompleteListener<Void> onCompletionListener){
        DatabaseReference document =MyDataBase.getNoteReference();
        isue.setId(document.push().getKey());
        document.setValue(isue).addOnCompleteListener(onCompletionListener);

    }

}
