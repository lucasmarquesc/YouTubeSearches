package com.unir.youtube;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edtTag;
    private TextInputEditText edtConsulta;
    private ImageButton btnSalvar;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<String> consultas;
    private SearchPreferences searchPreferences;
    private String tagEdicao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.edtTag = findViewById(R.id.edtTag);
        this.edtConsulta = findViewById(R.id.edt_busca);
        this.btnSalvar = findViewById(R.id.btnSalvar);
        this.recyclerView = findViewById(R.id.recyclerView);

        searchPreferences = new SearchPreferences(MainActivity.this);
        consultas = searchPreferences.getAllTags();

        adapter = new Adapter(consultas, searchPreferences);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        this.recyclerView.setAdapter(this.adapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtConsulta.getText().toString().isEmpty() && !edtTag.getText().toString().isEmpty()){
                    String tag = edtTag.getText().toString();
                    String consulta = edtConsulta.getText().toString();

                    if (tagEdicao != null){//Edição
                        consultas.remove(tagEdicao);
                        searchPreferences.removeSearch(tagEdicao);
                        tagEdicao = null;
                    }

                    adicionarConsulta(tag, consulta);
                    edtConsulta.getText().clear();
                    edtTag.getText().clear();
                    //Esconder teclado
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtTag.getWindowToken(), 0);

                }else{
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.setOnItemClickListener(new Adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String tag = consultas.get(position);
                String consulta = searchPreferences.getSearch(tag);
                String urlString = getString(R.string.searchURL) +
                        Uri.encode(consulta, "UTF-8");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                String tag = consultas.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setItems(R.array.dialog_itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                compartilharConsulta(tag);
                                break;
                            case 1:
                                edtTag.setText(tag);
                                edtConsulta.setText(searchPreferences.getSearch(tag));
                                tagEdicao = tag;
                                break;
                            case 2:
                                removerConsulta(tag);
                                break;
                        }
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    private void adicionarConsulta(String tag, String consulta){
        searchPreferences.saveSearch(tag, consulta);
        if (!consultas.contains(tag)){
            consultas.add(tag);
            adapter.notifyDataSetChanged();
        }
    }

    private void compartilharConsulta(String tag){
        String consulta = searchPreferences.getSearch(tag);
        String urlString = getString(R.string.searchURL) + Uri.encode(consulta, "UTF-8");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareSubject));
        intent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.shareMessage), urlString));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.shareSearch)));
    }

    private void removerConsulta(String tag){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(String.format(getString(R.string.confirmMessage), tag));
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                consultas.remove(tag);
                searchPreferences.removeSearch(tag);
                adapter.notifyDataSetChanged();
            }
        });
        builder.create().show();

    }

}