package jp.arannd.viewgroup.wrapper;

import android.view.View;

public interface IViewGroup {
	int getCurrentMode();

	View getView();

	boolean onBackPressed();

	void onDestroy();
}
