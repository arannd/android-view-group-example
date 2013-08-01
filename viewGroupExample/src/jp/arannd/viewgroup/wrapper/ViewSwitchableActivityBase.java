package jp.arannd.viewgroup.wrapper;

import android.app.Activity;
import android.content.Intent;

public abstract class ViewGroupManageActivityBase extends Activity {
	private IViewGroup contentViewGroup;

	/**
	 * 現在表示中のViewGroupを取得します。
	 * 
	 * @return　現在表示中のViewGroupを返します。
	 */
	public IViewGroup getContentViewGroup() {
		return contentViewGroup;
	}

	/**
	 * 管理用に必要なパラメータを取得します。
	 * 
	 * @return
	 */
	protected abstract IViewGroupManageActivityParam getViewGroupManageActivityParams();

	/**
	 * Activityから結果をViewGroupに反映させます。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getContentViewGroup().onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 戻るを押された時の動作
	 */
	@Override
	public void onBackPressed() {
		if (!getContentViewGroup().onBackPressed()) {
			super.onBackPressed();
		}
	}

	/**
	 * 現在のContentを切り替えます。
	 * 
	 * @param viewGroup
	 */
	public void onChangeView(IViewGroup viewGroup) {
		getViewGroupManageActivityParams().getCntentViewGroup()
				.removeAllViews();
		if (getContentViewGroup() != null) {
			getContentViewGroup().onDestroy();
		}
		setContentViewGroup(null);
		setContentViewGroup(viewGroup);
		getViewGroupManageActivityParams().getCntentViewGroup().addView(
				getContentViewGroup().getView());
	}

	/**
	 * 現在表示中のViewGroupを設定します。
	 * 
	 * @return　現在表示中のViewGroupを返します。
	 */
	protected void setContentViewGroup(IViewGroup contentViewGroup) {
		this.contentViewGroup = contentViewGroup;
	}

}
