package com.example.currencyconvertor.data.repo

import android.util.Log
import com.example.currencyconvertor.data.model.response.ConversionResponseModel
import com.example.currencyconvertor.data.network.Network
import com.example.currencyconvertor.domain.repo.RemoteRepo
import com.example.currencyconvertor.domain.statesandevents.States
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoImpl(private val network : Network): RemoteRepo {
    override suspend fun listCurrencies(): Flow<States<Map<String,String>>> {
        return flow {
            try {
                val resp = network.list().execute()
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.success == true) {
                        emit(States.SUCCESS(payload = body.currencies))
                    } else {
                        Log.e("Network Error",resp.body().toString())
                        emit(States.FAILURE(message = resp.body().toString()))
                    }
                }
                else{
                    Log.e("Network Error",resp.errorBody()?.string().toString())
                    emit(States.FAILURE(message = resp.errorBody()?.string().toString()))
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
                emit(States.FAILURE(message = e.message))
            }
        }
    }

    override suspend fun listRates(): Flow<States<Map<String,Double>>> {
       return flow {
           try {
               val resp = network.live().execute()
               if (resp.isSuccessful) {
                   val body = resp.body()
                   if (body?.success == true) {
                       emit(States.SUCCESS(body.quotes))
                   } else {
                       Log.e("Network Error",resp.body().toString())
                       emit(States.FAILURE(message = resp.body().toString()))
                   }
               }
               else{
                   Log.e("Network Error",resp.errorBody()?.string().toString())
                   emit(States.FAILURE(message = resp.errorBody()?.string().toString()))
               }
           }
           catch (e : Exception)
           {
               e.printStackTrace()
               emit(States.FAILURE(message = e.message))
           }
       }
    }

    override suspend fun convert(to: String, from: String, amount: Float): Flow<States<Float>> {
        return flow {
            try {
                val resp = network.convert(to,from,amount).execute()
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.success == true) {
                        emit(States.SUCCESS(payload = body.result.toFloat()))
                    } else {
                        Log.e("Network Error",resp.body().toString())
                        emit(States.FAILURE(message = resp.body().toString()))
                    }
                }
                else{
                    Log.e("Network Error",resp.errorBody().toString())
                    emit(States.FAILURE(message = resp.errorBody().toString()))
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
                emit(States.FAILURE(message = e.message))
            }
        }
    }
}