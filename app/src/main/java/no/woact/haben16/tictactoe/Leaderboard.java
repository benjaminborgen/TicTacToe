package no.woact.haben16.tictactoe;

import android.database.Cursor;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    private ListView listView;
    MyDBHandler myDBHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = (ListView) findViewById(R.id.listView);
        myDBHandler = new MyDBHandler(this);

        printListView();
    }

    public void printListView(){

        ListAdapter adapter;
        Cursor data = myDBHandler.getList();

        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Integer> listDataTwo = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));

            listDataTwo.add(data.getInt(2));
            String[] sArray = {"name", "wins"};
            int[] iArray = {android.R.id.text1, android.R.id.text2};

            adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, data, sArray, iArray);
            listView.setAdapter(adapter);
        }
    }
}
