package com.alexzforger.finalexam

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class USAStateCountryDataset(
    val data: List<StateMetadata>,
    val source: List<SourceMetadata>
)

@Parcelize
data class StateMetadata(
    @SerializedName("ID State")
    val stateId: String,
    @SerializedName("State")
    val state: String,
    @SerializedName("ID Year", alternate = ["Year"])
    val year: Int,
    @SerializedName("Slug State")
    val stateSlug: String,
    @SerializedName("Population")
    val population: Int
): Parcelable

data class SourceMetadata(
    val measures: List<String>,
    val annotations: Annotations,
    val name: String,
    val substitutions: List<Any>
)

data class Annotations(
    @SerializedName("source_name")
    val sourceName: String,
    @SerializedName("source_description")
    val sourceDescription: String,
    @SerializedName("dataset_name")
    val datasetName: String,
    @SerializedName("dataset_link")
    val datasetLink: String,
    @SerializedName("table_id")
    val tableId: String,
    val topic: String,
    @SerializedName("subtopic")
    val subTopic: String,
)

data class CityGeoCodingSearchResult(
    val name: String,
    val latitude: Double,
    val longitude: Double
)