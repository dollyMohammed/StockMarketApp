package com.example.multiplatformcrash.di

import com.example.multiplatformcrash.data.csv.CSVParser
import com.example.multiplatformcrash.data.csv.CompanyListingsParser
import com.example.multiplatformcrash.data.csv.IntradayInfoParser
import com.example.multiplatformcrash.data.repository.StockRepositoryImpl
import com.example.multiplatformcrash.domain.model.CompanyListing
import com.example.multiplatformcrash.domain.model.IntradayInfo
import com.example.multiplatformcrash.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun binCompanyListingsParser(
   intradayInfoParser: IntradayInfoParser
  ):CSVParser<IntradayInfo>

 @Binds
 @Singleton
 abstract fun bindIntradayInfoParser(
  companyListingsParsers:CompanyListingsParser
 ):CSVParser<CompanyListing>

 @Binds
  @Singleton
  abstract fun bindStockRepository(
   stockRepositoryImpl: StockRepositoryImpl
  ):StockRepository
}