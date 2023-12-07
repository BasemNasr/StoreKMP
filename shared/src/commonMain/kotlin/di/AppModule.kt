package di

import core.Context
import core.platformModule
import data.core.AppDataStoreManager
import data.repository.AppPreferencesRepository
import data.repository.AuthRepositoryImp
import data.repository.HomeRepositoryImp
import domain.core.AppDataStore
import domain.repository.AuthRepository
import domain.repository.HomeRepository
import domain.usecase.CategoryUseCase
import domain.usecase.LoginUseCase
import domain.usecase.ProductUseCase
import domain.usecase.RegisterUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import presentation.screens.auth.login.LoginViewModel
import presentation.screens.auth.register.RegisterViewModel
import presentation.screens.main.MainViewModel
import presentation.screens.main.taps.home.HomeViewModel
import presentation.screens.main.taps.profile.ProfileViewModel
import presentation.screens.splash.SplashViewModel


fun initKoin(context: Context, appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(platformModule(), appModule(context))
}

fun initKoin(context: Context) = initKoin(context) {}


fun appModule(context: Context) = module {
    single { createKtorClient() }
    single<AuthRepository> { AuthRepositoryImp(get()) }
    single<HomeRepository> { HomeRepositoryImp(get()) }

    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { CategoryUseCase(get()) }
    single { ProductUseCase(get()) }

    viewModelDefinition { LoginViewModel(get(), get()) }
    viewModelDefinition { RegisterViewModel(get()) }
    viewModelDefinition { MainViewModel(get()) }
    viewModelDefinition { SplashViewModel(get()) }
    viewModelDefinition { ProfileViewModel(get()) }
    viewModelDefinition { HomeViewModel(get(),get(),get()) }


    single<AppDataStore> { AppDataStoreManager(context) }
    single { AppPreferencesRepository(get()) }

}


private const val TIME_OUT = 60_000


fun createKtorClient(): HttpClient {
    return HttpClient() {
        // Configure your Ktor client here
        install(ContentNegotiation) {
            json(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTP:Logger=>$message")
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                println("HTTP:status:=>${response.status.value}")
            }
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}


