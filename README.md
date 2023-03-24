### **A library For implement multiple ad network in once**

**### How to**
Implement this in your project

**Step 1. Add the JitPack repository to your build file**

**Add it in your root build.gradle at the end of repositories:**

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2. Add the dependency**

	dependencies {
	        implementation 'com.github.DevMobarok:Mobarok_Ads_Sdk:1.0.0'
	}



### **Implement The Banner Ad**

**Step 1. Add the xml to your activity xml**

        <com.mobarok.ads.sdk.ui.BannerAdView
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"/>


**Step 2: Init the variable in your Activity.java code**



public class MainActivity extends AppCompatActivity {

    AdNetwork.Initialize adNetwork;
    BannerAd.Builder bannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         ///Initialize the ad network 
        adNetwork = new AdNetwork.Initialize(this)  //Init the adnetwork
                .setAdStatus(Constant.ON)   //Turn on and off the ad
                .setAdNetwork(Constant.ADMOB) //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)  //Select Backup ad network if you want
                .setAdMobAppId(null)  //set the admob app id
                .setStartappAppId(STARTAPP_APP_ID)  //set the start.io app id
                .setUnityGameId(UNITY_GAME_ID)  // set the unity game id
                .setAppLovinSdkKey(APPLOVIN_SDK_Key)  //set the applovin sdk key
                .setMopubBannerId(MOPUB_BANNER_ID)  //set mopub banner id
                .setDebug(BuildConfig.DEBUG)  
                .build();



       //Initialize the banner ad 
        bannerAd = new BannerAd.Builder(this)  //init the banner ad
                .setAdStatus(Constant.ON)  //select ad status
                .setAdNetwork(Constant.ADMOB)   //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)   //Select Backup ad network if you want
                .setAdMobBannerId(ADMOB_BANNER_ID)  //set admob banner id
                .setUnityBannerId(UNITY_BANNER_ID)  // set the unity banner ad id
                .setAppLovinBannerId(APPLOVIN_BANNER_ID)   //set the applovin banner ad id
                .setAppLovinBannerZoneId(APPLOVIN_BANNER_ZONE_ID)     //set applovin banner zone id
                .setMopubBannerId(MOPUB_BANNER_ID)   // set mopub banner id
                .setDarkTheme(false) //set dark mode  true or false
                .build();




    }

}








### **Implement the native Banner Ads**

**Step 1. Add the xml to your activity xml**

       <com.mobarok.ads.sdk.ui.MediumNativeAdView
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content" />



**Step 2: Init the variable in your Activity.java code**



public class MainActivity extends AppCompatActivity {

    AdNetwork.Initialize adNetwork;
    NativeAd.Builder nativeAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         ///Initialize the ad network 
        adNetwork = new AdNetwork.Initialize(this)  //Init the adnetwork
                .setAdStatus(Constant.ON)   //Turn on and off the ad
                .setAdNetwork(Constant.ADMOB) //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)  //Select Backup ad network if you want
                .setAdMobAppId(null)  //set the admob app id
                .setStartappAppId(STARTAPP_APP_ID)  //set the start.io app id
                .setUnityGameId(UNITY_GAME_ID)  // set the unity game id
                .setAppLovinSdkKey(APPLOVIN_SDK_Key)  //set the applovin sdk key
                .setMopubBannerId(MOPUB_BANNER_ID)  //set mopub banner id
                .setDebug(BuildConfig.DEBUG)  
                .build();





         //Initialize the native banner ad
         nativeAd = new NativeAd.Builder(this)   //init the native banner ad
                .setAdStatus(Constant.ON)  //select ad status
                .setAdNetwork(Constant.ADMOB)   //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)   //Select Backup ad network if you want
                .setAdMobNativeId(ADMOB_NATIVE_ID)  //set admob native banner id
                .setAppLovinNativeId(APPLOVIN_NATIVE_MANUAL_ID)   //set the applovin manual native banner ad id
                .setDarkTheme(false)   //set dark mode  true or false
                .build();




    }

}




**### Implement the interstitial Ad**


**Step 2: Init the variable in your Activity.java code**

public class MainActivity extends AppCompatActivity {

    AdNetwork.Initialize adNetwork;
    InterstitialAd.Builder interstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         ///Initialize the ad network 
        adNetwork = new AdNetwork.Initialize(this)  //Init the adnetwork
                .setAdStatus(Constant.ON)   //Turn on and off the ad
                .setAdNetwork(Constant.ADMOB) //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)  //Select Backup ad network if you want
                .setAdMobAppId(null)  //set the admob app id
                .setStartappAppId(STARTAPP_APP_ID)  //set the start.io app id
                .setUnityGameId(UNITY_GAME_ID)  // set the unity game id
                .setAppLovinSdkKey(APPLOVIN_SDK_Key)  //set the applovin sdk key
                .setMopubBannerId(MOPUB_BANNER_ID)  //set mopub banner id
                .setDebug(BuildConfig.DEBUG)  
                .build();


       //init the interstitialAd 
       interstitialAd = new InterstitialAd.Builder(this)
                .setAdStatus(Constant.ON)   //Turn on and off the ad
                .setAdNetwork(Constant.ADMOB)  //Choose the ad network type
                .setBackupAdNetwork(Constant.NONE)  //Select Backup ad network if you want
                .setAdMobInterstitialId(ADMOB_INTERSTITIAL_ID)  //set the interstitialAd  id
                .setUnityInterstitialId(UNITY_INTERSTITIAL_ID)   // set the unity interstitialAd  id
                .setAppLovinInterstitialId(APPLOVIN_INTERSTITIAL_ID)   //set the applovin interstitialAd  id
                .setAppLovinInterstitialZoneId(APPLOVIN_INTERSTITIAL_ZONE_ID) //set the applovin interstitialAd  zone id
                .setMopubInterstitialId(MOPUB_INTERSTITIAL_ID)   //set interstitialAd  banner id
                .setClick(1)  //set the adclick means show the ads after how many click
                .build();


        // Show the interstitial ad in a simple button on click
        findViewById(R.id.btn_interstitial).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            
            //Show
            interstitialAd.show();
        });



   

    }

}




**### Thanks For using our library take love❤️**
