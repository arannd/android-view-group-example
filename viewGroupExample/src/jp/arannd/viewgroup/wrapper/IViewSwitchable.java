package jp.arannd.viewgroup.wrapper;

import android.content.Intent;
import android.view.View;

/**
 * Viewを切り替えることが出来るインターフェイスです。
 * @author arannd
 *
 */
public interface IViewSwitchable {
	void clearViewSwitchableContent();

	int getCurrentMode();

	View getView();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void onBackForViewStack(int requestCode);

	boolean onBackPressed();

	void onDestroy();

	void onStart();

	void onStop();

	void onResume();

	void setViewSwitchableContent(ViewSwitchableActivityBase activity);
}
