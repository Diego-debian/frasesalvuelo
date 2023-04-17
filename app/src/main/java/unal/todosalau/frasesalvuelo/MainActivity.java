package unal.todosalau.frasesalvuelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import unal.todosalau.frasesalvuelo.adaptadores.PhrasesAdapter;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private TextView mNoPhrasesTextView;
    private RecyclerView mPhrasesRecyclerView;
    private PhrasesAdapter mPhrasesAdapter;
    private ArrayList<String> mPhrasesList;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String phrase = intent.getStringExtra(MyFirebaseMessagingService.EXTRA_PHRASE);

            mPhrasesList.add(phrase);
            mPhrasesAdapter.notifyDataSetChanged();

            // Ocultar el mensaje de "No phrases yet"
            mNoPhrasesTextView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);mProgressBar = findViewById(R.id.progress_bar);
        mNoPhrasesTextView = findViewById(R.id.no_phrases_text_view);
        mPhrasesRecyclerView = findViewById(R.id.phrases_recycler_view);

        mPhrasesList = new ArrayList<>();
        mPhrasesAdapter = new PhrasesAdapter(this, mPhrasesList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPhrasesRecyclerView.setLayoutManager(linearLayoutManager);
        mPhrasesRecyclerView.setAdapter(mPhrasesAdapter);

        // Registrar el receptor de difusión local para recibir las frases inspiradoras
        IntentFilter intentFilter = new IntentFilter(MyFirebaseMessagingService.ACTION_NEW_PHRASE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);

        // Mostrar el ProgressBar mientras se cargan las frases inspiradoras
        mProgressBar.setVisibility(View.VISIBLE);
        mNoPhrasesTextView.setVisibility(View.GONE);
        mPhrasesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Deregistrar el receptor de difusión local
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}