package com.androidbook.simplelayout;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public abstract class MenuActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.layout_menu, menu);
		
		menu.findItem(R.id.absolute_menu_item).setIntent(new Intent(this, AbsoluteLayoutActivity.class));
		menu.findItem(R.id.frame_menu_item).setIntent(new Intent(this, FrameLayoutActivity.class));
		menu.findItem(R.id.relative_menu_item).setIntent(new Intent(this, RelativeLayoutActivity.class));
		menu.findItem(R.id.linear_menu_item).setIntent(new Intent(this, LinearLayoutActivity.class));
		menu.findItem(R.id.table_menu_item).setIntent(new Intent(this, TableLayoutActivity.class));
		menu.findItem(R.id.multi_menu_item).setIntent(new Intent(this, MultipleLayoutActivity.class));
		
		super.onCreateOptionsMenu(menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		startActivity(item.getIntent());
		
		super.onOptionsItemSelected(item);
		return true;
	}

}
