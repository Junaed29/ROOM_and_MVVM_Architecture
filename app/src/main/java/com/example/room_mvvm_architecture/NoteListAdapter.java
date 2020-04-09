package com.example.room_mvvm_architecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**************************ListAdapter for RecyclerView**************************/

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteHolder> {

    private OnItemClickListener listener;

    public NoteListAdapter() {
        /*******DIFF_CALLBACK is created by me*****/
        super(DIFF_CALLBACK);
    }

    /*******it is static because there is no need to initialization to use this DIFF_CALLBACK object*****/
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        /*****************getItem is a builtin method provide by List Adapter super class***************/
        Note currentNote = getItem(position);
        String title = currentNote.getTitle();
        String description = currentNote.getDescription();
        int priority = currentNote.getPriority();

        holder.titleTextView.setText(title);
        holder.descriptionTextView.setText(description);
        holder.priorityTextView.setText(String.valueOf(priority));
    }



    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView priorityTextView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title_Id);
            descriptionTextView = itemView.findViewById(R.id.text_view_description_Id);
            priorityTextView = itemView.findViewById(R.id.text_view_priority_Id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        /*****************getItem is a builtin method provide by List Adapter super class***************/
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
