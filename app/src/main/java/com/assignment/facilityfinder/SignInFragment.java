package com.assignment.facilityfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {

    EditText emailEt, passEt;
    Button loginBtn;

    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signin_tab_fragment, container, false);

        FirebaseApp.initializeApp(getActivity());


        emailEt = root.findViewById(R.id.email_et);
        passEt = root.findViewById(R.id.pass_et);
        loginBtn = root.findViewById(R.id.login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        //if someone already logged in
        if(firebaseAuth.getCurrentUser()!=null) {
            String email="";
            email = emailEt.getText().toString().trim();
            startActivity(new Intent(getActivity(), MainActivity.class));
            //onDestroyView();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString().trim();
                String pass = passEt.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    emailEt.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)) {
                    passEt.setError("Email is required");
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                //AUTHENTICATE USE
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                   // getActivity()
                                    //        .finish();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                                else {
                                    Toast.makeText(getActivity(), "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getActivity(), MainActivity.class));
//                                    forgotpasstv.setVisibility(View.VISIBLE);
//                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
              /*  forgotpasstv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
                        final EditText resetmail = new EditText(view.getContext());
                        final AlertDialog.Builder passwordresetdialog = new AlertDialog.Builder(view.getContext());
                        passwordresetdialog.setTitle("Reset Password");
                        passwordresetdialog.setMessage("Enter Your Email to Receive Reset Link");
                        passwordresetdialog.setView(resetmail);

                        passwordresetdialog.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //extract email and send reset link
                                        String email = resetmail.getText().toString().trim();
                                        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getActivity(), "Reset link is sent to your email", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        passwordresetdialog.create().show();
                    }

                });*/
            }
        });

        emailEt.setTranslationX(800);
        passEt.setTranslationX(800);
        loginBtn.setTranslationX(800);

        emailEt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passEt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }

}
