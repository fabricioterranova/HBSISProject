package com.fabriciooliveira.hbsisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fabriciooliveira.hbsisproject.R;
import com.fabriciooliveira.hbsisproject.database.PessoaDB;
import com.fabriciooliveira.hbsisproject.model.Pessoa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fabriciooliveira on 1/22/16.
 */
public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.ViewHolderPessoa>{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Pessoa> mListPessoas = new ArrayList<Pessoa>();
    private PessoaDB mPessoaDB;


    public PessoaAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mPessoaDB = new PessoaDB(mContext);
        mListPessoas = mPessoaDB.getPessoaList();
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
    public void onBindViewHolder(ViewHolderPessoa holder, final int position) {
        final Pessoa pessoaAtual = mListPessoas.get(position);

        holder.mNome.setText(pessoaAtual.getNome());

        long dataNascimento = Long.parseLong(pessoaAtual.getDataNascimento());
        holder.mNascimento.setText(getLongDate(dataNascimento));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    Toast.makeText(mContext, "isAtivo : " + isChecked, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "isAtivo : " + isChecked, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static String getLongDate(long longValue) {
        Date date=new Date(longValue);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mListPessoas.size();
    }

    static class ViewHolderPessoa extends RecyclerView.ViewHolder{

        private TextView mNome;
        private TextView mNascimento;
        private CheckBox mCheckBox;

        public ViewHolderPessoa(View itemView) {
            super(itemView);

            mNome = (TextView) itemView.findViewById(R.id.pessoa_nome);
            mNascimento = (TextView) itemView.findViewById(R.id.pessoa_nascimento);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_ativo);
        }

    }
}
