package io.github.semtur.themoviedb;

import com.google.gson.annotations.SerializedName;

public class MovieLibItem {
    @SerializedName("id")
    private String mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("vote_count")
    private String mVoteCount;
    @SerializedName("vote_average")
    private String mVoteAverage;
    @SerializedName("poster_path")
    private String mPosterPath;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(String voteCount) {
        mVoteCount = voteCount;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getRating() {
        return new StringBuilder()
                .append(mVoteAverage)
                .append("/10 (")
                .append(mVoteCount)
                .append(" votes)")
                .toString();

    }

    public String getPosterUrl() {
        return new StringBuilder("http://image.tmdb.org/t/p/w500/")
                .append(mPosterPath)
                .toString();
    }
}
