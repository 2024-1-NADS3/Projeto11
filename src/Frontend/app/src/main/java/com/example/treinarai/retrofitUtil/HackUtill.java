package com.example.treinarai.retrofitUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

import com.example.treinarai.R;

import java.util.Base64;

public class HackUtill {

    public static String encriptografarSenha(String senha){
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    public static Dialog instanciarDialogLogin(Activity c){
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setCancelable(false);
        b.setView(LayoutInflater.from(c).inflate(R.layout.loading_layout, null));
        return b.create();
    }
}
