package com.example.issuedetection.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;
import com.example.issuedetection.database.IssueDao;
import com.example.issuedetection.database.model.IssueModel;
import com.example.issuedetection.locationHelper.MyLocationProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Date;

public class SaveIssueActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView mImageIssue;
    private Spinner mIssuegroup;
    private EditText mDescEdit;
    private Button mGelocation , mAddIssue;
    private MyLocationProvider myLocationProvider;
    private Location location;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 600,Request_Camera = 100, Select_Image = 101;
    private Uri imageUri;
    private String image;
    private ProgressDialog progressDialog;
    private StorageReference mImageStorage;
    private double lat=0.0,lng=0.0;
    private   MaterialDialog materialDialog ;
    private boolean getLocation=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_issue);

        initView();

    }

    private void initView() {
        mImageIssue =  findViewById(R.id.issue_image);
        mImageIssue.setOnClickListener(this);
        mIssuegroup =  findViewById(R.id.issuegroup);
        initSpinner();
        mDescEdit =  findViewById(R.id.edit_desc);
        mGelocation =  findViewById(R.id.gelocation);
        mGelocation.setOnClickListener(this);
        mAddIssue =  findViewById(R.id.addIssue);
        mAddIssue.setOnClickListener(this);
    }


    private void initSpinner() {
        List<String> issuelist = new ArrayList<>();
        issuelist.add("الجهه");
        issuelist.add("hjhjhj");
        issuelist.add("hjhjghgh");
        issuelist.add("hjghghgfhgf");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, issuelist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        mIssuegroup.setAdapter(dataAdapter);

        mIssuegroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
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

            case R.id.issue_image:// TODO 20/01/28
                selectImage();
                break;
            case R.id.gelocation:// TODO 20/01/28
                getLocation=true;
              checkPermision();

                break;
            case R.id.addIssue:// TODO 20/01/28
                String des = mDescEdit.getText().toString().trim();
                String issueGroup =mIssuegroup.getSelectedItem().toString().trim();

                if(getLocation==false)
                {  showToast("Location is required");}

                else if (getLocation){
                 lat=location.getLatitude();
                 lng =location.getLongitude();}

                if(isValidForm(des , issueGroup , lat, lng ))
                    saveIssueToFireBase(des,issueGroup,lat,lng);
                break;
            default:
                break;
        }
    }



    private void selectImage() {
        materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.uploadImages)
                .items(R.array.uploadImages)
                .itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which){
                            case 0:

                                Intent intentgallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intentgallery.setType("image/*");
                                if(intentgallery.resolveActivity(getPackageManager())!=null)
                                    startActivityForResult(intentgallery.createChooser(intentgallery, "Select File"), Select_Image);
                                break;
                            case 1:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if(intent.resolveActivity(getPackageManager())!=null)
                                    startActivityForResult(intent, Request_Camera);
                                break;
                            case 2:
                                materialDialog.dismiss();
                                break;
                        }
                    }
                })
                .show();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //auth = FirebaseAuth.getInstance();

        if (requestCode == Select_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);
        }
        if (requestCode == Request_Camera && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                //memoryImage.setImageBitmap(bitmap);
            }

            saveImage(imageUri);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                saveImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                showToast(error+"");
            }
        }
    }




    public void saveImage(Uri resultUri) {
        mImageStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);


        //final StorageReference filepath = mImageStorage.child("profile_images" + auth.getCurrentUser().getUid());
        final StorageReference filepath = mImageStorage.child("profile_images"+"1");
        filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        image = uri.toString();
                        Glide.with(SaveIssueActivity.this)
                                .load(image)
                                .into(mImageIssue);
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("uploading" + (int) prog + "%");
                progressDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Fail"+e.getMessage());
            }
        });
    }





    private void checkPermision() {
        if (isLocationPermissionAllowed()) {//permission granted
            //call your function
            getUserLocation();
        } else { //request runtime permission
            requestLocationPermission();
        }
    }

    private boolean isLocationPermissionAllowed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void getUserLocation() {
        if (myLocationProvider == null)
            myLocationProvider = new MyLocationProvider(this);
        // lw msh 3awaz a listen 3la update ab3t null
        location = myLocationProvider.getCurrentLocation(null);
    }

    private void requestLocationPermission() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            showMessage("this app wants your location ", "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions(SaveIssueActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION
                    );

                }
            }, true);
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getUserLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showToast("can not access location");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }











    private boolean isValidForm(String des, String issueGroup, double lat, double lng) {
        boolean isValid =true;

        if (des.isEmpty()) {
            mDescEdit.setError("input required");
            isValid=false;
        }
        else
            mDescEdit.setError(null);

        if(issueGroup.isEmpty()||"الجهه".equals(issueGroup)){
            showToast("type of issue is required");
            isValid=false;
        }

        if(getLocation==false)
        {
            isValid=false;
        }
        else if (lat==0.0||lng==0.0)
        {
            isValid=false;
        }

      return isValid;
    }















    public void saveIssueToFireBase(String des,String issueGroup , double lat ,double lng) {
        final String currentDateandTime = new SimpleDateFormat("EEE  MMM d, yyyy h:mm a").format(new Date());


        IssueModel issueModel = new IssueModel();
        issueModel.setLat(lat);
        issueModel.setLng(lng);
        issueModel.setDesc(des);
        issueModel.setImageUrl(image);
        issueModel.setDate(currentDateandTime);
        issueModel.setIssueGroup(issueGroup);
        showProgressDialog("please wait...");
        IssueDao.addIsue(issueModel, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressDialog();
                if(task.isSuccessful()){
                    showMessage("issue added succesful ", "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //finish();
                        }
                    },false);
                }
                else
                    showMessage(task.getException().getLocalizedMessage(),"OK");
            }
        });
    }
}
