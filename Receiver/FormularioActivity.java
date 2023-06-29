package Receiver;

public class FormularioActivity { package br.edu.unidavi.bsn.activities;

import java.io.Serializable;
import com.example.listagemdealunos.R;
import br.edu.unidavi.bsn.dao.AlunoDAO;
import br.edu.unidavi.bsn.helpers.FormularioHelper;
import br.edu.unidavi.bsn.model.Aluno;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

    public class FormularioActivity extends Activity {

        private FormularioHelper helper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_formulario);

            Intent intent = getIntent();
            final Aluno alunoParaSerAlterado = (Aluno) intent.getSerializableExtra("alunoSelecionado");

            this.helper = new FormularioHelper(this);

            Button botao = (Button) findViewById(R.id.botao);

            if (alunoParaSerAlterado != null) {
                botao.setText("Alterar");
                helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
                Toast.makeText(this, "Aluno: "+alunoParaSerAlterado.getNome(), Toast.LENGTH_SHORT).show();
            }

            botao.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Aluno aluno = helper.pegaAlunoDoFormulario();

                    AlunoDAO dao = new AlunoDAO(FormularioActivity.this);

                    if (alunoParaSerAlterado == null) {
                        dao.insere(aluno);
                    } else {
                        aluno.setId(alunoParaSerAlterado.getId());
                        dao.altera(aluno);
                    }

                    dao.close();

                    finish();

                    Toast.makeText(FormularioActivity.this, "Objeto aluno criado!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
