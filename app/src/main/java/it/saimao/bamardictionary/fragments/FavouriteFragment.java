package it.saimao.bamardictionary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import it.saimao.bamardictionary.DictionaryDatabase;
import it.saimao.bamardictionary.adapters.DictionaryAdapter;
import it.saimao.bamardictionary.dao.DictionaryDao;
import it.saimao.bamardictionary.dao.FavouriteDao;
import it.saimao.bamardictionary.databinding.FragmentFavouriteBinding;
import it.saimao.bamardictionary.entities.DictionaryEntity;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    private DictionaryAdapter adapter;
    private FavouriteDao favouriteDao;
    private DictionaryDao dictionaryDao;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDao();
        initRecyclerView();
        updateList();
    }

    private void initDao() {
        favouriteDao = DictionaryDatabase.getInstance(getContext()).favoriteDao();
        dictionaryDao = DictionaryDatabase.getInstance(getContext()).dictionaryDao();
    }

    private void updateList() {
        var favourites = favouriteDao.getAll();
        List<DictionaryEntity> dictionaries = new ArrayList<>();
        for (var fav : favourites) {
            DictionaryEntity dictionary = dictionaryDao.getDictionaryById(fav.getId());
            dictionaries.add(dictionary);
        }
        adapter.submitList(dictionaries);
    }

    private void initRecyclerView() {
        adapter = new DictionaryAdapter(favouriteDao);
        binding.rvFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFavourite.setAdapter(adapter);
        adapter.setOnFavouriteRemoveListener(id -> updateList());
    }
}