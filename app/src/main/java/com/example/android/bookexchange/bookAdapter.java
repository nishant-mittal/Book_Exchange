package com.example.android.bookexchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {

    private Context mContext;
    private List<Books> mBooksList;
    private Listener mListener;
    public bookAdapter(Context context, List<Books> booksList) {
        mContext = context;
        mBooksList = booksList;
    }
    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Books books = mBooksList.get(position);
        Picasso.get().load(books.getImagePath()).placeholder(R.drawable.books).fit().centerInside().into(holder.bookImage);
        holder.branchText.setText(books.getBranch());
        holder.semesterText.setText(books.getSemester());
        holder.dateText.setText(books.getDateAndTime());
        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mBooksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView semesterText,branchText,dateText;
        CardView bookCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_card_image_view);
            semesterText = itemView.findViewById(R.id.book_card_branch);
            branchText = itemView.findViewById(R.id.book_card_semester);
            dateText = itemView.findViewById(R.id.book_card_date);
            bookCard = itemView.findViewById(R.id.book_card_card_view);
        }
    }
}
