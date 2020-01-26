package com.example.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappclone.R;
import com.example.whatsappclone.config.ConfiguracaoFirebase;
import com.example.whatsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
    private TextView textCadastro;
    private EditText editLoginEmail, editLoginSenha;
    private Button buttonLogar;
    FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textCadastro = findViewById(R.id.textCadastro);
        textCadastro.setOnClickListener( abrirTelaCadastro );

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);

        buttonLogar = findViewById(R.id.buttonLogar);
        buttonLogar.setOnClickListener( acaoBotaoLogar );

    }

    @Override
    protected void onStart() {
        super.onStart();

        if ( verificarUsuarioLogado() ){
            abrirTelaPrincipal();
        }
    }

    public void validarUsuario () {
        String email = editLoginEmail.getText().toString();
        String senha = editLoginSenha.getText().toString();

        if ( validarEmail( email ) ){
            if ( validarSenha( senha ) ){
                Usuario usuario = new Usuario();
                usuario.setEmail( email );
                usuario.setSenha( senha );

                logarUsuario( usuario );
            }else {
                Toast.makeText(LoginActivity.this,
                        R.string.digite_senha,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void logarUsuario(Usuario usuario) {

        autenticacao.signInWithEmailAndPassword( usuario.getEmail(), usuario.getSenha() )
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            abrirTelaPrincipal();
                        } else {
                            Log.i("ERRO: ", task.getException().getMessage());

                            String excecao = "";
                            try {
                                throw task.getException();
                            }catch ( FirebaseAuthInvalidUserException e){
                                excecao = getString(R.string.excecao_usuario_nao_cadastrado);
                            } catch ( FirebaseAuthInvalidCredentialsException e){
                                excecao = getString( R.string.excecao_digite_email_valido );
                            } catch (Exception e) {
                                excecao = "Erro: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
    }

    private boolean validarSenha(String senha){
        boolean retorno = true;
        if ( senha.isEmpty() ) {
            Toast.makeText(LoginActivity.this,
                    R.string.informe_senha,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        } else if ( senha.trim().equals("") ){
            Toast.makeText(LoginActivity.this,
                    R.string.informe_senha,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    private boolean validarEmail(String email){
        boolean retorno = true;
        if ( email.isEmpty() ) {
            Toast.makeText(LoginActivity.this,
                    R.string.digite_email,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        } else if ( email.trim().equals("") ){
            Toast.makeText(LoginActivity.this,
                    R.string.informe_email_valido,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    public boolean verificarUsuarioLogado(){
        if ( autenticacao.getCurrentUser() != null ){
            return true;
        } else {
            return false;
        }
    }

    private View.OnClickListener abrirTelaCadastro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity( intent );
        }
    };

    private View.OnClickListener acaoBotaoLogar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validarUsuario();
        }
    };
}
