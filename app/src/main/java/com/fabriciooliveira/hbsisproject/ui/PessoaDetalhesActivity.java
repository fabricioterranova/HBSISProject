package com.fabriciooliveira.hbsisproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fabriciooliveira.hbsisproject.R;
import com.fabriciooliveira.hbsisproject.model.Pessoa;
import com.fabriciooliveira.hbsisproject.util.Constants;
import com.fabriciooliveira.hbsisproject.util.DataHelper;

/**
 * Created by fabriciooliveira on 1/23/16.
 */
public class PessoaDetalhesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_details);

        TextView nome, sobrenome, dataNascimento, ativo;
        Pessoa pessoa;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        nome = (TextView) findViewById(R.id.detalhe_nome);
        sobrenome = (TextView) findViewById(R.id.detalhe_sobrenome);
        dataNascimento = (TextView) findViewById(R.id.detalhe_datanascimento);
        ativo = (TextView) findViewById(R.id.detalhe_ativo);

        pessoa = getIntent().getParcelableExtra(Constants.PERSON);

        if (pessoa != null) {
            nome.setText(pessoa.getNome());
            sobrenome.setText(pessoa.getSobrenome());
            dataNascimento.setText(DataHelper.getLongDate(Long.valueOf(pessoa.getDataNascimento())));
            ativo.setText(pessoa.getAtivo() == 0 ? getString(R.string.inativo) : getString(R.string.ativo));
        }
    }
}
