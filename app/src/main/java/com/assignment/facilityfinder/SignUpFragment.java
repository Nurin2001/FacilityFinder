package com.assignment.facilityfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    EditText emailEt, passEt, nameEt;
    Button signupBtn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    String UID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        emailEt = root.findViewById(R.id.emails_et);
        passEt = root.findViewById(R.id.passs_et);
        nameEt = root.findViewById(R.id.names_et);

        signupBtn = root.findViewById(R.id.signup_btn);

        //get current instance of db from firebase to perform operation on db
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        //if someone already logged in
//        if(firebaseAuth.getCurrentUser()!=null) {
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            intent.putExtra("UID", firebaseAuth.getCurrentUser().getUid());
//            startActivity(intent);
//            //onDestroyView();
//        }

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString().trim();
                String pass = passEt.getText().toString().trim();
                String name = nameEt.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    emailEt.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)) {
                    passEt.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(name)) {
                    nameEt.setError("Name is required");
                    return;
                }
                if(pass.length() < 6){
                    passEt.setError("Password must be longer than 6 characters");
                }

                //REGISTER USER
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Account has been registered.", Toast.LENGTH_LONG).show();
                                    UserDetail userDetail = new UserDetail(email, name);
                                    UID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("users").document(UID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", userDetail.getEmail());
                                    user.put("name", userDetail.getName());

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("TAG", "onSuccess" + UID);
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            //intent.putExtra("UID", firebaseAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getContext(), "Fail to register." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    //progressBar.setVisibility(View.GONE);

                                }

                            }
                        });
            }
        });

        return root;
    }
}
