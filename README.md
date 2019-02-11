# Ingresse Android SDK

## Installation
### Root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
### Dependency
```gradle
dependencies {
    implementation 'com.github.ingresse:android-sdk:0.1.0'
}
```