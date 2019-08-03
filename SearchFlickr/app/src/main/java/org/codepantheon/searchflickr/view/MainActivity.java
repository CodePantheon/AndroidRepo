package org.codepantheon.searchflickr.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.codepantheon.searchflickr.R;
import org.codepantheon.searchflickr.model.ImageInfo;
import org.codepantheon.searchflickr.presenter.ImagePresenter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final ImagePresenter m_ImagePresenter = new ImagePresenter();
    private RecyclerView m_RecyclerView;
    private EditText m_SearchEditText;
    private Button m_SearchButton;
    private ImageAdapter m_ImageAdapter;
    private ProgressBar m_LoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_SearchEditText = findViewById(R.id.et_SearchText);
        m_SearchButton = findViewById(R.id.btn_OK);
        m_RecyclerView = findViewById(R.id.rv_imageList);
        m_LoadingIndicator = findViewById(R.id.pb_loading_indicator);

        m_ImageAdapter = new ImageAdapter();
        m_RecyclerView.setAdapter(m_ImageAdapter);
        m_RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        m_ImagePresenter.addImagesFetchedEventHandler(this::imagesArrivedEventHandler);
    }

    public void onOKButtonClick(View view) {
        String searchKeyword = m_SearchEditText.getText().toString();
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return;
        }

        m_LoadingIndicator.setVisibility(View.VISIBLE);
        m_ImagePresenter.fetchImagesAsync(searchKeyword);
    }

    private void imagesArrivedEventHandler(ImageInfo[] imageInfoArray) {

        m_ImageAdapter.setImageSource(Arrays.asList(imageInfoArray));
        m_LoadingIndicator.setVisibility(View.INVISIBLE);
        m_ImageAdapter.notifyDataSetChanged();
    }
}
