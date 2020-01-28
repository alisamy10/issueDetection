package com.example.issuedetection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.issuedetection.Adapter.PagerAdapter;
import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;
import com.example.issuedetection.helper.IssueAlramReciever;
import com.example.issuedetection.locationHelper.MyLocationProvider;
import com.example.issuedetection.ui.Fragment.AddNewEvent;
import com.example.issuedetection.ui.Fragment.History;
import com.example.issuedetection.ui.Fragment.HomeFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity implements LocationListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    PagerAdapter pagerAdapter;

    private MyLocationProvider myLocationProvider;
    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.mTabLayout);
        viewPager=findViewById(R.id.viewpager);
        pagerAdapter =new PagerAdapter(getSupportFragmentManager(),0);
        pagerAdapter.addFragment(new History(),"الذاكره");
        pagerAdapter.addFragment(new AddNewEvent(),"اضافه حدث");
        pagerAdapter.addFragment(new HomeFragment(),"الصفحه الرئسيه");



        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);






    }


    @Override
    protected void onStart() {
        super.onStart();


        Intent serviceIntent = new Intent(this, IssueAlramReciever.class);
        serviceIntent.putExtra("inputExtra", "desss");

        ContextCompat.startForegroundService(this, serviceIntent);
        //pushNotifaction();
        //mapView.onStart();

       // mapView.getMapAsync(this);

        //allNotes = NoteDataBase.getInstance(getApplicationContext()).notesDao().getAllNotes();

        //Log.e("k",tagForNotification+"  start");
        //Toast.makeText(this, allNotes.size()+"", Toast.LENGTH_SHORT).show();


    }

    private void getUserLocation() {
        if (myLocationProvider == null)
            myLocationProvider = new MyLocationProvider(this);
        // lw msh 3awaz a listen 3la update ab3t null
        location = myLocationProvider.getCurrentLocation(this);
        ///kjjk
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void pushNotifaction(){
/*        for (int i = 0; i < allNotes.size(); i++) {


            if (distance(allNotes.get(i).getLat(), allNotes.get(i).getLng(), location.getLatitude(), location.getLongitude()) * 1000 <= 400) {

                Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);

            }

        }
        freezNotificationOneMin();

 */
    }

}
