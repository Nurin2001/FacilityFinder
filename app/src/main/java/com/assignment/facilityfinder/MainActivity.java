package com.assignment.facilityfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManagerHome, linearLayoutManagerLocation;
    private MeowBottomNavigation bottomNavigation;
    RelativeLayout locationLayout, homeLayout, profileLayout, loginLayout, logoutLayout;

    Button profileBtn, logoutBtn, editBtn;
    TextView nameTxt;

    TabLayout tabLayout;
    ViewPager viewPager;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        nameTxt = findViewById(R.id.name_txt);

        locationLayout = findViewById(R.id.location_layout);
        homeLayout = findViewById(R.id.home_layout);
        profileLayout = findViewById(R.id.profile_layout);
        loginLayout = findViewById(R.id.login_layout);
        logoutLayout = findViewById(R.id.logout_layout);

        //profileBtn = findViewById(R.id.profileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        editBtn = findViewById(R.id.edit_btn);

        //get instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            if(firebaseAuth.getCurrentUser().getUid()!=null)
                loadName();
        }catch (Exception e) {

        }

        bottomNavigation = findViewById(R.id.bottom_nav_home);

        bottomNavigation.show(2, true);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.location_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.profile_img));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch(model.getId()){
                    case 1:
                        locationLayout.setVisibility(View.VISIBLE);
                        homeLayout.setVisibility(View.GONE);
                        profileLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        locationLayout.setVisibility(View.GONE);
                        homeLayout.setVisibility(View.VISIBLE);
                        profileLayout.setVisibility(View.GONE);
                        break;
                    case 3:
                        locationLayout.setVisibility(View.GONE);
                        homeLayout.setVisibility(View.GONE);
                        profileLayout.setVisibility(View.VISIBLE);
                        try {
                            if(firebaseAuth.getCurrentUser().getUid()!=null){
                                logoutLayout.setVisibility(View.VISIBLE);
                                loginLayout.setVisibility(View.GONE);
                            }
                            else {
                                logoutLayout.setVisibility(View.GONE);
                                loginLayout.setVisibility(View.VISIBLE);
                                tabLayout = findViewById(R.id.tab_layout);
                                viewPager = findViewById(R.id.viewpager);

                                tabLayout.addTab(tabLayout.newTab().setText("Login"));
                                tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
                                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                                tabLayout.setupWithViewPager(viewPager);

                                SigninAdapter signinAdapter = new SigninAdapter(getSupportFragmentManager(), MainActivity.this, tabLayout.getTabCount());
                                signinAdapter.addFrag(new SignInFragment(), "Login");
                                signinAdapter.addFrag(new SignUpFragment(), "Signup");

                                viewPager.setAdapter(signinAdapter);
                            }
                        }catch (Exception e) {

                        }


                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch(model.getId()){
                    case 1:
                        locationLayout.setVisibility(View.VISIBLE);
                        homeLayout.setVisibility(View.GONE);
                        profileLayout.setVisibility(View.GONE);
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch(model.getId()){
                    case 2:
                        locationLayout.setVisibility(View.GONE);
                        homeLayout.setVisibility(View.VISIBLE);
                        profileLayout.setVisibility(View.GONE);
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch(model.getId()){
                    case 3:
                        locationLayout.setVisibility(View.GONE);
                        homeLayout.setVisibility(View.GONE);
//                        profileLayout.setVisibility(View.VISIBLE);
//                        if(firebaseAuth.getCurrentUser().getUid()!=null){
//                            logoutLayout.setVisibility(View.VISIBLE);
//                            loginLayout.setVisibility(View.GONE);
//                        }
//                        else {
//                            logoutLayout.setVisibility(View.GONE);
//                            loginLayout.setVisibility(View.VISIBLE);
//                        }
                        break;
                }
                return null;
            }
        });

        List<ImageAndText> facilityList = getFacilityList();
        List<ImageAndText> locationList = getLocationList();

        RecyclerView homeRecycler = findViewById(R.id.home_recycler);
        RecyclerView locationRecycler = findViewById(R.id.location_recycler);

        linearLayoutManagerHome = new LinearLayoutManager(MainActivity.this);//effect how info is stored in recycler view
        linearLayoutManagerLocation = new LinearLayoutManager(MainActivity.this);//effect how info is stored in recycler view
        homeRecycler.setLayoutManager(linearLayoutManagerHome);
        locationRecycler.setLayoutManager(linearLayoutManagerLocation);

        HomeAdapter homeAdapter = new HomeAdapter(facilityList, MainActivity.this);
        homeRecycler.setAdapter(homeAdapter);
        LocationAdapter locationAdapter = new LocationAdapter(locationList, MainActivity.this);
        locationRecycler.setAdapter(locationAdapter);

//        profileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(MainActivity.this, Signin.class));
//               // onDestroy();
//
//            }
//        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                finish();

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Logout";
                String msg = "Are you sure you want to logout?";
                showAlertDialog(msg, title);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation = findViewById(R.id.bottom_nav_home);

        bottomNavigation.show(2, true);
//        if(firebaseAuth.getCurrentUser().getUid()!=null){
//            loadName();
//        }else {
//            startActivity(new Intent(this, Signin.class));
//        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        bottomNavigation = findViewById(R.id.bottom_nav_home);
//
//        bottomNavigation.show(2, true);
//        homeLayout.setVisibility(View.VISIBLE);
//        loginLayout.setVisibility(View.GONE);
//        if(firebaseAuth.getCurrentUser().getUid()!=null){
//            loadName();
//        }
//    }

    private List<ImageAndText> getLocationList() {
        List<ImageAndText> locationList = new ArrayList<>();

        locationList.add(new ImageAndText("Pusanika", R.drawable.pusanika));

        return locationList;
    }

    private List<ImageAndText> getFacilityList() {
        List<ImageAndText> facilityList = new ArrayList<>();

        facilityList.add(new ImageAndText("Parking Space", R.drawable.parking));
        facilityList.add(new ImageAndText("Accessible Toilet ", R.drawable.parking));
        facilityList.add(new ImageAndText("Elevator ", R.drawable.parking));
        facilityList.add(new ImageAndText("Ramp ", R.drawable.parking));

        return facilityList;
    }

    private void showAlertDialog(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title+"");
        builder.setMessage(msg+"");

        //positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                logoutLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);

            }
        });

        //negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //create and show
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadName() {
        documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            String email = documentSnapshot.getString("email");
                            String name = documentSnapshot.getString("name");

                            //Map<String, Object> data = documentSnapshot.getData();

                            nameTxt.setText("Hello, " + name);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Doc not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG);
                    }
                });
    }
}