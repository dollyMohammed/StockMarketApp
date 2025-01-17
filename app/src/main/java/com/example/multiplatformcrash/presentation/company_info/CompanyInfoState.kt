package com.example.multiplatformcrash.presentation.company_info

import com.example.multiplatformcrash.domain.model.CompanyInfo
import com.example.multiplatformcrash.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos:List<IntradayInfo> = emptyList(),
    val company:CompanyInfo? = null,
    val isLoading:Boolean=false,
    val error: String?=null
)
