package com.fabriciooliveira.hbsisproject.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fabriciooliveira.hbsisproject.AppController;
import com.fabriciooliveira.hbsisproject.R;
import com.fabriciooliveira.hbsisproject.adapter.PessoaAdapter;
import com.fabriciooliveira.hbsisproject.model.Pessoa;
import com.fabriciooliveira.hbsisproject.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private PessoaAdapter mPessoaAdapter;
    private ProgressDialog mProgressDialog;

    private ArrayList<Pessoa> mListPessoas = new ArrayList<Pessoa>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(getString(R.string.app_name));
        mProgressDialog.show();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mPessoaAdapter = new PessoaAdapter(MainActivity.this);

        mRecyclerView.setAdapter(mPessoaAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.app_name));

        if(savedInstanceState != null){
            mListPessoas = savedInstanceState.getParcelableArrayList(Constants.PERSON_STATE);
            mPessoaAdapter.setmListPessoas(mListPessoas);
            //mProgressBar.setVisibility(View.INVISIBLE);
            hideDialog();
        }else{
            requestURLForPeople();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.PERSON_STATE, mListPessoas);
    }

    public void requestURLForPeople() {

        JsonArrayRequest requestCategories = new JsonArrayRequest(Request.Method.GET, Constants.URL_FOR_PEOPLE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();

                Type fooType = new TypeToken<ArrayList<Pessoa>>() {}.getType();
                List<Pessoa> deserializedList = new Gson().fromJson(response.toString(), fooType);

                for (Pessoa p : deserializedList) {
                    mListPessoas.add(p);
                    mPessoaAdapter.setmListPessoas(mListPessoas);
                }

                mPessoaAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Erro: " + error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(requestCategories);
    }

    private void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_backup:
//                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "Fazer Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_sair:
                Toast.makeText(MainActivity.this, "Fazer Backup", Toast.LENGTH_SHORT).show();

                finish();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
}
