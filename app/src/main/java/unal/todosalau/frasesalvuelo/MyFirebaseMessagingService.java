package unal.todosalau.frasesalvuelo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import unal.todosalau.frasesalvuelo.repositorios.PhrasesRepository;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String ACTION_NEW_PHRASE = "com.example.frasesalvuelo.ACTION_NEW_PHRASE";
    public static final String EXTRA_PHRASE = "com.example.frasesalvuelo.EXTRA_PHRASE";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String phrase = intent.getStringExtra(MyFirebaseMessagingService.EXTRA_PHRASE);

            // Actualizar la lista de frases inspiradoras
            PhrasesRepository.getInstance().addPhrase(phrase);
        }
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String phrase = remoteMessage.getNotification().getBody();

            // Enviar el mensaje de difusión local con la nueva frase
            Intent intent = new Intent(ACTION_NEW_PHRASE);
            intent.putExtra(EXTRA_PHRASE, phrase);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // Aquí puedes manejar el nuevo token de registro para actualizarlo en tu servidor
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Registrar el receptor de difusión local para recibir las frases inspiradoras
        IntentFilter intentFilter = new IntentFilter(ACTION_NEW_PHRASE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Deregistrar el receptor de difusión local
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}