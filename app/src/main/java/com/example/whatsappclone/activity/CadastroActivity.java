package com.example.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsappclone.R;
import com.example.whatsappclone.config.ConfiguracaoFirebase;
import com.example.whatsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editSenha;
    private Button buttonCadastrar;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editLoginEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonCadastrar = findViewById(R.id.buttonCadatrar);

        buttonCadastrar.setOnClickListener( validarUsuario );
    }

    private void cadastrarUsuario(Usuario usuario) {
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastar usuário",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            }catch ( FirebaseAuthWeakPasswordException e){
                                excecao = getString( R.string.excecao_senha_fraca );
                            } catch ( FirebaseAuthInvalidCredentialsException e){
                                excecao = getString( R.string.excecao_digite_email_valido );
                            } catch ( FirebaseAuthUserCollisionException e){
                                excecao = getString( R.string.excecao_conta_ja_cadastrada );
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private View.OnClickListener validarUsuario = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if ( validarNome( nome ) ){
                if ( validarEmail( email ) ){
                    if ( !senha.isEmpty() ){
                        Usuario usuario = new Usuario(nome, email, senha);
                        cadastrarUsuario( usuario );
                    }else {
                        Toast.makeText(CadastroActivity.this,
                                "Informe sua senha",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private boolean validarNome(String nome){
        boolean retorno = true;
        if ( nome.isEmpty() ) {
            Toast.makeText(CadastroActivity.this,
                    R.string.digite_nome,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        } else if ( nome.trim().equals("") ){
            Toast.makeText(CadastroActivity.this,
                    R.string.informe_nome_valido,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    private boolean validarEmail(String email){
        boolean retorno = true;
        if ( email.isEmpty() ) {
            Toast.makeText(CadastroActivity.this,
                    R.string.digite_email,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        } else if ( email.trim().equals("") ){
            Toast.makeText(CadastroActivity.this,
                    R.string.informe_email_valido,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }
}
