package no.woact.haben16.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDb.db";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WINS = "wins";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public MyDBHandler(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String myDb = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_WINS + " INTEGER " +
                ");";
        db.execSQL(myDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_WINS, user.getWins());
        db.insert(TABLE_USERS, null, values);

        long result = db.insert(TABLE_USERS, null, values);

        if(result== -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_USERS;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteUser(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + "=\"" + name + "\";");
    }

    public void deleteDb(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public String printDb(){
        String data = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("name")) !=null){
                data += c.getString(c.getColumnIndex("name"));
                data += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return data;
    }

    public void getName(String name){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("SELECT NAME FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + name);
    }

    public void getWins(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("SELECT WINS FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + name);
    }

    public Cursor getList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE 1" +  " ORDER BY " + COLUMN_WINS + " DESC", null);
        return data;
    }
}
