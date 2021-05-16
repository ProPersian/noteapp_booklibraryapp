package story.book.myapplication.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import story.book.myapplication.values.Values;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private Context context;


    public DataBaseOpenHelper(@Nullable Context context) {
        super(context, Values.DATABASE_NAME, null, Values.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + Values.TABLE_NAME + " (\n" +
                Values.DATA_ID + "     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                Values.DATA_TITLE + "  TEXT,\n" +
                Values.DATA_AUTHOR + " TEXT,\n" +
                Values.DATA_PAGES + "  INTEGER,\n " +
                Values.DATA_FAV + "  INTEGER " + " DEFAULT 0 " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + Values.TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    public void updateBook(String id, String title, String author, String pages) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Values.DATA_TITLE, title);
        cv.put(Values.DATA_AUTHOR, author);
        cv.put(Values.DATA_PAGES, pages);

        long result = db.update(Values.TABLE_NAME, cv, "id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Update", Toast.LENGTH_SHORT).show();
        }


    }

    public void updateFavourite(String id, Integer fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Values.DATA_FAV, fav);

        long result = db.update(Values.TABLE_NAME, cv, "id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Failed update Fav item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Favourite Update", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBook(String title, String author, int pages) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Values.DATA_TITLE, title);
        cv.put(Values.DATA_AUTHOR, author);
        cv.put(Values.DATA_PAGES, pages);

        long result = database.insert(Values.TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }


    }

    public void deleteBook(String id) {

        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(Values.TABLE_NAME, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Seccessfully Deleted", Toast.LENGTH_SHORT).show();

        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + Values.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void deleteAllData(int state) {

        String query = "";

        SQLiteDatabase database = this.getWritableDatabase();

        if (state == Values.DELETE_ALL) {
            query = "DELETE FROM " + Values.TABLE_NAME;
            database.execSQL(query);
        } else if (state == Values.DROP_TABLE) {
            onUpgrade(database, Values.DATABASE_VERSION, Values.DATABASE_VERSION);
        }
    }
}
