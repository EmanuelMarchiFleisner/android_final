package Receiver;

public class SMSReceiver {
    package br.edu.unidavi.bsn.receiver;

//imports

    public class SMSReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            Object[] messages = (Object[]) bundle.get("pdus");
            byte[] message = (byte[]) messages[0];

            SmsMessage sms = SmsMessage.createFromPdu( message );
            String telefone = sms.getDisplayOriginatingAddress();

            AlunoDAO dao = new AlunoDAO(context);

            if (dao.isAluno(telefone)) {
                MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
                mp.start();

                Toast.makeText(context, "SMS recebido do aluno de telefone: "+
                        telefone, Toast.LENGTH_LONG).show();
            }

            dao.close();
        }
        MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();


    }

}

