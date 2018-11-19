package no.woact.haben16.tictactoe;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SinglePlayer extends AppCompatActivity {

    // 0 is X, 1 is O.
    private int player = 0, roundCounter = 1;
    private int btnClicked;
    private int playerScore = 0, botScore = 0;
    private boolean gameIsLive = true;
    private boolean gameOver;
    private boolean oddOrEven = true;

    // if button is unplayed - 2.
    int[] unPlayed = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winMap = {{3, 4, 5}, {2, 4, 6}, {0, 3, 6}, {6, 7, 8}, {0, 1, 2}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}};

    Button btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnReset;
    TextView txtHeader, txtPlayerOne, txtScorePlayerOne, txtBot, txtScoreBot;

    public void evenOrOdd(){
        if((roundCounter % 2) == 0){
            player = 1;
            oddOrEven = false;
        } else {
            player = 0;
            oddOrEven = true;
            setBotMove(btnSix);
        }
    }

    public void defend(){
        // Defend horizontal
        if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 1) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 1) && player == 0){
            setBotMove(btnOne);
        } else if((unPlayed[0] == 1 && unPlayed[1] == 1 && unPlayed[2] == 2) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[3] == 2 && unPlayed[4] == 1 && unPlayed[5] == 1) && player == 0){
            setBotMove(btnThree);
        } else if((unPlayed[3] == 1 && unPlayed[4] == 2 && unPlayed[5] == 1) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[3] == 1 && unPlayed[4] == 1 && unPlayed[5] == 2) && player == 0){
            setBotMove(btnFive);
        } else if((unPlayed[6] == 2 && unPlayed[7] == 1 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnSix);
        } else if((unPlayed[6] == 1 && unPlayed[7] == 2 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnSeven);
        } else if((unPlayed[6] == 1 && unPlayed[7] == 1 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);

        // Defend vertical
        } else if((unPlayed[0] == 2 && unPlayed[3] == 1 && unPlayed[6] == 1) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 1 && unPlayed[3] == 2 && unPlayed[6] == 1) && player == 0){
            setBotMove(btnThree);
        } else if((unPlayed[0] == 1 && unPlayed[3] == 1 && unPlayed[6] == 2) && player == 0){
            setBotMove(btnSix);
        } else if((unPlayed[1] == 2 && unPlayed[4] == 1 && unPlayed[7] == 1) && player == 0){
            setBotMove(btnOne);
        } else if((unPlayed[1] == 1 && unPlayed[4] == 2 && unPlayed[7] == 1) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[1] == 1 && unPlayed[4] == 1 && unPlayed[7] == 2) && player == 0){
            setBotMove(btnSeven);
        } else if((unPlayed[2] == 2 && unPlayed[5] == 1 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[2] == 1 && unPlayed[5] == 2 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnFive);
        } else if((unPlayed[2] == 1 && unPlayed[5] == 1 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);

        //Defend diagonal
        } else if((unPlayed[0] == 2 && unPlayed[4] == 1 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 1 && unPlayed[4] == 2 && unPlayed[8] == 1) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[0] == 1 && unPlayed[4] == 1 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);
        } else if((unPlayed[2] == 2 && unPlayed[4] == 1 && unPlayed[6] == 1) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[2] == 1 && unPlayed[4] == 2 && unPlayed[6] == 1) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[2] == 1 && unPlayed[4] == 1 && unPlayed[6] == 2) && player == 0){
            setBotMove(btnSix);
        }
    }

    public void win(){
        // Win horizontal
        if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 0) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 0) && player == 0){
            setBotMove(btnOne);
        } else if((unPlayed[0] == 0 && unPlayed[1] == 0 && unPlayed[2] == 2) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[3] == 2 && unPlayed[4] == 0 && unPlayed[5] == 0) && player == 0){
            setBotMove(btnThree);
        } else if((unPlayed[3] == 0 && unPlayed[4] == 2 && unPlayed[5] == 0) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[3] == 0 && unPlayed[4] == 0 && unPlayed[5] == 2) && player == 0){
            setBotMove(btnFive);
        } else if((unPlayed[6] == 2 && unPlayed[7] == 0 && unPlayed[8] == 0) && player == 0){
            setBotMove(btnSix);
        } else if((unPlayed[6] == 0 && unPlayed[7] == 2 && unPlayed[8] == 0 && player == 0)){
            setBotMove(btnSeven);
        } else if((unPlayed[6] == 0 && unPlayed[7] == 0 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);
        }

        // Win vertical
        else if((unPlayed[0] == 2 && unPlayed[3] == 0 && unPlayed[6] == 0) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 0 && unPlayed[3] == 2 && unPlayed[6] == 0) && player == 0){
            setBotMove(btnThree);
        } else if((unPlayed[0] == 0 && unPlayed[3] == 0 && unPlayed[6] == 2) && player == 0){
            setBotMove(btnSix);
        } else if((unPlayed[1] == 2 && unPlayed[4] == 0 && unPlayed[7] == 0) && player == 0){
            setBotMove(btnOne);
        } else if((unPlayed[1] == 0 && unPlayed[4] == 2 && unPlayed[7] == 0) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[1] == 0 && unPlayed[4] == 0 && unPlayed[7] == 2) && player == 0){
            setBotMove(btnSeven);
        } else if((unPlayed[2] == 2 && unPlayed[5] == 0 && unPlayed[8] == 0) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[2] == 0 && unPlayed[5] == 2 && unPlayed[8] == 0) && player == 0){
            setBotMove(btnFive);
        } else if((unPlayed[2] == 0 && unPlayed[5] == 0 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);
        }

        // Win diagonal
        else if((unPlayed[0] == 2 && unPlayed[4] == 0 && unPlayed[8] == 0) && player == 0){
            setBotMove(btnZero);
        } else if((unPlayed[0] == 0 && unPlayed[4] == 2 && unPlayed[8] == 0) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[0] == 0 && unPlayed[4] == 0 && unPlayed[8] == 2) && player == 0){
            setBotMove(btnEight);
        } else if((unPlayed[2] == 2 && unPlayed[4] == 0 && unPlayed[6] == 0) && player == 0){
            setBotMove(btnTwo);
        } else if((unPlayed[2] == 0 && unPlayed[4] == 2 && unPlayed[6] == 0) && player == 0){
            setBotMove(btnFour);
        } else if((unPlayed[2] == 0 && unPlayed[4] == 0 && unPlayed[6] == 2) && player == 0){
            setBotMove(btnSix);
        }
    }

    public void bestBotMove(){
        //
        // Level 2

        if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }

        //
        //
        // Level 3

        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }

        //
        //
        //
        // Level 4

        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnZero);
        }

        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        } else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnTwo);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        } else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnTwo);
        }
         else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }

        //
        //
        //
        //
        // Level 5 moves

        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnOne);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }

        //
        //
        //
        //
        //
        // Level 6 moves

        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnFive);
        }

        //
        //
        //
        //
        //
        //
        // level 7

        else if((unPlayed[0] == 1 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 0 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnEight);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnOne);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 2 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 2 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnSix);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnFour);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 2 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 2 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnFive);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 0 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 2
                && unPlayed[8] == 2) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 2 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 0 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 2 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 2 && unPlayed[6] == 2 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnTwo);
        }

        //
        //
        //
        //
        //
        //
        //
        // level 8

        else if((unPlayed[0] == 2 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 2 &&
                unPlayed[4] == 0 && unPlayed[5] == 1 && unPlayed[6] == 1 && unPlayed[7] == 0
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnZero);
        }
        else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 2 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 1 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnTwo);
        } else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 0 &&
                unPlayed[4] == 1 && unPlayed[5] == 2 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnFive);
        } else if((unPlayed[0] == 1 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 2 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnThree);
        }
        else if((unPlayed[0] == 0 && unPlayed[1] == 1 && unPlayed[2] == 0 && unPlayed[3] == 1 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 2
                && unPlayed[8] == 1) && player == 0)
        {
            setBotMove(btnSeven);
        }
        else if((unPlayed[0] == 2 && unPlayed[1] == 0 && unPlayed[2] == 1 && unPlayed[3] == 1 &&
                unPlayed[4] == 1 && unPlayed[5] == 0 && unPlayed[6] == 0 && unPlayed[7] == 1
                && unPlayed[8] == 0) && player == 0)
        {
            setBotMove(btnZero);
        }
    }

    public void clickOn(View view) {

        Button btn = (Button) findViewById(view.getId());
        btnClicked = Integer.parseInt(btn.getTag().toString());

        if (unPlayed[btnClicked] == 2 && gameIsLive) {

            unPlayed[btnClicked] = player;
            setPlayerMove(btn);

            for (int[] winMaps : winMap) {
                if (unPlayed[winMaps[0]] == unPlayed[winMaps[1]] &&
                        unPlayed[winMaps[1]] == unPlayed[winMaps[2]] &&
                        unPlayed[winMaps[0]] != 2) {

                    String winner = txtPlayerOne.getText().toString();

                    gameIsLive = false;
                    if (unPlayed[winMaps[0]] == 0) {

                        winner = "TTTBot";
                        botScore++;

                        setBotScore(botScore);

                    }
                    Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();
                } else if (player == 0){
                    win();
                    defend();
                    bestBotMove();
                }
            }
        } else {
            gameOver = true;
            for (int counter : unPlayed) {
                if (counter == 2) {
                    gameOver = false;
                }
            }
            if (gameOver) {
                Toast.makeText(this, "Draw - play again.", Toast.LENGTH_SHORT).show();
                gameIsLive = false;
            }
        }
        Log.i("Info", btn.toString() + " has been clicked");
    }

    public void setBotMove(Button btn){
        int button;

        if(player == 0 && oddOrEven == true){
            btn.setText("X");
            button = Integer.parseInt(btn.getTag().toString());
            unPlayed[button] = 0;
            player = 1;
            System.out.println("Button stored: " + button);
        } else if(player == 0 && oddOrEven == false){
            btn.setText("O");
            button = Integer.parseInt(btn.getTag().toString());
            unPlayed[button] = 0;
            player = 1;
        }
    }

    public void setPlayerMove(Button btnClicked) {
        if (player == 1 && oddOrEven == true) {
            btnClicked.setText("O"); // O = 1
            player = 0;
        } else if(player == 1 && oddOrEven == false){
            btnClicked.setText("X"); // X
            player = 0;
        }
    }

    public void resetButtons(View view) {
        for(int i = 0; i < unPlayed.length; i ++){
            System.out.println("Array unplayed: " + unPlayed[i]);
        }
        if(gameIsLive) {
            Toast.makeText(this, "You have to finish the game.", Toast.LENGTH_SHORT).show();
        }
        else if(!gameIsLive){
            for (int i = 0; i < unPlayed.length; i++) {
                unPlayed[i] = 2;
                System.out.println(unPlayed[i]);
            }

            btnZero.setText("");
            btnOne.setText("");
            btnTwo.setText("");
            btnThree.setText("");
            btnFour.setText("");
            btnFive.setText("");
            btnSix.setText("");
            btnSeven.setText("");
            btnEight.setText("");

            roundCounter++;
            evenOrOdd();
        }
        gameIsLive = true;
    }

    public void setBotScore(int score){
        String botScoreTemp = Integer.toString(score);
        txtScoreBot.setText(botScoreTemp);
    }

    public void setPlayerScore(int score){
        String playerScoreTemp = Integer.toString(score);
        txtScorePlayerOne.setText(playerScoreTemp);
    }

    public void exitGame(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

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

        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtPlayerOne = (TextView) findViewById(R.id.txtPlayerOne);
        txtScorePlayerOne = (TextView) findViewById(R.id.txtScorePlayerOne);

        txtBot = (TextView) findViewById(R.id.txtBot);
        txtScoreBot = (TextView) findViewById(R.id.txtScoreBot);

        txtPlayerOne.setText("Player");

        setPlayerScore(playerScore);
        setBotScore(botScore);
        setBotMove(btnSix);

        if(findViewById(R.id.defaultLayout) != null){
            FragmentManager manager = this.getSupportFragmentManager();

            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.boardFragment))
                    .show(manager.findFragmentById(R.id.menuFragment))
                    .commit();
        } else if(findViewById(R.id.landscapeLayout) != null){
            FragmentManager manager = this.getSupportFragmentManager();

            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.menuFragment))
                    .show(manager.findFragmentById(R.id.boardFragment))
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putIntArray("myArray", unPlayed);
    }
}
