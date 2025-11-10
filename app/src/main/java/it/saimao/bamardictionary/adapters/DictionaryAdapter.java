package it.saimao.bamardictionary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.saimao.bamardictionary.activities.WordActivity;
import it.saimao.bamardictionary.databinding.ItemDictionaryBinding;
import it.saimao.bamardictionary.entities.DictionaryEntity;

public class DictionaryAdapter extends ListAdapter<DictionaryEntity, DictionaryAdapter.DictionaryViewHolder> {
    private static final DiffUtil.ItemCallback<DictionaryEntity> itemCallback = new DiffUtil.ItemCallback<DictionaryEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull DictionaryEntity oldItem, @NonNull DictionaryEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DictionaryEntity oldItem, @NonNull DictionaryEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public DictionaryAdapter() {
        super(itemCallback);
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
            binding.getRoot().setOnClickListener(v -> {
                Context context = v.getContext();
                Intent it = new Intent(context, WordActivity.class);
                it.putExtra("id", dictionaryEntity.getId());
                context.startActivity(it);
            });
        }

    }
}