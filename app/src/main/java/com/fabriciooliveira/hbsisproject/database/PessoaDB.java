package com.fabriciooliveira.hbsisproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fabriciooliveira.hbsisproject.model.Pessoa;
import com.fabriciooliveira.hbsisproject.util.Constants;

import java.util.ArrayList;

/**
 * Created by fabriciooliveira on 12/25/15.
 */
public class PessoaDB extends SQLiteOpenHelper {

    public PessoaDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA ( ");
        query.append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append("NOME TEXT(50) NOT NULL, ");
        query.append("SOBRENOME TEXT(50) NOT NULL, ");
        query.append("DATA_NASCIMENTO TEXT(50), ");
        query.append("ATIVO INTEGER(1) )");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public void inserirListaDePessoas(ArrayList<Pessoa> listaDePessoas){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i <listaDePessoas.size(); i++) {
            Pessoa pessoa = listaDePessoas.get(i);

            values.put("NOME", pessoa.getNome());
            values.put("SOBRENOME", pessoa.getSobrenome());
            values.put("DATA_NASCIMENTO", pessoa.getDataNascimento());
            values.put("ATIVO", pessoa.getAtivo());

            db.insert("TB_PESSOA", null, values);
        }
    }

    public ArrayList<Pessoa> recuperarListaDePessoas(){
        ArrayList<Pessoa> pessoaList = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, "TB_PESSOA", null, null, null, null, null, "ID", null, null);

        while(cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();

            pessoa.setNome(cursor.getString(1));
            pessoa.setSobrenome(cursor.getString(2));
            pessoa.setDataNascimento(cursor.getString(3));
            pessoa.setAtivo(cursor.getInt(4));

            pessoaList.add(pessoa);
        }

        return pessoaList;
    }

    public void atualizarPessoaPorID(Pessoa pessoa){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOME", pessoa.getNome());
        values.put("SOBRENOME", pessoa.getSobrenome());
        values.put("DATA_NASCIMENTO", pessoa.getDataNascimento());
        values.put("ATIVO", pessoa.getAtivo());

        db.update("TB_PESSOA", values, "ID=?", new String[]{String.valueOf(pessoa.getId())});
    }

}
