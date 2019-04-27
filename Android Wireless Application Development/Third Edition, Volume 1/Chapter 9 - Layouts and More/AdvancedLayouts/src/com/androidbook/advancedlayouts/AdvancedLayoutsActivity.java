package com.androidbook.advancedlayouts;

public class AdvancedLayoutsActivity extends MenuActivity {

    public static final String DEBUG_TAG = "AdvancedLayoutsActivity";

    @Override
    void prepareMenu() {
        addMenuItem("1. Basic Layout", BasicLayoutActivity.class);
        addMenuItem("2. List Layout", MyListActivity.class);
        addMenuItem("3. GridView", GridLayoutActivity.class);
        addMenuItem("4. Tab Layout", TabLayoutActivity.class);
        addMenuItem("5. Adapters", AdaptersActivity.class);
        addMenuItem("6. Styles", StyleSamplesActivity.class);
        addMenuItem("7. Grid, List, Gallery", GridListGalleryMenuActivity.class);
        addMenuItem("8. Dialog", DialogActivity.class);
        addMenuItem("9. Drawer", DrawerActivity.class);
    }

}