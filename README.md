# WeatherApp


## Design:

The Weather App project is a simple app that shows the location of a single city (Toronto) using data for [meta weather API](https://www.metaweather.com/api/location/location_id/). 

The App is split into several the following packages 
#### Model: 
This is model of the App containing the objects and classes necessary to store weather information
#### Util: 
A utility class to do general processing
#### Network: 
Classes responsible for handling network calls.



The view for this project is the activilty_main.xml file, which displays the mainActivity. 

The work flow for the app is as follow:
-	Use the [cronet package](https://developer.android.com/guide/topics/connectivity/cronet/reference/org/chromium/net/package-summary) to make an api call to (https://www.metaweather.com/api/location/location_id/).
-	Validate the api response and use the [GSON package](https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.0/com/google/gson/Gson.html) to parse the response into a CombinedWeather class. 
-	Average the values in the weather list
-	Invoke a callback to show the weather on the UI


## Trade-offs:

One drawback to the current design of the app is that when the app loads, the screen is initially blank. This is because we need to wait for the api call before we have anything to show on the screen. An approach I considered was to make the API request in the background, and to have a button which only when clicked would update the weather information on the UI. This approach would require me to handle multiple clicks of the button to prevent multiple UI updates and given the simple scope of this I didn’t want to include a button on the UI, hence I rejected this approach
	I also considered using [androids httpUrl Connection](https://developer.android.com/reference/java/net/HttpURLConnection.html) directly, but I instead chose Cronet mainly because of its simplicity and how it seamlessly handles network exceptions.

