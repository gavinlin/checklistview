[![Build Status](https://travis-ci.org/gavinlin/checklistview.svg?branch=master)](https://travis-ci.org/gavinlin/checklistview)
[ ![Download](https://api.bintray.com/packages/gavinlinau/android/check-list-view/images/download.svg) ](https://bintray.com/gavinlinau/android/check-list-view/_latestVersion)

# Preview

![Preview Image](preview/preview.gif)

# Install

Add below snippet into app's build.gradle

```
repositories {
    maven {
        url "https://dl.bintray.com/gavinlinau/android"
    }
}
```

and

```
implementation "com.gavincode:checklistview:${checklistview_version}"
```

# How to use

```
<com.gavincode.checklistview.ChecklistView
    android:id="@+id/check_list_view"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    />
```

Get all data from checklistview

```
contentView.getChecklistData()
```

set init checklist data

```
contentView.setChecklistData(list)
```
