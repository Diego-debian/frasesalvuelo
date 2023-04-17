package unal.todosalau.frasesalvuelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class SendPhraseActivity extends AppCompatActivity {

    private EditText mPhraseEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phrase);

        mPhraseEditText = findViewById(R.id.phrase_edit_text);
    }

    public void onSendPhraseClicked(View view) {
        String phrase = mPhraseEditText.getText().toString();

        if (phrase.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a phrase", Toast.LENGTH_SHORT).show();
            return;
        }

        // Envía la frase inspiradora a través de Firebase Cloud Messaging
        FirebaseMessaging.getInstance().send(new RemoteMessage.Builder("SENDER_ID@fcm.googleapis.com")
                .setMessageId(Integer.toString(new Random().nextInt(999999)))
                .addData("phrase", phrase)
                .build());

        Toast.makeText(this, "Phrase sent", Toast.LENGTH_SHORT).show();
        finish();
    }
}