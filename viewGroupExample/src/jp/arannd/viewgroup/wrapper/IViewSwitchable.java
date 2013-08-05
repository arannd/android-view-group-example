package jp.arannd.viewgroup.wrapper;

import android.content.Intent;
import android.view.View;

public interface IViewSwitchable {
	void clearViewSwitchableContent();

	int getCurrentMode();

	View getView();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void onBackForViewStack(int requestCode);

	boolean onBackPressed();

	void onDestroy();

	void setViewSwitchableContent(ViewSwitchableActivityBase activity);
}
