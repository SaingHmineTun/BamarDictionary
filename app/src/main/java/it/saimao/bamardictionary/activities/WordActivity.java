package it.saimao.bamardictionary.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import java.util.Locale;

import it.saimao.bamardictionary.DictionaryDatabase;
import it.saimao.bamardictionary.R;
import it.saimao.bamardictionary.dao.DictionaryDao;
import it.saimao.bamardictionary.dao.FavouriteDao;
import it.saimao.bamardictionary.databinding.ActivityWordBinding;
import it.saimao.bamardictionary.entities.DictionaryEntity;
import it.saimao.bamardictionary.entities.FavoriteEntity;

public class WordActivity extends AppCompatActivity {
    private ActivityWordBinding binding;
    private DictionaryDao dictionaryDao;
    private FavouriteDao favouriteDao;
    private TextToSpeech tts;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initTtsEngine();
        initData();
        initActions();
    }

    private void initTtsEngine() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(WordActivity.this, "Language not supported!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initActions() {
        binding.ivSpeaker.setOnClickListener(v -> {
            if (tts != null && tts.getEngines().size() > 0) {
                tts.speak(binding.tvWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                Toast.makeText(this, "Text-to-Speech not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivFavourite.setOnClickListener(v -> {
            FavoriteEntity favoriteEntity = new FavoriteEntity();
            favoriteEntity.setId(id);
            if (favouriteDao.getById(id) != null) { // is favourite: un-favourite it
                favouriteDao.delete(favoriteEntity);
                binding.ivFavourite.setImageResource(R.drawable.ic_favourite);
            } else {
                favouriteDao.insert(favoriteEntity);
                binding.ivFavourite.setImageResource(R.drawable.ic_fill_favourite);
            }
        });

    }


    private void initData() {
        dictionaryDao = DictionaryDatabase.getInstance(this).dictionaryDao();
        favouriteDao = DictionaryDatabase.getInstance(this).favoriteDao();
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            DictionaryEntity dict = dictionaryDao.getDictionaryById(id);
            updateUi(dict);
        }
    }
    private void updateUi(DictionaryEntity dict) {
        binding.tvWord.setText(dict.getStripWord());
        binding.tvMeaning.setText(Html.fromHtml(dict.getDefinition(), 0));
        addSynonyms(dict);
        addKeywords(dict);
        addImage(dict.getPicture());
        initFavourite(dict.getId());
    }
    private void initFavourite(int id) {
        if (favouriteDao.getById(id) != null) {
            binding.ivFavourite.setImageResource(R.drawable.ic_fill_favourite);
        } else {
            binding.ivFavourite.setImageResource(R.drawable.ic_favourite);
        }
    }

    private void addImage(String picture) {
        if (picture == null || picture.isBlank()) {
            binding.ivWord.setVisibility(View.GONE);
        } else {
            binding.ivWord.setVisibility(View.VISIBLE);
            picture = picture.substring(picture.indexOf(","));
            var decodedString = Base64.decode(picture, Base64.DEFAULT);
            var bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            binding.ivWord.setImageBitmap(bitmap);
        }
    }

    private void addSynonyms(DictionaryEntity dictionaryEntity) {
        if (dictionaryEntity.getSynonyms() != null && !dictionaryEntity.getSynonyms().isBlank()) {
            binding.lySynonyms.setVisibility(View.VISIBLE);
            binding.cgSynonyms.removeAllViews();
            String[] synonyms = dictionaryEntity.getSynonyms().split(",");
            for (String syn : synonyms) {
                Chip chip = new Chip(this);
                chip.setText(syn);
                chip.setOnClickListener(v -> changeWord(syn));
                binding.cgSynonyms.addView(chip);
            }
        } else {
            binding.lySynonyms.setVisibility(View.GONE);
        }
    }

    private void addKeywords(DictionaryEntity dictionaryEntity) {
        if (dictionaryEntity.getKeywords() != null && !dictionaryEntity.getKeywords().isBlank()) {
            binding.lyKeywords.setVisibility(View.VISIBLE);
            binding.cgKeywords.removeAllViews();
            String[] keywords = dictionaryEntity.getKeywords().split(",");
            for (String kw : keywords) {
                Chip chip = new Chip(this);
                chip.setText(kw);
                chip.setOnClickListener(v -> changeWord(kw));
                binding.cgKeywords.addView(chip);
            }
        } else {
            binding.lyKeywords.setVisibility(View.GONE);
        }
    }

    private void changeWord(String word) {
        var dict = dictionaryDao.getDictionaryByStripWordExactly(word.toLowerCase());
        if (dict == null) {
            Toast.makeText(this, "Cannot find word for " + word, Toast.LENGTH_SHORT).show();
        } else {
            updateUi(dict);
        }
    }
}