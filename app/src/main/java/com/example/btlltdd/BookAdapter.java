package com.example.btlltdd;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private Context context;
    private List<String> bookList;

    public BookAdapter(Context context, List<String> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String bookTitle = bookList.get(position);
        holder.bookTitleTextView.setText(bookTitle);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitleTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitleTextView = itemView.findViewById(R.id.textView);
        }
    }
}
