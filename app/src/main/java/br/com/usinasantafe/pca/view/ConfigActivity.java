package br.com.usinasantafe.pca.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pca.PCAContext;
import br.com.usinasantafe.pca.R;
import br.com.usinasantafe.pca.util.ConexaoWeb;

public class ConfigActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextEquipConfig;
    private EditText editTextSenhaConfig;
    private PCAContext pcaContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button btOkConfig =  (Button) findViewById(R.id.buttonSalvarConfig );
        Button btCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarBD);
        editTextEquipConfig = (EditText)  findViewById(R.id.editTextEquipConfig);
        editTextSenhaConfig = (EditText)  findViewById(R.id.editTextSenhaConfig);

        pcaContext = (PCAContext) getApplication();

        if (pcaContext.getConfigCTR().hasElements()) {
            editTextEquipConfig.setText(String.valueOf(pcaContext.getConfigCTR().getConfig().getNroAparelhoConfig()));
            editTextSenhaConfig.setText(pcaContext.getConfigCTR().getConfig().getSenhaConfig());
        }

        btOkConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editTextEquipConfig.getText().toString().equals("") &&
                        !editTextSenhaConfig.getText().toString().equals("")){

                    pcaContext.getConfigCTR().salvarConfig(Long.parseLong(editTextEquipConfig.getText().toString()), editTextSenhaConfig.getText().toString());

                    Intent it = new Intent(ConfigActivity.this, MenuInicialActivity.class);
                    startActivity(it);
                    finish();

                }

            }
        });

        btCancConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ConfigActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }
        });

        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if(conexaoWeb.verificaConexao(ConfigActivity.this)){

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    pcaContext.getConfigCTR().atualTodasTabelas(ConfigActivity.this, progressBar);

                }
                else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();
                }
            }
        });

    }

    public void onBackPressed() {
    }

}