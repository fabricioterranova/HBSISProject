package com.fabriciooliveira.hbsisproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fabriciooliveira.hbsisproject.BusinessObject.PessoaBO;
import com.fabriciooliveira.hbsisproject.R;
import com.fabriciooliveira.hbsisproject.model.Pessoa;
import com.fabriciooliveira.hbsisproject.ui.DateHelper;
import com.fabriciooliveira.hbsisproject.ui.PessoaDetalhesActivity;
import com.fabriciooliveira.hbsisproject.util.Constants;

import java.util.ArrayList;

/**
 * Created by fabriciooliveira on 1/22/16.
 */
public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.ViewHolderPessoa>{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static ArrayList<Pessoa> mListPessoas = new ArrayList<Pessoa>();
    private PessoaBO mPessoaDO;


    public PessoaAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mPessoaDO = new PessoaBO(mContext);
        mListPessoas = mPessoaDO.recuperarListaDePessoas();
    }

    public void setmListPessoas(ArrayList<Pessoa> mListPessoas) {
        this.mListPessoas = mListPessoas;
    }

    @Override
    public ViewHolderPessoa onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.pessoa_celula, parent, false);
        ViewHolderPessoa viewHolderPayment = new ViewHolderPessoa(view);

        return viewHolderPayment;
    }

    @Override
    public void onBindViewHolder(final ViewHolderPessoa holder, final int position) {
        final Pessoa pessoaAtual = mListPessoas.get(position);

        holder.mNome.setText(pessoaAtual.getNome());

        long dataNascimento = Long.parseLong(pessoaAtual.getDataNascimento());
        holder.mNascimento.setText(DateHelper.formatarDataLonga(dataNascimento));

        holder.mCheckBox.setChecked(pessoaAtual.getAtivo() == 0 ? false : true);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pessoaAtual.setAtivo(1);
                    mListPessoas.set(position, pessoaAtual);
                    mPessoaDO.atualizarPessoaPorID(mListPessoas.get(position));
                } else {
                    pessoaAtual.setAtivo(0);
                    mListPessoas.set(position, pessoaAtual);
                    mPessoaDO.atualizarPessoaPorID(mListPessoas.get(position));
                }
            }
        });

        holder.mBotaoSalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PessoaDetalhesActivity.class);
                intent.putExtra(Constants.PERSON, pessoaAtual);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListPessoas.size();
    }

    static class ViewHolderPessoa extends RecyclerView.ViewHolder{

        private TextView mNome;
        private TextView mNascimento;
        private CheckBox mCheckBox;
        private Button mBotaoSalvar;

        public ViewHolderPessoa(View itemView) {
            super(itemView);

            mNome = (TextView) itemView.findViewById(R.id.pessoa_nome);
            mNascimento = (TextView) itemView.findViewById(R.id.pessoa_nascimento);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_ativo);
            mBotaoSalvar = (Button) itemView.findViewById(R.id.botao_salvar);
        }

    }
}
