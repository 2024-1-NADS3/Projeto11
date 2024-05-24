package com.example.treinarai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.treinarai.model.ResponseModel;
import com.example.treinarai.model.UserModel;
import com.example.treinarai.retrofitUtil.HackUtill;
import com.example.treinarai.retrofitUtil.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // Declaração das variáveis de campo para os componentes da UI
    private EditText campoEmail;
    private EditText campoSenha;

    // Diálogo de carregamento
    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o diálogo de carregamento
        dialogLoading = HackUtill.instanciarDialogLogin(MainActivity.this);

        // Desativa o modo noturno por questões estéticas
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // questão de estética by: Benicio

        // Obtém as referências dos campos de email e senha da UI
        campoEmail = findViewById(R.id.campoEmail);
        campoSenha = findViewById(R.id.campoSenha);
        campoSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Obtém as referências dos botões da UI
        Button botaoEntrar = findViewById(R.id.botaoEntrar);
        Button botaoCriarConta = findViewById(R.id.botaoCriarConta);
        Button botaoEsqueciSenha = findViewById(R.id.botaoEsqueciSenha);

        // Define o listener para o botão de login
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin(); // Chama o método para validar o login
            }
        });

        // Define o listener para o botão de criar conta
        botaoCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a atividade de cadastro
                Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        // Define o listener para o botão de esqueci a senha
        botaoEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a atividade de redefinição de senha
                Intent intent = new Intent(MainActivity.this, RedefinirSenha.class);
                startActivity(intent);
            }
        });
    }

    // Método para validar as credenciais de login
    private void validarLogin() {
        // Obtém o texto dos campos de email e senha
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        // Verifica se os campos estão vazios
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            // Verifica se o email é válido
        } else if (!isValidEmail(email)) {
            Toast.makeText(MainActivity.this, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show();
            // Verifica se a senha é válida
        } else if (!isValidPassword(senha)) {
            Toast.makeText(MainActivity.this, "A senha deve conter pelo menos 8 caracteres, incluindo letras e números", Toast.LENGTH_SHORT).show();
            // Se todas as validações passarem, realiza o login
        } else {
            /*
             * Lógica de login feita pela API, estou instânciando o retrofit para acessar a rota
             * que vai fazer o login do usuário no sistema.
             */
            UserModel usuarioLogar = new UserModel();
            usuarioLogar.setEmail(email);
            usuarioLogar.setSenha(HackUtill.encriptografarSenha(senha));

            // Mostra o diálogo de carregamento
            dialogLoading.show();
            RetrofitUtil.intanceService(
                    RetrofitUtil.instanceRetrofit()
            ).logarUser(usuarioLogar).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    // Dismiss the loading dialog
                    dialogLoading.dismiss();

                    if (response.isSuccessful()) {
                        // Exibe uma mensagem de sucesso e inicia a atividade principal
                        Toast.makeText(MainActivity.this, response.body().getResp(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, activity_tela_principal.class);
                        startActivity(intent);
                    } else {
                        // Exibe uma mensagem de erro se as credenciais forem inválidas
                        Toast.makeText(MainActivity.this, "Credênciais Inválidas!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                    // Desabilita o diálogo de carregamento e exibe uma mensagem de erro de conexão
                    dialogLoading.dismiss();
                    Toast.makeText(MainActivity.this, "Problema de Conexão!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Método para verificar se o email é válido usando expressão regular
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    // Método para verificar se a senha atende aos critérios mínimos
    private boolean isValidPassword(String password) {
        // Padrão que verifica se a senha contém pelo menos 8 caracteres, incluindo letras e números
        return password != null && password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }
}
