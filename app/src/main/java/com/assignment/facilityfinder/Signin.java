package com.assignment.facilityfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

public class Signin extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_signin);
        //FirebaseApp.initializeApp(this);


//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager = findViewById(R.id.viewpager);
//
//        tabLayout.addTab(tabLayout.newTab().setText("Login"));
//        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        SigninAdapter signinAdapter = new SigninAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
//        signinAdapter.addFrag(new SignInFragment(), "Login");
//        signinAdapter.addFrag(new SignUpFragment(), "Signup");
//
//        viewPager.setAdapter(signinAdapter);

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}