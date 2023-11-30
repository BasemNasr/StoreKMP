package di



import data.repository.AppPreferencesRepository
import data.repository.dataStoreFileName
import data.repository.getDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun getDatastoreModuleByPlatform() = module {

    single {
        getDataStore {
            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
                ?: throw Exception("Couldn't get Android Datastore context.")
        }
    }

    single { AppPreferencesRepository(get()) }

}