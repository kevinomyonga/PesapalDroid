# PesapalDroid
A simple library to grant access to the Pesapal payment portal on the android platform.


##Preview
### Demo
Try out the app here: 

### Screenshots
![ScreenShot](https://raw.github.com/ImperiusRex/PesapalDroid/master/screenshots/sample1.png)

![ScreenShot](https://raw.github.com/ImperiusRex/PesapalDroid/master/screenshots/sample2.png)


## Setup

### 1. Get the Keys
Head over to the Pesapal website to register and get the Consumer Key and Consumer Secret.

### 2. Provide the gradle dependency
Project build.gradle
```Java
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

app build.gradle
```Java
dependencies {
    compile 'com.kevinomyonga.pesapaldroid:library:1.0.0'
}
```

### 3. Initialize the fragment

#### Xml
```xml
<fragment xmlns:pesapal="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.kevinomyonga.pesapaldroid.PesapalDroidFragment"
        android:id="@+id/payment_fragment"
        pesapal:consumer_key="@string/consumer_key"
        pesapal:consumer_secret="@string/consumer_secret"
        pesapal:is_demo="false"
        pesapal:is_mobile="true"
        tools:layout="@layout/fragment_main" />
```

#### Java
```Java
        PesapalDroidFragment pesapalDroidFragment = new PesapalDroidFragment();
        pesapalDroidFragment.setConsumerKey(getString(R.string.consumer_key));
        pesapalDroidFragment.setConsumerSecret(getString(R.string.consumer_secret));
        pesapalDroidFragment.setDemoEnabled(false);
        pesapalDroidFragment.setMobileEnabled(true);

        // Insert the fragment by replacing any existing fragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content_frame, pesapalDroidFragment)
                .commit();
```

## Additional Setup
### Passing values to the portal
```Java
        //Pass the buyer details
        pesapalDroidFragment.setfName(args.getString("fname"));
        pesapalDroidFragment.setlName(args.getString("lname"));
        pesapalDroidFragment.setEmail(args.getString("email"));
        pesapalDroidFragment.setPhone(args.getString("phone"));
        pesapalDroidFragment.setDesc(args.getString("desc"));
        pesapalDroidFragment.setAmount(args.getString("amount"));
```


### Attributions
