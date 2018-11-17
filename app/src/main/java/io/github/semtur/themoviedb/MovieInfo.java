package io.github.semtur.themoviedb;

import com.google.gson.annotations.SerializedName;

public class MovieInfo {
    @SerializedName("id")
    private String mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("tagline")
    private String mTagline;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("vote_count")
    private String mVoteCount;
    @SerializedName("vote_average")
    private String mVoteAverage;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("imdb_id")
    private String mImdbId;

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

    public String getTagline() {
        return mTagline;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return "Overview: " + mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        String[] date = mReleaseDate.split("-");
        StringBuilder sb = new StringBuilder("Release date: ")
                .append(date[2])
                .append('.')
                .append(date[1])
                .append('.')
                .append(date[0]);
        return sb.toString();
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
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

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public String getPosterUrl() {
        return new StringBuilder("http://image.tmdb.org/t/p/w500/")
                .append(mPosterPath)
                .toString();
    }
    public String getRating() {
        return new StringBuilder()
                .append("Rating: ")
                .append(mVoteAverage)
                .append("/10 (")
                .append(mVoteCount)
                .append(" votes)")
                .toString();

    }
}
