package org.codepantheon.takenotes.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final NotePresenter mNotePresenter = NotePresenterFactory.create();
    private NoteAdapter mNoteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<NoteInfo> notes = mNotePresenter.getAllNotes();
        mNoteAdapter.setNotes(notes);

        RecyclerView mRecyclerView = findViewById(R.id.rv_note_container);
        mRecyclerView.setAdapter(mNoteAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::goToNewNote);
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
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToNewNote(View view) {
        Toast.makeText(this, "Create new note", Toast.LENGTH_SHORT).show();
    }
}
