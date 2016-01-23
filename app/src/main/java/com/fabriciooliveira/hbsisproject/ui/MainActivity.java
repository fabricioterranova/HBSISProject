package com.fabriciooliveira.hbsisproject.ui;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fabriciooliveira.hbsisproject.BusinessObject.PessoaBO;
import com.fabriciooliveira.hbsisproject.R;
import com.fabriciooliveira.hbsisproject.adapter.PessoaAdapter;
import com.fabriciooliveira.hbsisproject.model.Pessoa;
import com.fabriciooliveira.hbsisproject.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static boolean sSalvarNoBancoDeDados = true;

    private RecyclerView mRecyclerView;
    private PessoaAdapter mPessoaAdapter;
    private PessoaBO mPessoaBO;

    private static ArrayList<Pessoa> mListPessoas = new ArrayList<Pessoa>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        mListPessoas = bundle.getParcelableArrayList(Constants.PERSON_LIST);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        mPessoaBO = new PessoaBO(MainActivity.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mPessoaAdapter = new PessoaAdapter(MainActivity.this);

        mRecyclerView.setAdapter(mPessoaAdapter);
        mPessoaAdapter.setmListPessoas(mListPessoas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.app_name));

        if(savedInstanceState != null){
            mListPessoas = savedInstanceState.getParcelableArrayList(Constants.PERSON_STATE);
            mPessoaAdapter.setmListPessoas(mListPessoas);
        }

        if (sSalvarNoBancoDeDados) {
            mPessoaBO.salvarListaDePessoas(mListPessoas);
            sSalvarNoBancoDeDados = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.PERSON_STATE, mListPessoas);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_backup:
                try {
                    copiarDatabaseParaPastaDownload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu_sair:
                finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getString(R.string.sair))
                .setMessage(getString(R.string.deseja_sair))
                .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton(getString(R.string.nao), null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void copiarDatabaseParaPastaDownload() throws IOException {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("backup_avaliacao_").append(String.valueOf(new Date())).append(".bkp");
            File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), builder.toString()); 
            File currentDB = getApplicationContext().getDatabasePath(Constants.DB_NAME);
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0, fis.getChannel().size());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to download folder");
                Toast.makeText(MainActivity.this, "Banco de Dados copiado com sucesso", Toast.LENGTH_SHORT).show();

            } else Log.i("Copying Database", " fail, database not found");
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
        }
    }
}
