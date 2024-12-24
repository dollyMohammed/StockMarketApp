package com.example.multiplatformcrash.data.mapper

import com.example.multiplatformcrash.data.local.CompanyListingEntity
import com.example.multiplatformcrash.data.remote.dto.CompanyInfoDto
import com.example.multiplatformcrash.domain.model.CompanyInfo
import com.example.multiplatformcrash.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing {
    return CompanyListing(
        name=name,
        symbol=symbol,
        exchange=exchange
    )

}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity {
    return CompanyListingEntity(
        name=name,
        symbol=symbol,
        exchange=exchange
    )

}
fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
       name = name ?: "" ,
        symbol = symbol ?: "",
        description = description ?: "",
        country = country ?: "",
        industry = industry ?: ""

    )
}