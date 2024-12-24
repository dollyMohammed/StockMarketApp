package com.example.multiplatformcrash.domain.repository

import com.example.multiplatformcrash.domain.model.CompanyInfo
import com.example.multiplatformcrash.domain.model.CompanyListing
import com.example.multiplatformcrash.domain.model.IntradayInfo
import com.example.multiplatformcrash.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query: String

    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol:String,

    ):Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ):Resource<CompanyInfo>
}