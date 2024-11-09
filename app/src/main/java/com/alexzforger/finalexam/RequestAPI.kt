package com.alexzforger.finalexam

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ensureActive
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.coroutineContext

object RequestAPI {
    private fun okHttpClient(): OkHttpClient
    {
        return OkHttpClient.Builder()
            .build()
    }

    suspend fun getStateCountryDataset(): USAStateCountryDataset {
        val reqBuilder = Request.Builder()
            .url("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest")
            .get()

        val response = okHttpClient().newCall(reqBuilder.build())
            .execute()

        if (response.code != 200) {
            throw Exception("HTTP Failed!")
        }

        val results = Gson().fromJson<USAStateCountryDataset>(
            response.body!!.string(),
            TypeToken.getParameterized(USAStateCountryDataset::class.java).type
        )

        coroutineContext.ensureActive()

        return results

    }

    suspend fun getGeocodedState(stateMetadata: StateMetadata): CityGeoCodingSearchResult {
        val mappedState = mapOfStateSlugAndFlags[stateMetadata.stateSlug] ?: throw Exception("Cannot find the state slug and flags")
        if (mappedState.stateCode == "PR") {
            // hardcoded because cannot be found
            return CityGeoCodingSearchResult(
                "San Juan",
                18.4041587,
                -66.142982
            )
        }

        if (mappedState.stateCode == "DC") {
            // hardcoded because cannot be found
            return CityGeoCodingSearchResult(
                "Washington D.C",
                38.8936511,
                -77.1702943
            )
        }

        val url = "https://api.api-ninjas.com/v1/geocoding".toHttpUrl().newBuilder()
            .addQueryParameter("state", stateMetadata.state)
            .addQueryParameter("country", "US")
            .addQueryParameter("city", mappedState.capital)
            .build()

        val reqBuilder = Request.Builder()
            .url(url)
            .addHeader("X-Api-Key", BuildConfig.GEOCODING_API_KEY)
            .get()

        val response = okHttpClient().newCall(reqBuilder.build())
            .execute()

        if (response.code != 200) {
            throw Exception("HTTP Failed!")
        }

        val results = Gson().fromJson<List<CityGeoCodingSearchResult>>(
            response.body!!.string(),
            TypeToken.getParameterized(List::class.java, CityGeoCodingSearchResult::class.java).type
        )

        coroutineContext.ensureActive()

        if (results.isEmpty()) {
            throw Exception("The city cannot be found, or it is invalid.")
        }

        return results[0]
    }

}