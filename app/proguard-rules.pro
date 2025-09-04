#-repackageclasses
#-ignorewarnings
#-dontwarn
#-dontnote

# সব public/protected API বাদে বাকি rename
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# App class বাদে সব obfuscate
-keep class com.my.newprojectsas.MainActivity {
    <init>();
    *;
}

# Preserve ViewBinding classes
-keep class com.FGeminiPro.databinding.** { *; }

# Annotation/Reflection compatible রাখুন
-keepattributes Signature,InnerClasses,EnclosingMethod,Annotation