# Retrofit 2.X
## https://square.github.io/retrofit/ ##

#-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#-keepclasseswithmembers class * {
#    @retrofit2.http.* ;
#}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}