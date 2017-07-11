package com.devtau.fragmenttransitions;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.KittenViewHolder> {

    private final int mSize;
    private final KittenClickListener mListener;


    ListAdapter(int size, KittenClickListener listener) {
        mSize = size;
        mListener = listener;
    }

    @Override
    public KittenViewHolder onCreateViewHolder(ViewGroup container, int position) {
        return new KittenViewHolder(LayoutInflater.from(container.getContext()).inflate(R.layout.grid_item, container, false));
    }

    @Override
    public void onBindViewHolder(final KittenViewHolder viewHolder, final int position) {
        Glide.with(viewHolder.getContext())
				.load(MainActivity.getDrawableFromNumber(position))
				.transition(withCrossFade())
				.into(viewHolder.image);
		viewHolder.root.setOnClickListener(v -> mListener.onKittenClicked(viewHolder.image, position));

        // It is important that each shared element in the source screen has a unique transition name.
        // For example, we can't just give all the images in our grid the transition name "kittenImage"
        // because then we would have conflicting transition names.
		//назначенный здесь идентификатор больше нигде не используется. только самим фреймворком
        ViewCompat.setTransitionName(viewHolder.image, String.valueOf(position) + "_image");
    }

    @Override
    public int getItemCount() {
        return mSize;
    }



	interface KittenClickListener {
		void onKittenClicked(ImageView imageView, int kittenNumber);
	}



	class KittenViewHolder extends RecyclerView.ViewHolder {
        View root;
		ImageView image;

		KittenViewHolder(View root) {
			super(root);
            this.root = root;
			image = (ImageView) root.findViewById(R.id.image);
		}

		Context getContext() { return root.getContext();
        }
	}
}
