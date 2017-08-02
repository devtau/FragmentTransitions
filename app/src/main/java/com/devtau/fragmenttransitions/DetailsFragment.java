package com.devtau.fragmenttransitions;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailsFragment extends Fragment {

    private static final String ARG_KITTEN_NUMBER = "argKittenNumber";

    public static DetailsFragment newInstance(@IntRange(from = 1, to = 6) int kittenNumber) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);
        fragment.setArguments(args);
        return fragment;
    }

    //анимацию перехода можно назначать во время компоновки транзакции в MainActivity или в onCreate DetailsFragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            Transition changeTransform = TransitionInflater.from(getContext()).inflateTransition(R.transition.transition);
            setSharedElementEnterTransition(changeTransform);
            //по умолчанию возврат к списку анимируется так же
//            setSharedElementReturnTransition(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        int kittenNumber = getArguments().getInt(ARG_KITTEN_NUMBER);
        Glide.with(this)
                .load(MainActivity.getDrawableFromNumber(kittenNumber))
                .transition(withCrossFade())
                .into(image);
        return view;
    }
}
