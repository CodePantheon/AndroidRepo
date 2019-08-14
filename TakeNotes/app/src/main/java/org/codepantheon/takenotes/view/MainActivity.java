package org.codepantheon.takenotes.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.codepantheon.takenotes.R;
import org.codepantheon.takenotes.model.NoteInfo;
import org.codepantheon.takenotes.presenter.NotePresenter;
import org.codepantheon.takenotes.presenter.NotePresenterFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotePresenter notePresenter;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notePresenter = NotePresenterFactory.create(this);
        noteAdapter = new NoteAdapter();
        noteAdapter.setOnNoteSelectedListener(this::onNoteSelected);

        RecyclerView mRecyclerView = findViewById(R.id.rv_note_container);
        mRecyclerView.setAdapter(noteAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::showNewNotePage);
    }

    @Override
    protected void onResume() {
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

        return super.onOptionsItemSelected(item);
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
}
