package com.example.moviecataloguefordicoding.network.response;


import com.example.moviecataloguefordicoding.model.ModelTV;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVResponse {

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
    private List<ModelTV> results = null;



    public List<ModelTV> getResults() {
        return results;
    }



}