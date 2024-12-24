package com.example.multiplatformcrash.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.multiplatformcrash.data.csv.CSVParser
import com.example.multiplatformcrash.data.local.StockDatabase
import com.example.multiplatformcrash.data.mapper.toCompanyInfo
import com.example.multiplatformcrash.data.mapper.toCompanyListing
import com.example.multiplatformcrash.data.mapper.toCompanyListingEntity
import com.example.multiplatformcrash.data.remote.StockApi
import com.example.multiplatformcrash.domain.model.CompanyInfo
import com.example.multiplatformcrash.domain.model.CompanyListing
import com.example.multiplatformcrash.domain.model.IntradayInfo
import com.example.multiplatformcrash.domain.repository.StockRepository
import com.example.multiplatformcrash.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
  private  val api:StockApi,
  private  val db:StockDatabase,
   private val companyListingsParser: CSVParser<CompanyListing> ,
    private val intradayInfoParser:CSVParser<IntradayInfo>
):StockRepository{
    private val dao = db.dao
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings=dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustificationCache= !isDbEmpty && !fetchFromRemote
            if (shouldJustificationCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
                //response.byteStream()
               // val csvReader= CSVReader(InputStreamReader(response.byteStream()))

            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null

            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null

            }
            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )

                emit(Resource.Success(
                    data=dao.searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))

            }
        }

    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response=api.getIntradayInfo(symbol)
            val results=intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        }
        catch (e:IOException){
            e.printStackTrace()
            Resource.Error(
                message = "Could not loud intraday info"
            )
        } catch (e:HttpException){
            e.printStackTrace()
            Resource.Error(
                message = "Could not loud intraday info"
            )



        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
       return try {
           val result=api.getCompanyInfo(symbol)
           Resource.Success(result.toCompanyInfo())

       }
       catch (e:IOException){
           e.printStackTrace()
           Resource.Error(
               message = "Could not loud Company info"
           )
       } catch (e:HttpException){
           e.printStackTrace()
           Resource.Error(
               message = "Could not loud Company info"
           )



       }
    }
}