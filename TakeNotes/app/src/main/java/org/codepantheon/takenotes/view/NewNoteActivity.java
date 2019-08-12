package org.codepantheon.takenotes.view;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.codepantheon.takenotes.R;
import org.codepantheon.takenotes.model.NoteInfo;
import org.codepantheon.takenotes.presenter.NotePresenter;
import org.codepantheon.takenotes.presenter.NotePresenterFactory;

public class NewNoteActivity extends AppCompatActivity {
    private NotePresenter notePresenter;
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        notePresenter = NotePresenterFactory.create(this);
        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
    }

    @Override
    protected void onPause() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        NoteInfo newNoteInfo = new NoteInfo(title, content);

        notePresenter.saveNote(newNoteInfo);
        super.onPause();
    }
}
