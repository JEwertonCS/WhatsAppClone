package com.example.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.whatsappclone.R;

public class LoginActivity extends AppCompatActivity {
    private TextView textCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textCadastro = findViewById(R.id.textCadastro);
        textCadastro.setOnClickListener( abrirTelaCadastro );

    }

    private View.OnClickListener abrirTelaCadastro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity( intent );
        }
    };
}
