# MonthYearPicker
Fancy year and month picker library for your android app

## How to use?
### Maven

```Maven
<dependency>
  <groupId>com.github.twinkle942910</groupId>
  <artifactId>monthyearpicker</artifactId>
  <version>0.0.1</version>
  <type>aar</type>
</dependency>
```

### Gradle

```Gradle
dependencies {
    compile 'com.github.twinkle942910:monthyearpicker:0.0.1'
}
```

## Demonstration

| And you can pick a year  | You can pick a month |
| ------------------------ | -------------------- |
| <img src="https://preview.ibb.co/mxCCOQ/933df630_0f78_486a_bed0_ae19d318c2d0.jpg" width="180" height="320">  | <img src="https://preview.ibb.co/enesOQ/6d12ae79_7fd6_46de_8075_1771df030571.jpg" width="180" height="320">  |

## You can simply create your dialog in code, using next lines

```Java
YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(this, new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                yearMonth.setText(dateFormat.format(calendar.getTime()));
            }
        });
```
1st argument - Context.

2nd - Date set listener.

### And after this just show it whenever you need it to appear.

```Java
yearMonthPickerDialog.show();
  ```
## If you want to add your style then add themeId to the constructor as a third argument
for exemple:

Add your custom style to android resources

```XML
   <!-- Dialog default theme. -->
    <style name="MyDialogTheme" parent="Theme.AppCompat.Light.Dialog.Alert">
        <item name="colorControlNormal">@android:color/white</item>
        <item name="colorControlActivated">@color/colorPrimary</item>
        <item name="textColorAlertDialogListItem">@android:color/white</item>
        <item name="colorAccent">@color/colorPrimary</item>
        <item name="android:textColorPrimary">@android:color/black</item>
        <item name="android:windowBackground">@drawable/dialog_background</item>
    </style>
```

It would add colors to the controls and text.
Then add a content drawable for backgound. Example:

```XML
<?xml version="1.0" encoding="utf-8"?>

<inset xmlns:android="http://schemas.android.com/apk/res/android"
    android:insetLeft="16dp"
    android:insetTop="16dp"
    android:insetRight="16dp"
    android:insetBottom="16dp">

    <shape android:shape="rectangle">
        <corners android:radius="2dp" />
        <solid android:color="@color/colorMainBackground" />
    </shape>

</inset>
```

use example : ``` R.style.MyDialogTheme ```

And you will have your custom theme applied.

## Customizing dialog title

If you want to change title text color, then add a int color value as a 4th constructor parameter.

use example : ``` R.color.MyTextTitleColor ```

Also, if you want to change color of title view, you have to change you main theme primary color, because it depends on it's value.

### Check demonstraction project for more details.
