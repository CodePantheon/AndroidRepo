package org.codepantheon.searchflickr.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.codepantheon.searchflickr.R;
import org.codepantheon.searchflickr.model.ImageInfo;
import org.codepantheon.searchflickr.presenter.ImagePresenter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final ImagePresenter m_ImagePresenter = new ImagePresenter();
    private RecyclerView m_RecyclerView;
    private EditText m_SearchEditText;
    private ImageAdapter m_ImageAdapter;
    private ProgressBar m_LoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_SearchEditText = findViewById(R.id.et_SearchText);
        m_RecyclerView = findViewById(R.id.rv_imageList);
        m_LoadingIndicator = findViewById(R.id.pb_loading_indicator);

        m_ImageAdapter = new ImageAdapter();
        m_RecyclerView.setAdapter(m_ImageAdapter);
        m_RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        m_SearchEditText.setOnKeyListener(this::enterKeyPressedEventHandler);
        m_ImagePresenter.getImagesFetchedEvent().add(this::imagesArrivedEventHandler);
    }

    public void onOKButtonClick(View view) {
        String searchKeyword = m_SearchEditText.getText().toString();
        if (searchKeyword.isEmpty()) {
            return;
        }

        m_LoadingIndicator.setVisibility(View.VISIBLE);
        m_ImagePresenter.fetchImagesAsync(searchKeyword);

        hideKeyboard();
    }

    private boolean enterKeyPressedEventHandler(View view, int i, KeyEvent keyEvent) {
        if(!(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            return false;
        }

        onOKButtonClick(view);
        return true;
    }

    private void imagesArrivedEventHandler(ImageInfo[] imageInfoArray) {
        if(imageInfoArray == null || imageInfoArray.length == 0){
            Toast.makeText(this, "Error. Please Try Again!", Toast.LENGTH_LONG)
                 .show();
        }
        else {
            m_ImageAdapter.setImageSource(Arrays.asList(imageInfoArray));
        }

        m_LoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void hideKeyboard() {
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}
