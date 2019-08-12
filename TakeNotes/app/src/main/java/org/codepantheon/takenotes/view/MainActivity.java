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

        RecyclerView mRecyclerView = findViewById(R.id.rv_note_container);
        mRecyclerView.setAdapter(noteAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::goToNewNote);
    }

    @Override
    protected void onResume() {
        List<NoteInfo> notes = notePresenter.getAllNotes();
        noteAdapter.setNotes(notes);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToNewNote(View view) {
        Intent newPageIntent = new Intent(this, NewNoteActivity.class);
        startActivity(newPageIntent);
    }
}
