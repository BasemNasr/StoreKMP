package data.core


import core.Context
import core.getData
import core.putData
import domain.core.AppDataStore

const val APP_DATASTORE = "com.bn.store.kmp"

class AppDataStoreManager(val context: Context) : AppDataStore {

    override suspend fun setValue(
        key: String,
        value: String
    ) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }
}