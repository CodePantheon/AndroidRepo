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
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<NoteInfo> mNotes;

    public NoteAdapter(List<NoteInfo> notes) {
        if(notes == null) {
            throw new InvalidParameterException("notes cant be null");
        }

        mNotes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("TakeNotes", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.v("TakeNotes", "onCreateViewHolder");
        holder.setNoteInfo(mNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setNotes(List<NoteInfo> notes) {
        if(notes == null) {
            throw new InvalidParameterException("notes cant be null");
        }

        mNotes = notes;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView mTitleTextView;
        private final TextView mModifiedDateTextView;
        private final TextView mSummeryTextView;

        public NoteViewHolder(@NotNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.tv_title);
            mModifiedDateTextView = itemView.findViewById(R.id.tv_date);
            mSummeryTextView = itemView.findViewById(R.id.tv_summary);
        }

        public void setNoteInfo(NoteInfo noteInfo){
            mTitleTextView.setText(noteInfo.getTitle());
            mModifiedDateTextView.setText(noteInfo.getModifiedDate());
            mSummeryTextView.setText(noteInfo.getSummary());
        }
    }
}
