package com.example.multiplatformcrash.presentation.company_Listings

sealed class CompanyListingsEvent{
    object Refresh:CompanyListingsEvent()
    data class OnSearchQueryChange(val query:String) : CompanyListingsEvent()
}
