package com.example.iotools.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "grid_table";
    private static final String COL2 = "grid_id";
    private static final String COL3 = "picture";
    private static final String COL4 = "created_date";
    private static final String COL5 = "note";
    private static final String COL6 = "topic";
    private static final String COL7 = "type";


    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2+" TEXT1, "+COL3+" TEXT2, "+COL4+" TEXT3, "+COL5+" TEXT4, "+COL6+" TEXT5, "+COL7+" TEXT6)";
        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String grid_id,String picture, String created_date, String note, String topic, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL2, grid_id);
        Log.d(TAG, "addData: Adding " + grid_id + " to " + TABLE_NAME);

        contentValues.put(COL3, picture);
        Log.d(TAG, "addData: Adding " + picture + " to " + TABLE_NAME);


        contentValues.put(COL4, created_date);
        Log.d(TAG, "addData: Adding " + created_date + " to " + TABLE_NAME);


        contentValues.put(COL5, note);
        Log.d(TAG, "addData: Adding " + note + " to " + TABLE_NAME);


        contentValues.put(COL6, topic);
        Log.d(TAG, "addData: Adding " + topic + " to " + TABLE_NAME);


        contentValues.put(COL7, type);
        Log.d(TAG, "addData: Adding " + type + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }


    }

    public void resetDB(){
        int oldVersion = 1;
        int newVersion = oldVersion++;
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, oldVersion, newVersion);

    }





    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor data = db.rawQuery(query, null);
            return data;
        }



    public void updateNote(String newNote, String grid_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + newNote + "' WHERE " + COL2 + " = '" + grid_id +"'";
        Log.d(TAG, "updateNote: query: " + query);
        Log.d(TAG, "updateNote: Setting note to " + newNote);
        db.execSQL(query);
    }

    public void updateTopic(String newTopic, String grid_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 +
                " = '" + newTopic + "' WHERE " + COL2 + " = '" + grid_id +"'";
        Log.d(TAG, "updateTopic: query: " + query);
        Log.d(TAG, "updateTopic: Setting topic to " + newTopic);
        db.execSQL(query);
    }


    public void updateDate(String newDate, String grid_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newDate + "' WHERE " + COL2 + " = '" + grid_id +"'";
        Log.d(TAG, "updateDate: query: " + query);
        Log.d(TAG, "updateDate: Setting date to " + newDate);
        db.execSQL(query);
    }


    public void deleteID(String grid_id, String topic){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + grid_id + "'";
        Log.d(TAG, "deleteGrid: query: " + query);
        Log.d(TAG, "deleteTopic: Deleting " + topic + " from database.");
        db.execSQL(query);
    }

}