package org.codepantheon.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText m_emailEditText;
    private EditText m_passwordEditText;
    private Button m_LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_LoginButton = findViewById(R.id.btn_login);
        m_emailEditText = findViewById(R.id.edt_email);
        m_passwordEditText = findViewById(R.id.edt_password);
    }

    public void OnLoginButtonClick(View view) {
        String email = m_emailEditText.getText().toString();
        String password = m_passwordEditText.getText().toString();
    }


    private void OnSuccessfulLogin(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("TakeNotesPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_user", "");
        editor.putBoolean("is_logged_in", true);
        editor.commit();
    }

    public void OnSignUpButtonClick(View view) {

        startActivity(new Intent(this, SignUpActivity.class));
    }
}
