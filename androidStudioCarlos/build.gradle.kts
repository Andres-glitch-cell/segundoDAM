plugins {
    id("com.android.application") version "8.8.0" apply false
}
subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib-jdk")) {
                    useTarget("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
                }
            }
        }
    }
}
