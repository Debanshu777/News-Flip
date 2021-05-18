<p align="center">
  <img height=100 width=100 src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/app/src/main/res/drawable/logo.png"/>
</p>
<h1 align="center">
News/Flip
</h1>
<p align="center">
A simple news app getting data from the news api and showcasing using the MVVM architechture pattern. Following a single actvity design, <b><i>Coroutines, NDK to store API Key, Retrofit, Navigation Components, Webkit, Preferences DataStore, Lottie animations, Linting, Firebase Crashlytics & Performance Monitoring </i></b> 
</p>
<table align="center">
  <tr>
    <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(106).png" height=400 width=200/></td>
    <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(107).png" height=400 width=200/></td>
    <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(110).png" height=400 width=200/></td>
    <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(111).png" height=400 width=200/></td>
  </tr>
 </table>
 
 ## Setting Up Your Own
 
 - Add your API KEY from <a href="https://newsapi.org/">newsapi.org</a> it is free and easy can be obtained
    ```
    ../src/main/jni/api-keys.c
    
    #include <jni.h>

    JNIEXPORT jstring JNICALL
    Java_com_debanshu777_newsapp_util_Constant_00024Companion_getKeys(JNIEnv *env, jobject thiz) {
      return (*env)->NewStringUTF(env, "<YOUR-API-KEY>");
    }
    ```
 - Add file "github.properties" add two variable <br>
 ```
    gpr.usr = ADD-GITHUB-USER-ID     //get id from the link https://api.github.com/users/GITHUB-USER-NAME <br>
    gpr.key = GITHUB-PERSONAL-TOKEN  //Make a github personal access token Settings/ Developer settings/Personal access tokens
 ```
 
- For Firebase Integration build a new project If you havn't in firebase-console, add your add get the "google-services.json" replace the already present.
 <a href="https://www.youtube.com/watch?v=LCYndFH-cvc&t=367s">Refer to this video for more info</a>
 <table align="center">
    <tr>
      <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(108).png" height=500 width=250/></td>
      <td><img src="https://github.com/Debanshu777/MVVM_NewsApp/blob/master/Images/Screenshot%20(109).png" height=500 width=250/></td>
    </tr>
</table>

## Want to Contribute ?

Awesome! If you want to contribute to this project, you're always welcome!
Have any questions, doubts or want to present your opinions, views? You're always welcome. You can mail me at <b>debanshudatta123@gmail.com</b> or make an issue.

## Contributors

- [Debanshu Datta](https://github.com/Debanshu777)

## License

```
Copyright 2021 Debanshu Datta

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

