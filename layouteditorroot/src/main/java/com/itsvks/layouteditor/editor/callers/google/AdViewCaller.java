package com.itsvks.layouteditor.editor.callers.google;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.BaseAdView;

public class AdViewCaller {
  public static void setAdUnitId(View target, String value, Context context) {
    if (target instanceof BaseAdView) {
      ((BaseAdView) target).setAdUnitId(value);
    }
  }

  public static void setAdSize(View target, String value, Context context) {
    if (target instanceof BaseAdView) {
      if (value.equalsIgnoreCase("BANNER")) {
        ((BaseAdView) target).setAdSize(AdSize.BANNER);
      } else if (value.equalsIgnoreCase("FULL_BANNER")) {
        ((BaseAdView) target).setAdSize(AdSize.FULL_BANNER);
      } else if (value.equalsIgnoreCase("SMART_BANNER")) {
        ((BaseAdView) target).setAdSize(AdSize.SMART_BANNER);
      } else if (value.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
        ((BaseAdView) target).setAdSize(AdSize.MEDIUM_RECTANGLE);
      } else if (value.equalsIgnoreCase("WIDE_SKYSCRAPER")) {
        ((BaseAdView) target).setAdSize(AdSize.WIDE_SKYSCRAPER);
      } else if (value.equalsIgnoreCase("LEADERBOARD")) {
        ((BaseAdView) target).setAdSize(AdSize.LEADERBOARD);
      } else if (value.equalsIgnoreCase("FLUID")) {
        ((BaseAdView) target).setAdSize(AdSize.FLUID);
      } else if (value.equalsIgnoreCase("LARGE_BANNER")) {
        ((BaseAdView) target).setAdSize(AdSize.LARGE_BANNER);
      } else if (value.equalsIgnoreCase("SEARCH")) {
        ((BaseAdView) target).setAdSize(AdSize.SEARCH);
      } else if (value.equalsIgnoreCase("INVALID")) {
        ((BaseAdView) target).setAdSize(AdSize.INVALID);
      }
    }
  }
}
