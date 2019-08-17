package org.codepantheon.takenotes.view;

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

    private List<NoteInfo> noteInfos = new ArrayList<>();
    private OnNoteSelectedListener onNoteSelectedListener;

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
        holder.setNoteInfo(noteInfos.get(position));
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

    NoteInfo deleteNoteAtPosition(int position) {
        NoteInfo deletedNote = this.noteInfos.remove(position);
        notifyDataSetChanged();
        return deletedNote;
    }

    void setOnNoteSelectedListener(OnNoteSelectedListener onNoteSelectedListener){
        this.onNoteSelectedListener = onNoteSelectedListener;
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
        }

        private void setNoteInfo(NoteInfo noteInfo){
            mTitleTextView.setText(noteInfo.getTitle());
            mModifiedDateTextView.setText(noteInfo.getModifiedDate());
            mSummeryTextView.setText(noteInfo.getSummary());

            this.noteInfo = noteInfo;
        }

        private void onItemClick(View view) {
            if(this.noteAdapter.onNoteSelectedListener == null){
                return;
            }

            noteAdapter.onNoteSelectedListener.onNoteSelected(noteInfo);
        }
    }
}
