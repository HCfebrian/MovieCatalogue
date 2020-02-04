package com.example.moviecataloguefordicoding.network.response;


import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<ModelFilm> results = null;


    public List<ModelFilm> getResults() {
        return results;
    }


}