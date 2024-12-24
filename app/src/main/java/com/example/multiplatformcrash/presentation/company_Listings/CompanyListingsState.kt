package com.example.multiplatformcrash.presentation.company_Listings

import com.example.multiplatformcrash.domain.model.CompanyListing

data class CompanyListingsState(
    val companies:List<CompanyListing> = emptyList(),
    val isLoading:Boolean=false,
    val isRefreshing:Boolean=false,
    val searchQuery :String=""

)
