package io.github.semtur.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MoviePagerActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_ID = "movie_id";
    private String mMovieId;
    private List<MovieInfo> mMovies;

    public static final Intent newIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MoviePagerActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);
        mMovieId = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        ViewPager viewPager = findViewById(R.id.view_pager);
        mMovies = MovieLibrary.getInstance().getMoviesInfo();

        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                MovieInfo movie = mMovies.get(position);
                return MovieFragment.newInstance(movie.getId());
            }

            @Override
            public int getCount() {
                return mMovies.size();
            }
        });

        for (int i = 0; i < mMovies.size(); i++) {
            MovieInfo movie = mMovies.get(i);
            if (movie.getId().equals(mMovieId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
