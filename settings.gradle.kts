pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // JitPack's build of cert4android@b6b7ef3f10 is broken upstream; it's built from source
        // into the local Maven repo instead (scripts/build-cert4android.sh).
        mavenLocal {
            content { includeModule("com.github.bitfireAT", "cert4android") }
        }
        maven("https://jitpack.io")
    }
}

include(":app")
