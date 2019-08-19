package org.codepantheon.takenotes.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.codepantheon.takenotes.R;
import org.codepantheon.takenotes.model.NoteInfo;
import org.codepantheon.takenotes.presenter.NotePresenter;
import org.codepantheon.takenotes.presenter.NotePresenterFactory;

public class NoteActivity extends AppCompatActivity {
    private NoteInfo currentNoteInfo = new NoteInfo();
    private NotePresenter notePresenter;
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        notePresenter = NotePresenterFactory.create(this);
        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);

        handleEditMode();
    }

    private void handleEditMode() {
        Intent intent = getIntent();
        if(!intent.hasExtra(NoteInfo.class.getSimpleName())){
            return;
        }

        currentNoteInfo = (NoteInfo) intent.getSerializableExtra(NoteInfo.class.getSimpleName());
        titleEditText.setText(currentNoteInfo.getTitle());
        contentEditText.setText(currentNoteInfo.getContent());
    }

    @Override
    protected void onPause() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        currentNoteInfo.setTitle(title);
        currentNoteInfo.setContent(content);

        if(!currentNoteInfo.isEmpty()) {
            notePresenter.saveNote(currentNoteInfo);
        }
        super.onPause();
    }
}
