package com.devtau.fragmenttransitions;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements ListAdapter.KittenClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new ListFragment(), ListFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onKittenClicked(ImageView imageView, int kittenNumber) {
        DetailsFragment detailsFragment = DetailsFragment.newInstance(kittenNumber);

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition changeTransform = TransitionInflater.from(this).inflateTransition(R.transition.transition);
            detailsFragment.setSharedElementEnterTransition(changeTransform);
            ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(ListFragment.FRAGMENT_TAG);
            listFragment.setSharedElementReturnTransition(changeTransform);
            //нельзя использовать android.R.transition.fade, т.к. это будет фейдить и героя тоже
            detailsFragment.setEnterTransition(new Fade());
            listFragment.setExitTransition(new Fade());
        }

        //обязательно replace. с add не работает
        //назначенный здесь идентификатор должен совпадать с используемым в разметке DetailsFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addSharedElement(imageView, "kittenImage")
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }


    public static int getDrawableFromNumber(int kittenIndex) {
        int drawableId = 0;
        if (kittenIndex == 0) drawableId = R.drawable.kitten_1;
        else if (kittenIndex == 1) drawableId = R.drawable.kitten_2;
        else if (kittenIndex == 2) drawableId = R.drawable.kitten_3;
        else if (kittenIndex == 3) drawableId = R.drawable.kitten_4;
        else if (kittenIndex == 4) drawableId = R.drawable.kitten_5;
        else if (kittenIndex == 5) drawableId = R.drawable.kitten_6;
        return drawableId;
    }
}
