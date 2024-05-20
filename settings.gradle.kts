pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { url=uri("https://jitpack.io") }
        maven { url=uri("https://devrepo.kakao.com/nexus/content/groups/public/")} // 카카오 로그인
    }
}


rootProject.name = "Tape_AOS"
include(":app")
 