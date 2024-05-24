package com.example.treinarai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Classe para gerenciar o banco de dados SQLite
public class DatabaseHelper extends SQLiteOpenHelper {
    // Nome do banco de dados
    private static final String DATABASE_NAME = "MyDatabase.db";
    // Versão do banco de dados
    private static final int DATABASE_VERSION = 1;

    // Construtor que chama o super construtor da classe SQLiteOpenHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método chamado quando o banco de dados é criado pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação das tabelas do banco de dados
        db.execSQL("CREATE TABLE IF NOT EXISTS MyTable (id INTEGER PRIMARY KEY, name TEXT)");
    }

    // Método chamado quando o banco de dados precisa ser atualizado
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizações do banco de dados, se necessário
        // Este método pode ser usado para modificar a estrutura do banco de dados,
        // migrar dados, etc. ao alterar a versão do banco de dados.
    }
}
