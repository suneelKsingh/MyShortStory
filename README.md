# MyShortStory
this is simple and secure Note app To Keep your words here to not forget the things


def lifecycle_version = "2.1.0"
    def room_version = "2.2.3"
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.3.2'
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'

    //    RoomDb Dep...
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    kapt "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation 'io.reactivex.rxjava2:rxjava:2.2.16'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

// new
    implementation  'com.jakewharton.rxbinding2:rxbinding:2.0.0'
    implementation  'io.reactivex.rxjava2:rxjava:2.1.9'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
