package it.saimao.bamardictionary.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import it.saimao.bamardictionary.DictionaryDatabase;
import it.saimao.bamardictionary.R;
import it.saimao.bamardictionary.adapters.DictionaryAdapter;
import it.saimao.bamardictionary.dao.DictionaryDao;
import it.saimao.bamardictionary.dao.FavouriteDao;
import it.saimao.bamardictionary.databinding.FragmentHomeBinding;
import it.saimao.bamardictionary.entities.DictionaryEntity;
import it.saimao.bamardictionary.entities.FavoriteEntity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DictionaryDao dictionaryDao;
    private FavouriteDao favouriteDao;
    private DictionaryAdapter dictionaryAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        initDaos();
        initAdapter();
        initActions();
        binding.etSearchWord.setText("");
        return binding.getRoot();
    }

    private void initAdapter() {
        dictionaryAdapter = new DictionaryAdapter(favouriteDao);
        binding.rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWords.setAdapter(dictionaryAdapter);
    }

    private void initDaos() {
        dictionaryDao = DictionaryDatabase.getInstance(getContext()).dictionaryDao();
        favouriteDao = DictionaryDatabase.getInstance(getContext()).favoriteDao();
    }

    private void initActions() {
        binding.etSearchWord.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word = s.toString();
                List<DictionaryEntity> list;
                if (word.isEmpty()) {
                    list = dictionaryDao.getAllDictionary();
                } else {
                    list = dictionaryDao.getDictionaryByStripWord(word + "%");
                }
                dictionaryAdapter.submitList(list);
            }
        });
    }
}