package io.github.semtur.themoviedb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieLibrary {
    private static MovieLibrary MOVIE_LIBRARY;
    private List<MovieLibItem> mMovieLib;
    private List<MovieInfo> mMoviesInfo;

    private MovieLibrary() {
        mMovieLib = new ArrayList<>();
        mMoviesInfo = new ArrayList<>();
    }

    public static MovieLibrary getInstance() {
        if (MOVIE_LIBRARY == null) {
            MOVIE_LIBRARY = new MovieLibrary();
        }
        return MOVIE_LIBRARY;
    }

    public void addMovie(MovieLibItem item) {
        mMovieLib.add(item);
    }

    public List<MovieLibItem> getMovieLib() {
        return mMovieLib;
    }

    public void addMovie(MovieInfo movieInfo) {
        mMoviesInfo.add(movieInfo);
    }

    public List<MovieInfo> getMoviesInfo() {
        return mMoviesInfo;
    }

    public MovieInfo getMovieInfo(String movieId) {
        for (MovieInfo m : mMoviesInfo) {
            if (m.getId().equals(movieId)) {
                return m;
            }
        }
        return null;
    }
}
