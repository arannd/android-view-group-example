package jp.arannd.viewgroup.wrapper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * Viewの切り替えを管理するActivityの基底クラスです。
 * 
 * @author arannd
 * 
 */
public abstract class ViewSwitchableActivityBase extends Activity {
	protected class TabChangeOnClickListener implements OnClickListener {

		private final int mode;

		public TabChangeOnClickListener(int mode) {
			this.mode = mode;
		}

		protected int getModel() {
			return mode;
		}

		@Override
		public void onClick(View v) {
			onChangeView(createContentViewSwitchable(getModel()));
		}
	}

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

	/**
	 * ViewSwichableのインスタンスを作成します。
	 * 
	 * @param mode
	 * @return
	 */
	protected abstract IViewSwitchable createContentViewSwitchable(int mode);

	private final ArrayList<ViewStackData> viewStackDataList = new ArrayList<ViewStackData>();

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
	 * 現在表示中のViewを取得します。
	 * 
	 * @return　現在表示中のViewを返します。
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
				doBackPressed();
			}
		}
	}

	protected void doBackPressed() {
		super.onBackPressed();
	}

	/**
	 * Viewを切り替えます。
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

	@Override
	protected void onResume() {
		super.onResume();
		getViewSwitchable().onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getViewSwitchable().onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		getViewSwitchable().onStop();
	}

	/**
	 * Viewを設定します。
	 */
	protected void setViewSwitchable(IViewSwitchable viewSwitchable) {
		this.viewSwitchable = viewSwitchable;
	}
}
