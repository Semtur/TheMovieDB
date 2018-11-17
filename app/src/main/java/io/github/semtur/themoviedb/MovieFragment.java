package io.github.semtur.themoviedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieFragment extends Fragment {
    private static final String ARG_MOVIE_ID = "movie_id";

    public static MovieFragment newInstance(String movieId) {
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieId);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        String movieId = getArguments().getString(ARG_MOVIE_ID);
        final MovieInfo movie = MovieLibrary.getInstance().getMovieInfo(movieId);
        TextView txtTitle = view.findViewById(R.id.txt_title);
        String title = movie.getTitle();
        String titleOrig = movie.getOriginalTitle();
        if (!title.equals(titleOrig)) {
            title += "/" + titleOrig;
        }
        txtTitle.setText(title);
        TextView txtTagline = view.findViewById(R.id.txt_tagline);
        txtTagline.setText(movie.getTagline());
        TextView txtRating = view.findViewById(R.id.txt_rating);
        txtRating.setText(movie.getRating());
        TextView txtDate = view.findViewById(R.id.txt_release_date);
        txtDate.setText(movie.getReleaseDate());
        TextView txtOverview = view.findViewById(R.id.txt_overview);
        txtOverview.setText(movie.getOverview());
        ImageView imgIMDb = view.findViewById(R.id.img_imdb);
        imgIMDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.imdb.com/title/" + movie.getImdbId();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ImageView imgPoster = view.findViewById(R.id.img_movie_poster);
        Picasso.get()
                .load(movie.getPosterUrl())
                .fit()
                .into(imgPoster);
        return view;
    }
}

