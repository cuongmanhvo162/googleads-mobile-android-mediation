/*
 * Copyright (C) 2014 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.ads.mediation.sample.customevent;

import com.google.ads.mediation.sample.sdk.SampleAdListener;
import com.google.ads.mediation.sample.sdk.SampleAdView;
import com.google.ads.mediation.sample.sdk.SampleErrorCode;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;

/**
 * A {@link SampleAdListener} that forwards events to AdMob's
 * {@link CustomEventBannerListener}.
 */
public class SampleCustomBannerEventForwarder extends SampleAdListener {
    private CustomEventBannerListener mBannerListener;
    private SampleAdView mAdView;

    /**
     * Creates a new {@code SampleBannerEventForwarder}.
     * @param listener An AdMob Mediation {@link CustomEventBannerListener} that should receive
     *                 forwarded events.
     * @param adView   A {@link SampleAdView}.
     */
    public SampleCustomBannerEventForwarder(
            CustomEventBannerListener listener, SampleAdView adView) {
        this.mBannerListener = listener;
        this.mAdView = adView;
    }

    @Override
    public void onAdFetchSucceeded() {
        mBannerListener.onAdLoaded(mAdView);
    }

    @Override
    public void onAdFetchFailed(SampleErrorCode errorCode) {
        switch (errorCode) {
            case UNKNOWN:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                break;
            case BAD_REQUEST:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            case NETWORK_ERROR:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            case NO_INVENTORY:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                break;
        }
    }

    @Override
    public void onAdFullScreen() {
        mBannerListener.onAdClicked();
        mBannerListener.onAdOpened();
        // Only call onAdLeftApplication if your ad network actually exits the developer's app.
        mBannerListener.onAdLeftApplication();
    }

    @Override
    public void onAdClosed() {
        mBannerListener.onAdClosed();
    }
}
