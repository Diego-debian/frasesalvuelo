package unal.todosalau.frasesalvuelo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import unal.todosalau.frasesalvuelo.R;

public class PhrasesAdapter extends RecyclerView.Adapter<PhrasesAdapter.PhraseViewHolder> {

    private Context mContext;
    private ArrayList<String> mPhrases;

    public PhrasesAdapter(Context context, ArrayList<String> phrases) {
        mContext = context;
        mPhrases = phrases;
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_phrase, parent, false);
        return new PhraseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {
        String phrase = mPhrases.get(position);
        holder.bind(phrase);
    }

    @Override
    public int getItemCount() {
        return mPhrases.size();
    }

    class PhraseViewHolder extends RecyclerView.ViewHolder {

        private TextView mPhraseTextView;

        public PhraseViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhraseTextView = itemView.findViewById(R.id.phrase_text_view);
        }

        public void bind(String phrase) {
            mPhraseTextView.setText(phrase);
        }
    }
}