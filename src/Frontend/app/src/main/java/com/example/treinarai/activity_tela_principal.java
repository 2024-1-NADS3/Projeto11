package com.example.treinarai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_tela_principal extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        // Configuração necessária para rodar o site do TypeBot
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        WebView webView = findViewById(R.id.webView2); // Localiza o WebView no layout
        WebSettings webSettings = webView.getSettings(); // Obtém as configurações do WebView
        webSettings.setJavaScriptEnabled(true); // Habilita JavaScript
        // Define o user agent do WebView
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, como Gecko) Chrome/58.0.3029.110 Safari/537.3");
        webSettings.setDomStorageEnabled(true); // Habilita o armazenamento DOM
        webSettings.setDatabaseEnabled(true); // Habilita o banco de dados
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // Permite que JavaScript abra janelas automaticamente
        webSettings.setAllowFileAccess(true); // Permite acesso a arquivos
        webSettings.setAllowContentAccess(true); // Permite acesso a conteúdo
        webSettings.setUseWideViewPort(true); // Habilita o uso de viewport amplo
        webSettings.setLoadWithOverviewMode(true); // Habilita o carregamento com visão geral
        webSettings.setSupportMultipleWindows(true); // Suporte a múltiplas janelas
        webSettings.setGeolocationEnabled(true); // Habilita geolocalização
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // Define o modo de cache padrão

        // Passando somente o link do site
        webView.loadUrl("https://typebot.co/lead-generation-scalex"); // Carrega a URL especificada no WebView

        // Define um WebViewClient para controlar o carregamento das URLs dentro do próprio WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // Carrega a URL dentro do WebView
                return true; // Indica que o WebView deve carregar a URL
            }
        });

        // Adiciona um clique listener no título para voltar à tela de login
        TextView tituloTreinarAI3 = findViewById(R.id.tituloTreinarAI3); // Localiza o TextView no layout
        tituloTreinarAI3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent para iniciar a MainActivity
                Intent intent = new Intent(activity_tela_principal.this, MainActivity.class);
                startActivity(intent); // Inicia a MainActivity
                finish(); // Opcional: fecha a atividade atual para que não fique na pilha de atividades
            }
        });
    }
}
