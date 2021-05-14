package story.book.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import story.book.myapplication.R;
import story.book.myapplication.UpdateActivity;
import story.book.myapplication.values.Values;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Activity context;
    ArrayList book_id, book_title, book_author, book_pages;


    int position;

    public DataAdapter(Activity context, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_pages) {

        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        this.position = position;

        holder.id.setText(String.valueOf(book_id.get(position)));

        holder.id.setText(String.valueOf(book_id.get(position)));
        holder.title.setText(String.valueOf(book_title.get(position)));
        holder.author.setText(String.valueOf(book_author.get(position)));
        holder.pages.setText(String.valueOf(book_pages.get(position)));

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra(Values.ID, String.valueOf(book_id.get(position)));

            intent.putExtra(Values.TITLE, String.valueOf(book_title.get(position)));
            intent.putExtra(Values.AUTHOR, String.valueOf(book_author.get(position)));
            intent.putExtra(Values.PAGES, String.valueOf(book_pages.get(position)));
            context.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, id, pages;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.item_layout);

            title = itemView.findViewById(R.id.item_title);
            author = itemView.findViewById(R.id.item_author);
            id = itemView.findViewById(R.id.item_id);
            pages = itemView.findViewById(R.id.item_pages);
        }
    }
}
