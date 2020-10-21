# Ingresse Android SDK ![JitPack](https://img.shields.io/jitpack/v/github/ingresse/android-sdk.svg?style=flat-square)


## [ :gear: ] Installation guide
### For production use:

- **Root _build.gradle_ at the end of repositories:**
   ```gradle
   allprojects {
       repositories {
           ...
           maven { url 'https://jitpack.io' }
       }
   }
   ```
- **Add the dependency**
   ```gradle
   dependencies {
       implementation 'com.github.ingresse:android-sdk:<jitpack-version>'
   }
   ```

## [ :electric_plug: ] Usage guide (V2)
#### With plain functions inside repository
```kotlin
val service = ServiceManager.service.V2().search
    
fun getEvents() {
    val request = SearchEvents(state = "sp")
    
    viewModelScope.launch {
        kotlin.runCatching {
            service.getSearchedEventsPlain(request)
        }.onSuccess { data -> 
            // TODO
        }.onFailure { exception ->  
            // TODO
        }
    }
}
```

#### With `Result<T>` functions inside repository
```kotlin
val service = ServiceManager.service.V2().search
    
fun getEvents() {
    val request = SearchEvents(state = "sp")

    viewModelScope.launch {
        val result = service.getSearchedEvents(request = request)
        result.onSuccess { data ->
            // TODO
        }.onError { code, throwable ->
            // TODO
        }.connectionError {
            // TODO
        }.onTokenExpired {
            // TODO
        }
    }
}
```
___

###  :hammer_and_wrench: For developing use:

#### Using a jitpack snapshot:
```gradle
ext {
    ingresse_sdk_version = "feature~my-new-feature-SNAPSHOT"
}

dependencies {
    implementation "com.github.ingresse:android-sdk:$ingresse_sdk_version"
}
```

#### Using local ingresse-sdk repository:

- **Set local path in _settings.gradle_**
   ```gradle
   include ':sdk'
   project(':sdk').projectDir = new File(settingsDir, '../android-sdk/sdk')
   ``` 

- **Add the dependency**
   ```gradle
   dependencies {
       implementation project(':sdk')
   }
   ```