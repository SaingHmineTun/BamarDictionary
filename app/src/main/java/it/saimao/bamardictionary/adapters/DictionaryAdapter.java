package it.saimao.bamardictionary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.saimao.bamardictionary.R;
import it.saimao.bamardictionary.activities.WordActivity;
import it.saimao.bamardictionary.dao.FavouriteDao;
import it.saimao.bamardictionary.databinding.ItemDictionaryBinding;
import it.saimao.bamardictionary.entities.DictionaryEntity;
import it.saimao.bamardictionary.entities.FavoriteEntity;
import it.saimao.bamardictionary.fragments.FavouriteFragment;

public class DictionaryAdapter extends ListAdapter<DictionaryEntity, DictionaryAdapter.DictionaryViewHolder> {

    public interface OnFavouriteRemoveListener {
        void onFavouriteRemove(int id);
    }

    private OnFavouriteRemoveListener onFavouriteRemoveListener;

    public void setOnFavouriteRemoveListener(OnFavouriteRemoveListener onFavouriteRemoveListener) {
        this.onFavouriteRemoveListener = onFavouriteRemoveListener;
    }

    private static final DiffUtil.ItemCallback<DictionaryEntity> itemCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull DictionaryEntity oldItem, @NonNull DictionaryEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DictionaryEntity oldItem, @NonNull DictionaryEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final FavouriteDao favouriteDao;

    public DictionaryAdapter(FavouriteDao favouriteDao) {
        super(itemCallback);
        this.favouriteDao = favouriteDao;
    }

    @NonNull
    @Override
    public DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DictionaryViewHolder(ItemDictionaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }

    public class DictionaryViewHolder extends RecyclerView.ViewHolder {
        private ItemDictionaryBinding binding;

        public DictionaryViewHolder(ItemDictionaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DictionaryEntity dictionaryEntity) {
            binding.tvWord.setText(dictionaryEntity.getWord());
            binding.tvSubTitle.setText(dictionaryEntity.getTitle());
            if (favouriteDao.getById(dictionaryEntity.getId()) != null) { // is favourite
                binding.ivFavourite.setImageResource(R.drawable.ic_fill_favourite);
            } else {
                binding.ivFavourite.setImageResource(R.drawable.ic_favourite);
            }

            binding.getRoot().setOnClickListener(v -> {
                Context context = v.getContext();
                Intent it = new Intent(context, WordActivity.class);
                it.putExtra("id", dictionaryEntity.getId());
                context.startActivity(it);
            });

            binding.ivFavourite.setOnClickListener(v -> {
                FavoriteEntity favoriteEntity = new FavoriteEntity();
                favoriteEntity.setId(dictionaryEntity.getId());
                if (favouriteDao.getById(dictionaryEntity.getId()) != null) { // is favourite: un-favourite it
                    favouriteDao.delete(favoriteEntity);
                    binding.ivFavourite.setImageResource(R.drawable.ic_favourite);
                } else {
                    favouriteDao.insert(favoriteEntity);
                    binding.ivFavourite.setImageResource(R.drawable.ic_fill_favourite);
                }
                if (onFavouriteRemoveListener != null) {
                    onFavouriteRemoveListener.onFavouriteRemove(favoriteEntity.getId());
                }
            });

        }

    }
}