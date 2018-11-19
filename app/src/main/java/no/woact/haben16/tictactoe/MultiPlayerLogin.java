package no.woact.haben16.tictactoe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MultiPlayerLogin extends AppCompatActivity {

    TextView txtHeader;
    EditText playerOne, playerTwo;
    Button btnLogin;

    MyDBHandler dbHandler;

    public void loginClick(View view){
        String strOne = playerOne.getText().toString();
        String strTwo = playerTwo.getText().toString();

        if(TextUtils.isEmpty(strOne)){
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(strTwo)){
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        } else{

            String value=strOne;
            String valueTwo=strTwo;

            Intent i = new Intent(MultiPlayerLogin.this, MultiPlayer.class);
            i.putExtra("playerOneValue",value);
            i.putExtra("playerTwoValue",valueTwo);
            startActivity(i);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_login);

        dbHandler = new MyDBHandler(this);

        txtHeader = (TextView) findViewById(R.id.txtHeader);
        playerOne = (EditText) findViewById(R.id.playerOne);
        playerTwo = (EditText) findViewById(R.id.playerTwo);
        btnLogin  = (Button) findViewById(R.id.btnLogin);

    }
}
