package no.woact.haben16.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnSinglePlayer, btnMultiPlayer, btnLeaderBoard, btnLogout;

    private Login login;
    private FirebaseAuth firebaseAuth;

    public void clickSinglePlayer(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        //Intent intent = new Intent(getApplicationContext(), SinglePlayer.class);
        startActivity(intent);
    }

    public void clickMultiPlayer(View view){
        Intent intent = new Intent(getApplicationContext(), MultiPlayerLogin.class);
        startActivity(intent);
    }

    public void clickLeaderBoard(View view){
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }

    public void clickLogout(View view){
        firebaseAuth.signOut();

        if(firebaseAuth.getCurrentUser() == null){
            View b = findViewById(R.id.btnLogout);
            b.setVisibility(View.GONE);
            Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        btnSinglePlayer = (Button) findViewById(R.id.btnSinglePlayer);
        btnMultiPlayer = (Button) findViewById(R.id.btnMultiPlayer);
        btnLeaderBoard = (Button) findViewById(R.id.btnLeaderBoard);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        if(firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, firebaseAuth.getCurrentUser().getEmail() + " logged in.", Toast.LENGTH_SHORT).show();
        } else{
            View b = findViewById(R.id.btnLogout);
            b.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(firebaseAuth.getCurrentUser() != null) {
            View b = findViewById(R.id.btnLogout);
            b.setVisibility(View.VISIBLE);
            Toast.makeText(this, firebaseAuth.getCurrentUser().getEmail() + " is logged in.", Toast.LENGTH_SHORT).show();
        } else{
            View b = findViewById(R.id.btnLogout);
            b.setVisibility(View.GONE);
        }
    }
}
