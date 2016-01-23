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
        query.append("NONE TEXT(50) NOT NULL, ");
        query.append("DATA_NASCIMENTO INTEGER(4) NOT NULL)");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertPessoa(Pessoa pessoa){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOME", pessoa.getNome());
        values.put("DATA_NASCIMENTO", pessoa.getDataNascimento().toString());

        db.insert("TB_PESSOA", null, values);

    }

    public ArrayList<Pessoa> getPessoaList(){
        ArrayList<Pessoa> pessoaList = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, "TB_PESSOA", null, null, null, null, null, "ID", null, null);

        while(cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(cursor.getString(1));
            //pessoa.setDataNascimento(cursor.getString(2).toString());

            pessoaList.add(pessoa);
        }

        return pessoaList;
    }

//    public List<CardTransaction> getCardTransactionList(){
//        List<CardTransaction> cardTransactionList = new ArrayList<CardTransaction>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(true, "TB_TRANSACTION", null, null, null, null, null, "ID", null, null);
//
//        while(cursor.moveToNext()){
//            CardTransaction cardTransaction = new CardTransaction();
//            cardTransaction.setOwner(cursor.getString(1));
//            cardTransaction.setCardNumber(cursor.getString(2));
//            cardTransaction.setExpirationYear(cursor.getString(2));
//            cardTransaction.setExpirationMonth(cursor.getString(3));
//            cardTransaction.setCardFlag(cursor.getString(4));
//            cardTransaction.setcVV(cursor.getString(5));
//            cardTransaction.setTotal(cursor.getInt(6));
//
//            cardTransactionList.add(cardTransaction);
//        }
//
//        return cardTransactionList;
//    }


}
