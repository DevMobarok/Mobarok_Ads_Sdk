package com.mobarok.ads.sdk.format;

import static com.mobarok.ads.sdk.util.Constant.ADMOB;
import static com.mobarok.ads.sdk.util.Constant.FACEBOOK;
import static com.mobarok.ads.sdk.util.Constant.ON;
import static com.mobarok.ads.sdk.util.Constant.APPLOVIN;
import static com.mobarok.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.mobarok.ads.sdk.util.Constant.NONE;
import static com.mobarok.ads.sdk.util.Constant.STARTAPP;
import static com.mobarok.ads.sdk.util.Constant.UNITY;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.mobarok.ads.sdk.R;
import com.mobarok.ads.sdk.util.Constant;
import com.mobarok.ads.sdk.util.NativeTemplateStyle;
import com.mobarok.ads.sdk.util.TemplateView;
import com.mobarok.ads.sdk.util.Tools;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.ArrayList;
import java.util.List;

public class NativeAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;
        LinearLayout native_ad_view_container;

        MediaView mediaView;
        TemplateView admob_native_ad;
        LinearLayout admob_native_background;
        View startapp_native_ad;
        ImageView startapp_native_image;
        ImageView startapp_native_icon;
        TextView startapp_native_title;
        TextView startapp_native_description;
        Button startapp_native_button;
        LinearLayout startapp_native_background;

        FrameLayout applovin_native_ad;
        MaxNativeAdLoader nativeAdLoader;
        MaxAd nativeAd;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobNativeId = "";
        private String facebookNativeId = "";
        private String appLovinNativeId = "";
        private int placementStatus = 1;
        private boolean darkTheme = false;
        private boolean legacyGDPR = false;


        private NativeAdLayout nativeAdLayout;
        private LinearLayout adView;
        private com.facebook.ads.NativeAd fbNativeAd;


        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadNativeAd();
            return this;
        }

        public Builder setPadding(int left, int top, int right, int bottom) {
            setNativeAdPadding(left, top, right, bottom);
            return this;
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Builder setAdMobNativeId(String adMobNativeId) {
            this.adMobNativeId = adMobNativeId;
            return this;
        }

        public Builder setFacebookNativeId(String facebookNativeId) {
            this.facebookNativeId = facebookNativeId;
            return this;
        }

        public Builder setAppLovinNativeId(String appLovinNativeId) {
            this.appLovinNativeId = appLovinNativeId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadNativeAd() {

            if (adStatus.equals(ON) && placementStatus != 0) {



                native_ad_view_container = activity.findViewById(R.id.native_ad_view_container);
                admob_native_ad = activity.findViewById(R.id.admob_native_ad_container);
                mediaView = activity.findViewById(R.id.media_view);
                admob_native_background = activity.findViewById(R.id.background);
                startapp_native_ad = activity.findViewById(R.id.startapp_native_ad_container);
                startapp_native_image = activity.findViewById(R.id.startapp_native_image);
                startapp_native_icon = activity.findViewById(R.id.startapp_native_icon);
                startapp_native_title = activity.findViewById(R.id.startapp_native_title);
                startapp_native_description = activity.findViewById(R.id.startapp_native_description);
                startapp_native_button = activity.findViewById(R.id.startapp_native_button);
                startapp_native_button.setOnClickListener(v -> startapp_native_ad.performClick());
                startapp_native_background = activity.findViewById(R.id.startapp_native_background);
                applovin_native_ad = activity.findViewById(R.id.applovin_native_ad_container);

                switch (adNetwork) {
                    case ADMOB:
                        if (admob_native_ad.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(activity, adMobNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, R.color.colorBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admob_native_ad.setStyles(styles);
                                            admob_native_background.setBackgroundResource(R.color.colorBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, R.color.colorBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admob_native_ad.setStyles(styles);
                                            admob_native_background.setBackgroundResource(R.color.colorBackgroundLight);
                                        }
                                        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        admob_native_ad.setNativeAd(NativeAd);
                                        admob_native_ad.setVisibility(View.VISIBLE);
                                        native_ad_view_container.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            loadBackupNativeAd();
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                        } else {
                            Log.d(TAG, "AdMob Native Ad has been loaded");
                        }
                        break;

                    case STARTAPP:
                        if (startapp_native_ad.getVisibility() != View.VISIBLE) {
                            StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
                            NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                    .setAdsNumber(3)
                                    .setAutoBitmapDownload(true)
                                    .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                            AdEventListener adListener = new AdEventListener() {
                                @Override
                                public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad arg0) {
                                    Log.d(TAG, "StartApp Native Ad loaded");
                                    startapp_native_ad.setVisibility(View.VISIBLE);
                                    native_ad_view_container.setVisibility(View.VISIBLE);
                                    //noinspection rawtypes
                                    ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                    // Print all ads details to log
                                    for (Object ad : ads) {
                                        Log.d(TAG, "StartApp Native Ad " + ad.toString());
                                    }

                                    NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                    if (ad != null) {
                                        startapp_native_image.setImageBitmap(ad.getImageBitmap());
                                        startapp_native_icon.setImageBitmap(ad.getSecondaryImageBitmap());
                                        startapp_native_title.setText(ad.getTitle());
                                        startapp_native_description.setText(ad.getDescription());
                                        startapp_native_button.setText(ad.isApp() ? "Install" : "Open");
                                        ad.registerViewForInteraction(startapp_native_ad);
                                    }

                                    if (darkTheme) {
                                        startapp_native_background.setBackgroundResource(R.color.colorBackgroundDark);
                                    } else {
                                        startapp_native_background.setBackgroundResource(R.color.colorBackgroundLight);
                                    }

                                }

                                @Override
                                public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad arg0) {
                                    loadBackupNativeAd();
                                    Log.d(TAG, "StartApp Native Ad failed loaded");
                                }
                            };
                            startAppNativeAd.loadAd(nativePrefs, adListener);
                        } else {
                            Log.d(TAG, "StartApp Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN_MAX:
                    case APPLOVIN:
                        if (applovin_native_ad.getVisibility() != View.VISIBLE) {
                            nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, activity);
                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                @Override
                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                                    // Clean up any pre-existing native ad to prevent memory leaks.
                                    if (nativeAd != null) {
                                        nativeAdLoader.destroy(nativeAd);
                                    }

                                    // Save ad for cleanup.
                                    nativeAd = ad;

                                    // Add ad view to view.
                                    applovin_native_ad.removeAllViews();
                                    applovin_native_ad.addView(nativeAdView);
                                    applovin_native_ad.setVisibility(View.VISIBLE);
                                    native_ad_view_container.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                                    // We recommend retrying with exponentially higher delays up to a maximum delay
                                    loadBackupNativeAd();
                                }

                                @Override
                                public void onNativeAdClicked(final MaxAd ad) {
                                    // Optional click callback
                                }
                            });
                            nativeAdLoader.loadAd(createNativeAdView());
                        } else {
                            Log.d(TAG, "AppLovin Native Ad has been loaded");
                        }
                        break;


                    case FACEBOOK:
                        loadFacebookNativeAd();
                        break;

                    case UNITY:
                        //do nothing
                        break;
                }

            }

        }

        public void loadBackupNativeAd() {

            if (adStatus.equals(ON) && placementStatus != 0) {

                native_ad_view_container = activity.findViewById(R.id.native_ad_view_container);
                admob_native_ad = activity.findViewById(R.id.admob_native_ad_container);
                mediaView = activity.findViewById(R.id.media_view);
                admob_native_background = activity.findViewById(R.id.background);
                startapp_native_ad = activity.findViewById(R.id.startapp_native_ad_container);
                startapp_native_image = activity.findViewById(R.id.startapp_native_image);
                startapp_native_icon = activity.findViewById(R.id.startapp_native_icon);
                startapp_native_title = activity.findViewById(R.id.startapp_native_title);
                startapp_native_description = activity.findViewById(R.id.startapp_native_description);
                startapp_native_button = activity.findViewById(R.id.startapp_native_button);
                startapp_native_button.setOnClickListener(v -> startapp_native_ad.performClick());
                startapp_native_background = activity.findViewById(R.id.startapp_native_background);
                applovin_native_ad = activity.findViewById(R.id.applovin_native_ad_container);

                switch (backupAdNetwork) {
                    case ADMOB:
                        if (admob_native_ad.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(activity, adMobNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, R.color.colorBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admob_native_ad.setStyles(styles);
                                            admob_native_background.setBackgroundResource(R.color.colorBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, R.color.colorBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admob_native_ad.setStyles(styles);
                                            admob_native_background.setBackgroundResource(R.color.colorBackgroundLight);
                                        }
                                        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        admob_native_ad.setNativeAd(NativeAd);
                                        admob_native_ad.setVisibility(View.VISIBLE);
                                        native_ad_view_container.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            admob_native_ad.setVisibility(View.GONE);
                                            native_ad_view_container.setVisibility(View.GONE);
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                        } else {
                            Log.d(TAG, "AdMob Native Ad has been loaded");
                        }
                        break;

                    case STARTAPP:
                        if (startapp_native_ad.getVisibility() != View.VISIBLE) {
                            StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
                            NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                    .setAdsNumber(3)
                                    .setAutoBitmapDownload(true)
                                    .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                            AdEventListener adListener = new AdEventListener() {
                                @Override
                                public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad arg0) {
                                    Log.d(TAG, "StartApp Native Ad loaded");
                                    startapp_native_ad.setVisibility(View.VISIBLE);
                                    native_ad_view_container.setVisibility(View.VISIBLE);
                                    //noinspection rawtypes
                                    ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                    // Print all ads details to log
                                    for (Object ad : ads) {
                                        Log.d(TAG, "StartApp Native Ad " + ad.toString());
                                    }

                                    NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                    if (ad != null) {
                                        startapp_native_image.setImageBitmap(ad.getImageBitmap());
                                        startapp_native_icon.setImageBitmap(ad.getSecondaryImageBitmap());
                                        startapp_native_title.setText(ad.getTitle());
                                        startapp_native_description.setText(ad.getDescription());
                                        startapp_native_button.setText(ad.isApp() ? "Install" : "Open");
                                        ad.registerViewForInteraction(startapp_native_ad);
                                    }

                                    if (darkTheme) {
                                        startapp_native_background.setBackgroundResource(R.color.colorBackgroundDark);
                                    } else {
                                        startapp_native_background.setBackgroundResource(R.color.colorBackgroundLight);
                                    }

                                }

                                @Override
                                public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad arg0) {
                                    startapp_native_ad.setVisibility(View.GONE);
                                    native_ad_view_container.setVisibility(View.GONE);
                                    Log.d(TAG, "StartApp Native Ad failed loaded");
                                }
                            };
                            startAppNativeAd.loadAd(nativePrefs, adListener);
                        } else {
                            Log.d(TAG, "StartApp Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN_MAX:
                    case APPLOVIN:
                        if (applovin_native_ad.getVisibility() != View.VISIBLE) {
                            nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, activity);
                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                @Override
                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                                    // Clean up any pre-existing native ad to prevent memory leaks.
                                    if (nativeAd != null) {
                                        nativeAdLoader.destroy(nativeAd);
                                    }

                                    // Save ad for cleanup.
                                    nativeAd = ad;

                                    // Add ad view to view.
                                    applovin_native_ad.removeAllViews();
                                    applovin_native_ad.addView(nativeAdView);
                                    applovin_native_ad.setVisibility(View.VISIBLE);
                                    native_ad_view_container.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                                    // We recommend retrying with exponentially higher delays up to a maximum delay
                                }

                                @Override
                                public void onNativeAdClicked(final MaxAd ad) {
                                    // Optional click callback
                                }
                            });
                            nativeAdLoader.loadAd(createNativeAdView());
                        } else {
                            Log.d(TAG, "AppLovin Native Ad has been loaded");
                        }
                        break;

                    case FACEBOOK:
                        loadFacebookNativeAd();
                        break;

                    case UNITY:

                    case NONE:
                        native_ad_view_container.setVisibility(View.GONE);
                        break;
                }

            }

        }

        public void setNativeAdPadding(int left, int top, int right, int bottom) {
            native_ad_view_container = activity.findViewById(R.id.native_ad_view_container);
            native_ad_view_container.setPadding(left, top, right, bottom);
        }

        public MaxNativeAdView createNativeAdView() {
            MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_medium_template_view)
                    .setTitleTextViewId(R.id.title_text_view)
                    .setBodyTextViewId(R.id.body_text_view)
                    .setAdvertiserTextViewId(R.id.advertiser_textView)
                    .setIconImageViewId(R.id.icon_image_view)
                    .setMediaContentViewGroupId(R.id.media_view_container)
                    .setOptionsContentViewGroupId(R.id.ad_options_view)
                    .setCallToActionButtonId(R.id.cta_button)
                    .build();
            return new MaxNativeAdView(binder, activity);
        }


        private void loadFacebookNativeAd() {

            fbNativeAd = new com.facebook.ads.NativeAd(activity, facebookNativeId);

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                    Log.e(TAG, "Native ad finished downloading all assets.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Native ad failed to load
                    loadBackupNativeAd();
                    Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Race condition, load() called again before last ad was displayed
                    if (nativeAd == null || nativeAd != ad) {
                        Toast.makeText(activity, "return", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    // Inflate Native Ad into Container
                    inflateAd(fbNativeAd);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                    Log.d(TAG, "Native ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                    Log.d(TAG, "Native ad impression logged!");
                }
            };

            // Request an ad
            fbNativeAd.loadAd(
                    fbNativeAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        }

        private void inflateAd(com.facebook.ads.NativeAd nativeAd) {
            nativeAd.unregisterView();

            // Add the Ad view into the ad container.
            nativeAdLayout = activity.findViewById(R.id.native_ad_container);
            LayoutInflater inflater = LayoutInflater.from(activity);
            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
            nativeAdLayout.addView(adView);

            // Add the AdOptionsView
            LinearLayout adChoicesContainer = activity.findViewById(R.id.ad_choices_container);
            AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
            adChoicesContainer.removeAllViews();
            adChoicesContainer.addView(adOptionsView, 0);

            // Create native UI using the ad metadata.
            com.facebook.ads.MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
            TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
            com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
            TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
            TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
            TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
            Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

            // Set the Text.
            nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeAdBody.setText(nativeAd.getAdBodyText());
            nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            // Create a list of clickable views
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);

            // Register the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(
                    adView, nativeAdMedia, nativeAdIcon, clickableViews);
        }


    }

}
