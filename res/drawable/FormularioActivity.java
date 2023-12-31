package res.drawable;

public class FormularioActivity {
    public class FormularioActivity extends Activity {

        private FormularioHelper helper;
        private String localArquivoFoto;
        private static final int TIRA_FOTO = 123;

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
                }
            });

            ImageView foto = helper.getBotaoImagem();
            foto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    localArquivoFoto = Environment.getExternalStorageDirectory() +
                            "/"+ System.currentTimeMillis()+".jpg";

                    File arquivo = new File(localArquivoFoto);
                    Uri localFoto = Uri.fromFile(arquivo);

                    Intent irParaCamera =
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                    startActivityForResult(irParaCamera, TIRA_FOTO);

                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == TIRA_FOTO) {
                if (resultCode == Activity.RESULT_OK) {
                    helper.carregaImagem(this.localArquivoFoto);
                } else {
                    this.localArquivoFoto = null;
                }
            }
        }
    }


}
