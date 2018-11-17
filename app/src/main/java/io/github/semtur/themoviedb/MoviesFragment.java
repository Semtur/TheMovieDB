package io.github.semtur.themoviedb;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCountForGridLM()));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    private int getSpanCountForGridLM() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 4;
        } else {
            return 2;
        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private ImageView mImgMoviePoster;
        private TextView mTxtMovieTitle;
        private TextView mTxtMovieRating;
        private MovieLibItem mItem;

        public Holder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mImgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            mTxtMovieTitle = itemView.findViewById(R.id.txt_title);
            mTxtMovieRating = itemView.findViewById(R.id.txt_movie_rating);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String movieId = mItem.getId();
                    Intent intent = MoviePagerActivity.newIntent(getActivity(), movieId);
                    startActivity(intent);
                }
            });
        }

        public void bindMovie(MovieLibItem item) {
            mItem = item;
            mTxtMovieTitle.setText(item.getTitle());
            mTxtMovieRating.setText(item.getRating());
            Picasso.get()
                    .load(item.getPosterUrl())
                    .fit()
                    .into(mImgMoviePoster);
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {
        List<MovieLibItem> mMovies;

        public Adapter() {
            MovieLibrary movieLibrary = MovieLibrary.getInstance();
            mMovies = movieLibrary.getMovieLib();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.movie_lib_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bindMovie(mMovies.get(position));
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }
    }
}
