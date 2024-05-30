# weather



## Setup

### 1. Register an account
Go to [CWA open data](https://opendata.cwa.gov.tw/index) to register an account for API keys.

### 2. Get the authorization
Visit [authorization page](https://opendata.cwa.gov.tw/user/authkey) to get the authorization key.

### 3. Clone project
```git
$ git clone https://gitlab.kdanmobile.com/android/practice/weather.git
```

### 4. New a variable
Create a variable named `WEATHER_API_KEY` in `local.properties`.
```properties
WEATHER_API_KEY = {YOUR API KEY}
```

### 5. Check Mainfest
Check `Mainfest.xml` file including specific meta-data (map into `local.properties` field).
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />

    ...
    
    <meta-data
            android:name="WEATHER_API_KEY"
            android:value="${WEATHER_API_KEY}" />
    </application>
</manifest>
```