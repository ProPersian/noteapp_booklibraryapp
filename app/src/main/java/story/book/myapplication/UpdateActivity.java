package story.book.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import story.book.myapplication.sqlite.DataBaseOpenHelper;
import story.book.myapplication.values.Values;

import static android.R.drawable.ic_menu_delete;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_button;
    Button delete_button;

    private String id;
    private String title;
    private String author;
    private String pages;
    private Boolean isData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        title_input = findViewById(R.id.update_titleInput);
        author_input = findViewById(R.id.update_authorInput);
        pages_input = findViewById(R.id.update_pagesInput);
        update_button = findViewById(R.id.update_Button);
        delete_button = findViewById(R.id.delete_Button);

        getIntentData();
        setIntentData();

        update_button.setOnClickListener(v -> {

            DataBaseOpenHelper db = new DataBaseOpenHelper(UpdateActivity.this);
            getValuesFromText();
            db.updateBook(id, title, author, pages);

            finish();
        });

        delete_button.setOnClickListener(v -> {

            confirmDeleteDialog();
        });


    }

    @Override
    public void onBackPressed() {

//        super.onBackPressed();

        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivityForResult(intent, 1);
        finish();
    }

    private void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Book Item")
                .setMessage("Are you sure you want to delete " + title + " " + author + " item?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with confirm operation
                        DataBaseOpenHelper db = new DataBaseOpenHelper(UpdateActivity.this);
                        db.deleteBook(id);
                        finish();

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("نخیر", null)
                .setIcon(ic_menu_delete)
                .show();
    }

    private void getValuesFromText() {
        title = title_input.getText().toString();
        author = author_input.getText().toString();
        pages = pages_input.getText().toString();
    }

    private void setIntentData() {
        isData = getIntent().hasExtra(Values.ID) && getIntent().hasExtra(Values.TITLE) &&
                getIntent().hasExtra(Values.AUTHOR) && getIntent().hasExtra(Values.PAGES);


        if (isData) {
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        }
    }

    private void getIntentData() {

        isData = getIntent().hasExtra(Values.ID) && getIntent().hasExtra(Values.TITLE) &&
                getIntent().hasExtra(Values.AUTHOR) && getIntent().hasExtra(Values.PAGES);

        if (isData) {
            id = getIntent().getStringExtra(Values.ID);
            title = getIntent().getStringExtra(Values.TITLE);
            author = getIntent().getStringExtra(Values.AUTHOR);
            pages = getIntent().getStringExtra(Values.PAGES);
        } else {

            Toast.makeText(this, "No Data...", Toast.LENGTH_SHORT).show();

        }


    }
}