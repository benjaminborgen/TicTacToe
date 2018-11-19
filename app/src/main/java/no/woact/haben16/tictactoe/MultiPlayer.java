package no.woact.haben16.tictactoe;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MultiPlayer extends AppCompatActivity {


    private int player = 0, roundCounter = 1;
    private int playerOneScore = 0, playerTwoScore = 0;
    int btnPushed;
    private boolean gameIsLive = true;
    private boolean oddOrEven = true;

    String value;
    String valueTwo;

    //
    int[] unPlayed = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winMap = {{3, 4, 5}, {2, 4, 6}, {0, 3, 6}, {6, 7, 8}, {0, 1, 2}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}};

    Button btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnReset;
    TextView txtHeader, txtPlayerOne, txtScorePlayerOne, txtBot, txtScoreBot;
    MyDBHandler dbHandler;

    public void clickOn(View view){
        Button btn = (Button) findViewById(view.getId());
        btnPushed = Integer.parseInt(btn.getTag().toString());

        if (unPlayed[btnPushed] == 2 && gameIsLive) {

            unPlayed[btnPushed] = player;
            setPlayerMove(btn);

            for (int[] winMaps : winMap) {
                if (unPlayed[winMaps[0]] == unPlayed[winMaps[1]] &&
                        unPlayed[winMaps[1]] == unPlayed[winMaps[2]] &&
                        unPlayed[winMaps[0]] != 2) {

                    gameIsLive = false;
                    String winner = "getName";

                    if (unPlayed[winMaps[0]] == 0) {

                        winner = "getName2";
                        playerTwoScore++;
                        setPlayerTwoScore(playerTwoScore);
                    }else{
                        playerOneScore++;
                        setPlayerOneScore(playerOneScore);
                    }

                    Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            boolean gameOver = true;

            for (int counter : unPlayed) {
                if (counter == 2) {
                    gameOver = false;
                }
            }
            if (gameOver) {
                Toast.makeText(this, "The game is draw.", Toast.LENGTH_SHORT).show();
            }
        }
        Log.i("Info", btn.toString() + " has been clicked");
    }

    public void setPlayerMove(Button btn){

        if(player == 0 && oddOrEven == true){
            btn.setText("X");
            player = 1;
            System.out.println("Button stored: " + btn);
        } else if(player == 0 && oddOrEven == false){
            btn.setText("O");
            player = 1;
        } else if(player == 1 && oddOrEven == true){
            btn.setText("O");
            player = 0;
            System.out.println("Button stored: " + btn);
        } else if(player == 1 && oddOrEven == false){
            btn.setText("X");
            player = 0;
            System.out.println("Button stored: " + btn);
        }
    }



    public void evenOrOdd(){
        if((roundCounter % 2) == 0){
            player = 1;
            oddOrEven = false;
        } else {
            player = 0;
            oddOrEven = true;
        }
    }

    public void resetButtons(View view) {

        btnZero.setText("");
        btnOne.setText("");
        btnTwo.setText("");
        btnThree.setText("");
        btnFour.setText("");
        btnFive.setText("");
        btnSix.setText("");
        btnSeven.setText("");
        btnEight.setText("");

        for (int i = 0; i < unPlayed.length; i++) {
            unPlayed[i] = 2;
        }
        gameIsLive = true;

        roundCounter++;
        evenOrOdd();
    }

    public void setPlayerTwoScore(int score){
        String playerOneScoreTemp = Integer.toString(score);
        txtScoreBot.setText(playerOneScoreTemp);
    }

    public void setPlayerOneScore(int score){
        String playerTwoScoreTemp = Integer.toString(score);
        txtScorePlayerOne.setText(playerTwoScoreTemp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        dbHandler = new MyDBHandler(this);

        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setText(R.string.multiPlayer);
        btnZero     = findViewById(R.id.btnZero);
        btnOne      = findViewById(R.id.btnOne);
        btnTwo      = findViewById(R.id.btnTwo);
        btnThree    = findViewById(R.id.btnThree);
        btnFour     = findViewById(R.id.btnFour);
        btnFive     = findViewById(R.id.btnFive);
        btnSix      = findViewById(R.id.btnSix);
        btnSeven    = findViewById(R.id.btnSeven);
        btnEight    = findViewById(R.id.btnEight);
        btnReset    = findViewById(R.id.btnReset);

        txtPlayerOne = (TextView) findViewById(R.id.txtPlayerOne);
        txtScorePlayerOne = (TextView) findViewById(R.id.txtScorePlayerOne);

        txtBot = (TextView) findViewById(R.id.txtBot);
        txtScoreBot = (TextView) findViewById(R.id.txtScoreBot);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("playerOneValue");
            valueTwo = extras.getString("playerTwoValue");
            //The key argument here must match that used in the other activity
            txtPlayerOne.setText(value);
            txtBot.setText(valueTwo);
        }

        setPlayerOneScore(playerOneScore);
        setPlayerTwoScore(playerTwoScore);



        // The phone is in portrait mode
        if(findViewById(R.id.defaultMulti) != null){
            FragmentManager manager = this.getSupportFragmentManager();

            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.boardMultiFrag))
                    .show(manager.findFragmentById(R.id.menuMultiFrag))
                    .commit();
        } else if(findViewById(R.id.landscapeMulti) != null){
            FragmentManager manager = this.getSupportFragmentManager();

            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.menuMultiFrag))
                    .show(manager.findFragmentById(R.id.boardMultiFrag))
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        User userOne = new User(value, playerOneScore);
        User userTwo = new User(valueTwo, playerTwoScore);

        dbHandler.addUser(userOne);
        dbHandler.addUser(userTwo);
    }
}
