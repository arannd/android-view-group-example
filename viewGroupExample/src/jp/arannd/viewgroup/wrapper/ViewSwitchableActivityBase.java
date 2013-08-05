package jp.arannd.viewgroup.wrapper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

public abstract class ViewSwitchableActivityBase extends Activity {
	private class ViewStackData {
		private IViewSwitchable viewSwitchable;
		public int requestCode;

		private ViewStackData() {

		}

		public int getRequestCode() {
			return requestCode;
		}

		public IViewSwitchable getViewSwitchable() {
			return viewSwitchable;
		}

		public void setRequestCode(int requestCode) {
			this.requestCode = requestCode;
		}

		public void setViewSwitchable(IViewSwitchable viewSwitchable) {
			this.viewSwitchable = viewSwitchable;
		}
	}

	private ViewGroup contentViewGroup = null;

	private IViewSwitchableActivityParam viewGroupManageActivityParam = null;

	private IViewSwitchable viewSwitchable;

	ArrayList<ViewStackData> viewStackDataList = new ArrayList<ViewStackData>();

	private IViewSwitchableActivityParam getActivityParam() {
		if (viewGroupManageActivityParam == null) {
			viewGroupManageActivityParam = getViewGroupManageActivityParam();
		}
		return viewGroupManageActivityParam;
	}

	protected ViewGroup getContentViewGroup() {
		if (contentViewGroup == null) {
			contentViewGroup = (ViewGroup) findViewById(getActivityParam()
					.getCntentViewGroupLayoutId());
		}
		return contentViewGroup;
	}

	/**
	 * 管理用に必要なパラメータを取得します。
	 * 
	 * @return
	 */
	protected abstract IViewSwitchableActivityParam getViewGroupManageActivityParam();

	/**
	 * 現在表示中のViewGroupを取得します。
	 * 
	 * @return　現在表示中のViewGroupを返します。
	 */
	public IViewSwitchable getViewSwitchable() {
		return viewSwitchable;
	}

	/**
	 * Activityから結果をViewGroupに反映させます。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getViewSwitchable().onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 戻るを押された時の動作
	 */
	@Override
	public void onBackPressed() {
		if (!getViewSwitchable().onBackPressed()) {
			if (viewStackDataList.size() > 0) {
				int index = viewStackDataList.size() - 1;
				ViewStackData stackData = viewStackDataList.get(index);
				stackData.getViewSwitchable().setViewSwitchableContent(this);
				onChangeView(stackData.getViewSwitchable());
				stackData.getViewSwitchable().onBackForViewStack(
						stackData.getRequestCode());
				viewStackDataList.remove(index);
			} else {
				super.onBackPressed();
			}
		}
	}

	/**
	 * 現在のContentを切り替えます。
	 * 
	 * @param viewSwitchable
	 */
	public void onChangeView(IViewSwitchable viewSwitchable) {
		getContentViewGroup().removeAllViews();
		if (getViewSwitchable() != null) {
			getViewSwitchable().onDestroy();
		}
		setViewSwitchable(null);
		setViewSwitchable(viewSwitchable);
		getContentViewGroup().addView(getViewSwitchable().getView());
	}

	/**
	 * ViewSwitchをStackしながら画面遷移を行います。
	 * 
	 * @param viewSwitchable
	 */
	public void onChangeViewStack(IViewSwitchable viewSwitchable,
			int requestCode) {
		getViewSwitchable().clearViewSwitchableContent();
		ViewStackData viewStackData = new ViewStackData();
		viewStackData.setViewSwitchable(getViewSwitchable());
		viewStackData.setRequestCode(requestCode);
		viewStackDataList.add(viewStackData);
		onChangeView(viewSwitchable);
	}

	/**
	 * 現在表示中のViewGroupを設定します。
	 * 
	 * @return　現在表示中のViewGroupを返します。
	 */
	protected void setViewSwitchable(IViewSwitchable viewSwitchable) {
		this.viewSwitchable = viewSwitchable;
	}

}
