# **WebView Challenge**

***Challenge:*** The WebView injection with multiple loadUrl() calls, first one to inject some js code and succeeding ones to load few webpages work fine... until Android Nougat came into picture.

**Killer Compatibility Note:** The compatibility note on new [Android N documentation here](https://developer.android.com/reference/android/webkit/WebView.html#evaluateJavascript\(java.lang.String,%20android.webkit.ValueCallback<java.lang.String>\)) explains what changed.

The challenge here is to get the code working as before with proposed changes on the branch **android-n**

## **Set up:**
* Get an Nexus 5X/6P and upgrade it to Android Nougat (API-24 aka. 7.0). 
  * Register the device on beta channel to get Android N, if needed.
* Create a local file named dummy_file.html on the device with below content (no changes to html allowed).
```html
<html><body>
<p id="demo">Click the button to change the text in this paragraph.</p>
<button onclick="window.vungle.myFunction()">Try it</button>
</body></html>
```
* On branch *master*, when the button is pushed, the text changes.
* On the other hand, with proposed changes to upgrade the app to Android N on branch *android-n* the text doesn't change

## **Constraints**:
* The dummy_file.html file contents shouldn't be changed.
* Solution should be free of any race condition and should always work irrespective of the amount of javascript code being injected before loading dummy_file.html.

## **Bonus challenge:**
* [ ] Hints can be provided upon request.
