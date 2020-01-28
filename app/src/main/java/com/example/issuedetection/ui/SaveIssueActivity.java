package com.example.issuedetection.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;
import java.util.List;
import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

public class SaveIssueActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView mImageProfile;
    private Spinner missuegroup;
    private EditText mDescEdit;
    private Button mUpBtnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_issue);
        initView();
    }

    private void initView() {
        mImageProfile =  findViewById(R.id.profile_image);
        mImageProfile.setOnClickListener(this);
        missuegroup =  findViewById(R.id.bloodgroup);
        initSpinner();
        mDescEdit = findViewById(R.id.edit_desc);
        mUpBtnSign =  findViewById(R.id.sign_up_btn);
        mUpBtnSign.setOnClickListener(this);
    }

    private void initSpinner() {

        List <String> issuelist=new ArrayList<>();
        issuelist.add("الجهه");
        issuelist.add("الجهه");
        issuelist.add("الجهه");
        issuelist.add("الجهه");


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, issuelist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter .setDropDownViewResource(R.layout.spinner_item);
        missuegroup.setAdapter(dataAdapter);

        missuegroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    //showToast("Selected : " + selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                // TODO 20/01/27
                break;
            case R.id.sign_up_btn:
                // TODO 20/01/27
                break;
            default:
                break;
        }
    }
}
