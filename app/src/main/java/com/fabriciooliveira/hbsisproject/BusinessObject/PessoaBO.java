package com.fabriciooliveira.hbsisproject.BusinessObject;

import android.content.Context;

import com.fabriciooliveira.hbsisproject.database.PessoaDB;
import com.fabriciooliveira.hbsisproject.model.Pessoa;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by fabriciooliveira on 1/23/16.
 */
public class PessoaBO {

    private PessoaDB mPessoaDB;

    public PessoaBO(Context context) {
        mPessoaDB = new PessoaDB(context);
    }

    public void salvarListaDePessoas(ArrayList<Pessoa> listaPessoas){
        mPessoaDB.inserirListaDePessoas(listaPessoas);
    }

    public ArrayList<Pessoa> recuperarListaDePessoas() {
        return mPessoaDB.recuperarListaDePessoas();
    }

    public void atualizarPessoaPorID(Pessoa pessoa) {
        mPessoaDB.atualizarPessoaPorID(pessoa);
    }

}
