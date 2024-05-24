package com.example.treinarai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treinarai.model.ResponseModel;
import com.example.treinarai.model.UserModel;
import com.example.treinarai.retrofitUtil.HackUtill;
import com.example.treinarai.retrofitUtil.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedefinirSenha extends AppCompatActivity {

    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha); // Define o layout da tela de redefinição de senha

        dialogLoading = HackUtill.instanciarDialogLogin(RedefinirSenha.this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Desativa o modo noturno por questões estéticas

        // Obtém as referências dos campos de entrada e botões no layout
        EditText campoEmailRed = findViewById(R.id.campoEmailRed);
        EditText campoSenhaRed = findViewById(R.id.campoSenhaRed);
        Button botaoRedefinirSenha = findViewById(R.id.botaoRedefinirSenha);
        Button botaoVoltarTelaLogin = findViewById(R.id.botaoVoltarTelaLogin);

        // Define um listener de clique para o botão "Redefinir Senha"
        botaoRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o texto dos campos de email e senha
                String email = campoEmailRed.getText().toString().trim();
                String senha = campoSenhaRed.getText().toString().trim();

                // Verifica se os campos estão vazios
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                    // Exibe uma mensagem de erro se algum dos campos estiver vazio
                    Toast.makeText(RedefinirSenha.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica para redefinir a senha

                    // Cria um objeto UserModel e define os valores de email e nova senha
                    UserModel trocarSenhaUser = new UserModel();
                    trocarSenhaUser.setEmail(email);
                    trocarSenhaUser.setNovaSenha(HackUtill.encriptografarSenha(senha));

                    /*
                     * Instancia rapidamente um retrofit e chama a função que vai atualizar
                     * a senha no banco de dados do usuário.
                     */

                    dialogLoading.show(); // Exibe o diálogo de carregamento
                    RetrofitUtil.intanceService(
                            RetrofitUtil.instanceRetrofit()
                    ).atualizarUser(trocarSenhaUser).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            dialogLoading.dismiss(); // Fecha o diálogo de carregamento

                            if (response.isSuccessful()) {
                                // Exibe uma mensagem de sucesso e navega de volta para a tela de login
                                Toast.makeText(RedefinirSenha.this, response.body().getResp(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RedefinirSenha.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Encerra a atividade atual para evitar acumulação na pilha de atividades
                            } else {
                                // Exibe uma mensagem de erro se o email não for encontrado
                                Toast.makeText(RedefinirSenha.this, "E-mail não Encontrado no Banco de Dados!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                            dialogLoading.dismiss(); // Fecha o diálogo de carregamento
                            Toast.makeText(RedefinirSenha.this, "Problema de Conexão!", Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de erro de conexão
                        }
                    });
                }
            }
        });

        // Define um listener de clique para o botão "Voltar para a tela de login"
        botaoVoltarTelaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega de volta para a tela de login (MainActivity)
                Intent intent = new Intent(RedefinirSenha.this, MainActivity.class);
                startActivity(intent);
                finish(); // Encerra a atividade atual para evitar acumulação na pilha de atividades
            }
        });

        // Obtém a referência do ícone de texto no layout
        TextView tituloTreinarAI2 = findViewById(R.id.tituloTreinarAI4);

        // Define um listener de clique para o ícone de texto
        tituloTreinarAI2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega de volta para a tela de login (MainActivity)
                Intent intent = new Intent(RedefinirSenha.this, MainActivity.class);
                startActivity(intent);
                finish(); // Encerra a atividade atual para evitar acumulação na pilha de atividades
            }
        });
    }
}
