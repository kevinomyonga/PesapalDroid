# PesapalDroid
A simple library to grant easy access to the Pesapal portal on the android platform.


##Preview
### Demo
Click below to watch a quick demo.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=C7UQXoZi9j8
" target="_blank"><img src="http://img.youtube.com/vi/C7UQXoZi9j8/0.jpg" 
alt="IMAGE ALT TEXT HERE" width="512" height="360" border="10" /></a>

Try out the app here: 

 *[![Play Store Badge](https://developer.android.com/images/brand/en_app_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=com.kevinomyonga.pesapaldroid.samples)*

### Screenshots
![ScreenShot](https://raw.github.com/ImperiusRex/PesapalDroid/master/screenshots/sample1.png)
![ScreenShot](https://raw.github.com/ImperiusRex/PesapalDroid/master/screenshots/sample2.png)


##Target Platforms
Android API level 9 or later.


## Setup
### 1. Get the Keys
Head over to the Pesapal website (https://www.pesapal.com) to register as a merchant and get the Consumer Key and Consumer Secret.

### 2. Provide the gradle dependency
```Java
dependencies {
    compile 'com.kevinomyonga.pesapaldroid:library:1.0.0'
}
```

### 3. Declare the fragment
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

or

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

## Acknowledgments
Davide Parise bubini.mara5@gmail.com


## License
This project is licensed under the Apache License - see the [LICENSE](https://raw.github.com/ImperiusRex/PesapalDroid/master/LICENSE) file for details.
