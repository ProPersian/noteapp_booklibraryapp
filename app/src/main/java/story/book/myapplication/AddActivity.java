package story.book.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import story.book.myapplication.sqlite.DataBaseOpenHelper;

public class AddActivity extends AppCompatActivity {

    EditText title_input;
    EditText author_input;
    EditText pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.add_titleInput);
        author_input = findViewById(R.id.add_authorInput);
        pages_input = findViewById(R.id.add_pagesInput);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new ClickMethod());
    }

    @Override
    public void onBackPressed() {


//        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivityForResult(intent, 1);
        finish();
    }


    private class ClickMethod implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String title = title_input.getText().toString();
            String author = author_input.getText().toString();
            int pages = Integer.valueOf(pages_input.getText().toString().trim());


            DataBaseOpenHelper db = new DataBaseOpenHelper(AddActivity.this);
            db.addBook(title, author, pages);

        }
    }
}