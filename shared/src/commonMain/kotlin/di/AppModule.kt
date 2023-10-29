package di

import data.repository.AuthRepositoryImp
import domain.repository.AuthRepository
import domain.usecase.LoginUseCase
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
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.screens.auth.AuthViewModel


val appModule = module {
    single { createKtorClient() }
    single<AuthRepository> { AuthRepositoryImp(get()) }
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    viewModelDefinition { AuthViewModel(get(), get()) }
}
//val activityModule = module {
//    scope<MainActivity> {
//        scoped(qualifier = named("hello")) { "Heloo" }
//        scoped(qualifier = named("bye")) { "Byeeeeee" }
//    }
//}


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


