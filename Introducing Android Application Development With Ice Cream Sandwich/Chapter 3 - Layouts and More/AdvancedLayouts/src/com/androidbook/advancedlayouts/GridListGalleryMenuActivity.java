package com.androidbook.advancedlayouts;


public class GridListGalleryMenuActivity extends MenuActivity {

    
    @Override
    /*
     * The code for all three of these is identical, except for the 
     * class used for the layout in each.
     */
    void prepareMenu() {
        addMenuItem("1. Grid w/Adapter", GridAdapterSampleActivity.class);
        addMenuItem("2. List w/Adapter", ListAdapterSampleActivity.class);
        addMenuItem("3. Gallery w/Adapter", GalleryAdapterSampleActivity.class);
    }

}
