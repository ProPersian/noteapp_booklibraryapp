package story.book.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import story.book.myapplication.adapter.DataAdapter;
import story.book.myapplication.sqlite.DataBaseOpenHelper;
import story.book.myapplication.values.Values;

import static android.R.drawable.ic_menu_delete;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;

    DataBaseOpenHelper dataBaseOpenHelper;

    DataAdapter adapter;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    ArrayList<Integer> book_fav;


    ImageView imageView_noData;
    TextView textView_noData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        storeDataInArrays();

        adapter = new DataAdapter(MainActivity.this, book_id, book_title, book_author, book_pages, book_fav);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void initialization() {

        recyclerView = findViewById(R.id.recycler_view_items);
        actionButton = findViewById(R.id.addButtonFab);
        imageView_noData = findViewById(R.id.imageView_no_data);
        textView_noData = findViewById(R.id.text_no_data);

        actionButton.setOnClickListener(new ClickEvent());

        dataBaseOpenHelper = new DataBaseOpenHelper(MainActivity.this);

        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        book_fav = new ArrayList<>();

    }


    private void sortIDs() {

    }

    private void storeDataInArrays() {
        Cursor cursor = dataBaseOpenHelper.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            imageView_noData.setVisibility(View.VISIBLE);
            textView_noData.setVisibility(View.VISIBLE);

        } else {
            while (cursor.moveToNext()) {
//

                book_id.add(getStringFromCursor(cursor, Values.DATA_ID));
                book_title.add(getStringFromCursor(cursor, Values.DATA_TITLE));
                book_author.add(getStringFromCursor(cursor, Values.DATA_AUTHOR));
                book_pages.add(getStringFromCursor(cursor, Values.DATA_PAGES));
                book_fav.add(getIntegerFromCursor(cursor, Values.DATA_FAV));

            }

            Toast.makeText(this, "data found.", Toast.LENGTH_SHORT).show();
            imageView_noData.setVisibility(View.GONE);
            textView_noData.setVisibility(View.GONE);
        }
    }

    private Integer getIntegerFromCursor(Cursor cursor, String dataFav) {
        int i;
        int j;
        i = cursor.getColumnIndex(dataFav);
        j = cursor.getInt(i);
        return j;
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        int i;
        String s;
        i = cursor.getColumnIndex(columnName);
        s = cursor.getString(i);
        return s;
    }

    private void confirmDeleteDialog(int state) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Items")
                .setMessage("Are you sure you want to delete all items?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with confirm operation

                        DataBaseOpenHelper database = new DataBaseOpenHelper(getApplicationContext());

                        database.deleteAllData(state);

                        //refresh
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("نخیر", null)
                .setIcon(ic_menu_delete)
                .show();
    }

    private class ClickEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item_delete_all:

                confirmDeleteDialog(Values.DELETE_ALL);
                break;
            case R.id.item_reset:
                confirmDeleteDialog(Values.DROP_TABLE);
                break;
            case R.id.item_sort_id:

                break;
            case R.id.item_rtl:
                changeLayoutDirection();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    private void changeLayoutDirection() {
        ConstraintLayout layout = findViewById(R.id.main_activity);
        layout.setLayoutDirection(View.TEXT_DIRECTION_RTL);
    }
}