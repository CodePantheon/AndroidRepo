package org.codepantheon.takenotes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codepantheon.takenotes.R;
import org.codepantheon.takenotes.model.NoteInfo;
import org.codepantheon.takenotes.presenter.NotePresenter;
import org.codepantheon.takenotes.presenter.NotePresenterFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotePresenter notePresenter;
    private NoteAdapter noteAdapter;
    private RecyclerView mRecyclerView;
    private List<NoteInfo> selectedNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notePresenter = NotePresenterFactory.create(this);
        noteAdapter = new NoteAdapter();
        noteAdapter.setOnNoteSelectedListener(this::onNoteSelected);
        noteAdapter.setOnNoteLongClickListener(this::onNoteLongClicked);

        mRecyclerView = findViewById(R.id.rv_note_container);
        mRecyclerView.setAdapter(noteAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::showNewNotePage);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                int position = viewHolder.getAdapterPosition();
                onNoteItemSwipe(position);
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(mRecyclerView);
    }

    private void onNoteItemSwipe(int swipePosition) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.delete_dialog_title);
        adb.setIcon(android.R.drawable.ic_delete);
        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                NoteInfo swipedItem = noteAdapter.deleteNoteAtPosition(swipePosition);
                notePresenter.deleteNote(swipedItem);
                Toast.makeText(getApplicationContext(), R.string.delete_message, Toast.LENGTH_LONG).show();
            }
        });
        adb.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                noteAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), getString(R.string.cancel_message), Toast.LENGTH_LONG).show();
            }
        });
        adb.show();
    }

    @Override
    protected void onResume() {
        selectedNotes.clear();
        List<NoteInfo> notes = notePresenter.getAllNotes();
        noteAdapter.setNotes(notes);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            showSettingsPage();
        }
        else if (item.getItemId() == R.id.delete_menu_button) {
            deleteSelectedNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    // if items are selected, then back button clears selection instead of pausing application.
    @Override
    public void onBackPressed() {
        if (noteAdapter.removeSelection()) {
            selectedNotes.clear();
            return;
        }
        super.onBackPressed();
    }

    private void deleteSelectedNotes() {
        for ( NoteInfo note : selectedNotes) {
            notePresenter.deleteNote(note);
        }
        noteAdapter.removeSelection();
        selectedNotes.clear();
        noteAdapter.setNotes(notePresenter.getAllNotes());
    }

    private void showNewNotePage(View view) {
        Intent newPageIntent = new Intent(this, NewNoteActivity.class);
        startActivity(newPageIntent);
    }

    private void showSettingsPage() {
        Intent settingsPageIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsPageIntent);
    }

    private void onNoteSelected(NoteInfo noteInfo) {
        Intent editNotePageIntent = new Intent(this, NewNoteActivity.class);
        editNotePageIntent.putExtra(NoteInfo.class.getSimpleName(), noteInfo);
        startActivity(editNotePageIntent);
    }

    private void onNoteLongClicked(NoteInfo noteInfo) {
        selectedNotes.add(noteInfo);
    }
}
