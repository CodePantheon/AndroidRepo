package org.codepantheon.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText m_emailEditText;
    private EditText m_passwordEditText;
    private Button m_RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_RegisterButton = findViewById(R.id.btn_set_user);
        m_emailEditText = findViewById(R.id.edt_set_email);
        m_passwordEditText = findViewById(R.id.edt_set_password);

        m_emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!m_emailEditText.getText().toString().contains("@")) {
                        Toast.makeText(getApplicationContext(), "Invalid email ID", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void OnClickRegister(View view) {

        String email = m_emailEditText.getText().toString();
        String password = m_passwordEditText.getText().toString();

        //validate this string and pass it to DB
        finish();
    }
}
