plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
//    kotlin("plugin.serialization") version "1.8.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0-Beta1"

//    id("dev.icerock.mobile.multiplatform-resources") version "0.23.0"

}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = true
            baseName = "shared"
//            export("dev.icerock.moko:resources:0.23.0")
//            export("dev.icerock.moko:resources-compose:0.23.0")

        }
    }

    //':shared:podDebugFrameworkIosFat' and [configuration ':shared:debugFrameworkIosFat']
   val myAttribute = Attribute.of("myOwnAttribute", String::class.java)
/*
// replace releaseFrameworkIosFat by the name of the first configuration that conflicts
    configurations.named(":podDebugFrameworkIosFat").configure {
        attributes {
            // put a unique attribute
            attribute(myAttribute, "release-all")
        }
    }

// replace debugFrameworkIosFat by the name of the second configuration that conflicts
    configurations.named("podDebugFrameworkIosFat").configure {
        attributes {
            attribute(myAttribute, "debug-all")
        }
    }*/

    configurations.all {
        if (name == "podDebugFrameworkIosFat") {
            attributes {
                // put a unique attribute
                attribute(myAttribute, "pod-debug")
            }
        }
        if (name == "podReleaseFrameworkIosFat") {
            attributes {
                // put a unique attribute
                attribute(myAttribute, "pod-release")
            }
        }
    }


    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
//        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {

        val voyagerVersion = "1.0.0-rc07"

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("media.kamel:kamel-image:0.6.0")
                implementation("io.ktor:ktor-client-core:2.3.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("io.ktor:ktor-client-json:2.1.3")
                implementation("io.ktor:ktor-client-logging:2.3.1")
                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatfrom
                api("io.insert-koin:koin-core:3.4.0")
                api("io.insert-koin:koin-test:3.4.0")
                implementation("io.insert-koin:koin-compose:1.0.4")

                // Voyager
//                implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

//                implementation("dev.icerock.moko:resources:0.23.0")
//                implementation("dev.icerock.moko:resources-compose:0.23.0") // for compose multiplatform
//                implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")

                implementation("androidx.datastore:datastore-preferences-core:1.1.0-alpha03")
                implementation("co.touchlab:kermit:2.0.0-RC4")


            }

        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.6.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")
                implementation("io.ktor:ktor-client-android:2.3.1")
                implementation("io.insert-koin:koin-core:3.4.0")
                implementation("io.insert-koin:koin-android:3.4.0")
                implementation("androidx.compose.ui:ui-tooling-preview-android:1.5.4")
                implementation("androidx.datastore:datastore-preferences:1.0.0")

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.1")
            }
        }
    }
0


}
//multiplatformResources {
//    multiplatformResourcesPackage = "com.bn.store.kmp"
//    disableStaticFrameworkWarning = true
//}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.bn.store.kmp"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

