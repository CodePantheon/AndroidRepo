package org.codepantheon.takenotes.view;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.codepantheon.takenotes.R;
import org.codepantheon.takenotes.model.NoteInfo;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    interface OnNoteSelectedListener{
        void onNoteSelected(NoteInfo noteInfo);
    }

    interface OnNoteLongClickListener{
        void OnNoteLongClick(NoteInfo noteInfo);
    }

    private List<NoteInfo> noteInfos = new ArrayList<>();
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteLongClickListener onNoteLongClickListener;
    private boolean areItemsSelected = false;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("TakeNotes", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent,false);

        return new NoteViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.v("TakeNotes", "onCreateViewHolder");

        // sets default background color for each note item; this helps in resetting selection.
        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.setNoteInfo(noteInfos.get(position));
    }

    // resets background of each card item on selection removal.
    boolean removeSelection() {
        if (areItemsSelected) {
            notifyDataSetChanged();
            areItemsSelected = false;
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return noteInfos.size();
    }

    void setNotes(List<NoteInfo> noteInfos) {
        if(noteInfos == null) {
            throw new InvalidParameterException("noteInfos can't be null");
        }

        this.noteInfos = noteInfos;
        notifyDataSetChanged();
    }

    // deletes a particular card from adapter's noteInfo list and refreshes.
    NoteInfo deleteNoteAtPosition(int position) {
        NoteInfo deletedNote = this.noteInfos.remove(position);
        notifyDataSetChanged();
        return deletedNote;
    }

    void setOnNoteSelectedListener(OnNoteSelectedListener onNoteSelectedListener){
        this.onNoteSelectedListener = onNoteSelectedListener;
    }

    void setOnNoteLongClickListener(OnNoteLongClickListener onNoteLongClickListener){
        this.onNoteLongClickListener = onNoteLongClickListener;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        private final NoteAdapter noteAdapter;
        private final TextView mTitleTextView;
        private final TextView mModifiedDateTextView;
        private final TextView mSummeryTextView;
        private NoteInfo noteInfo;

        private NoteViewHolder(@NotNull View itemView, NoteAdapter noteAdapter) {
            super(itemView);

            this.noteAdapter = noteAdapter;
            mTitleTextView = itemView.findViewById(R.id.tv_title);
            mModifiedDateTextView = itemView.findViewById(R.id.tv_date);
            mSummeryTextView = itemView.findViewById(R.id.tv_summary);

            itemView.setOnClickListener(this::onItemClick);

            // long click event subscription
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    setLongClickSelection(view);
                    return true;
                }
            });
        }

        // long click event handler
        private void setLongClickSelection(View view) {
            if (noteAdapter.onNoteLongClickListener != null) {
                view.setBackgroundColor(Color.parseColor("#ffb2b2"));
                noteAdapter.onNoteLongClickListener.OnNoteLongClick(noteInfo);
                noteAdapter.areItemsSelected = true;
            }
        }

        private void setNoteInfo(NoteInfo noteInfo){
            mTitleTextView.setText(noteInfo.getTitle());
            mModifiedDateTextView.setText(noteInfo.getModifiedDate());
            mSummeryTextView.setText(noteInfo.getSummary());

            this.noteInfo = noteInfo;
        }

        private void onItemClick(View view) {

            // if long click selection is already in progress, treat normal click also for selection.
            if (noteAdapter.areItemsSelected) {
                setLongClickSelection(view);
                return;
            }
            if(this.noteAdapter.onNoteSelectedListener == null){
                return;
            }

            noteAdapter.onNoteSelectedListener.onNoteSelected(noteInfo);
        }
    }
}
