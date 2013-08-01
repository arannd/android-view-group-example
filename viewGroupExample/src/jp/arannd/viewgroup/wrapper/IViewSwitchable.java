package jp.arannd.viewgroup.wrapper;

import android.content.Intent;
import android.view.View;

public interface IViewGroup {
	int getCurrentMode();

	View getView();

	boolean onBackPressed();

	void onDestroy();

	void onActivityResult(int requestCode, int resultCode, Intent data);
}
