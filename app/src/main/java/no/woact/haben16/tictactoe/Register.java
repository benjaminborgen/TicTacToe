package no.woact.haben16.tictactoe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText emailTxt, passwordTxt;
    // EditText nicknameTxt;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;


    public void clickRegister(View view){
        registerUser();
    }

    private void registerUser(){
        String email = emailTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        //String nickname = nicknameTxt.getText().toString().trim();

        //User userName = new User(nickname);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //databaseReference.child(user.getUid()).setValue(userName);

        Toast.makeText(this, "Information stored...", Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } /*else if (TextUtils.isEmpty(nickname)){
            Toast.makeText(this, "Please enter nickname", Toast.LENGTH_SHORT).show();
            return;
        }*/

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                } else{
                                    Toast.makeText(Register.this, "Failed to register, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        emailTxt    = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        //nicknameTxt = findViewById(R.id.nicknameTxt);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}
