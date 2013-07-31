package jp.arannd.viewgroup.wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class ViewGroupBase<TActivity extends Activity> implements
		IViewGroup {
	protected class NavigateOnClickListener implements OnClickListener {
		private final int mode;

		public NavigateOnClickListener(int mode) {
			this.mode = mode;
		}

		@Override
		public void onClick(View v) {
			doChangeViewAction(mode);
		}

	}

	private final TActivity activity;
	private final Context context;
	private int currentMode = 0;
	private int prevCurrentMode = 0;

	private View view;

	public ViewGroupBase(TActivity activity) {
		this.activity = activity;
		this.context = activity;
		initialize();
		setElements();
		setEvents();
	}

	/**
	 * Viewの切替時の処理を行います。
	 */
	protected abstract void doChangeView();

	/**
	 * Viewの切替時の処理を行います。
	 * 
	 * @param mode
	 *            変更先のModeを設定して下さい。
	 */
	protected void doChangeViewAction(int mode) {
		currentMode = mode;
		doChangeView();
		prevCurrentMode = mode;
	}

	/**
	 * ViewからIDを指定し要素を取得します。
	 * 
	 * @param id
	 *            取得したい要素のIDを設定して下さい。
	 * @return
	 */
	protected View findViewById(int id) {
		return getView().findViewById(id);
	}

	/**
	 * 親ActivityのInstanceを取得します。
	 * 
	 * @return 親ActivityのInstanceを返します。
	 */
	protected TActivity getActivity() {
		return activity;
	}

	/**
	 * 親ActivityのContextのInstanceを取得します。
	 * 
	 * @return 親ActivityのContext Instanceを返します。
	 */
	protected Context getContext() {
		return context;
	}

	/**
	 * Layoutファイルを取得します。
	 * 
	 * @return Layoutファイルを返します。
	 */
	protected abstract int getContextLayoutId();

	/**
	 * 現在の表示モードを取得します。
	 * 
	 * @return 現在の表示モードを返します。
	 */
	public int getCurrentMode() {
		return currentMode;
	}

	public int getPrevCurrentMode() {
		return prevCurrentMode;
	}

	/**
	 * 現在のViewを取得します。
	 * 
	 * @return 現在のViewを返します。
	 */
	public View getView() {
		return view;
	}

	/**
	 * 現在のモードから表示・非表示を取得します。
	 * 
	 * @param viewMode
	 *            表示させたい対象のModeを設定して下さい。
	 * @return 表示させたい対象のModeとCurrentModeが同一である場合 表示(VISIBLE)
	 *         それ以外は非表示(GONE)を返します。
	 */
	protected int getVisibilityModel(int viewMode) {
		return getCurrentMode() == viewMode ? View.VISIBLE : View.GONE;
	}

	/**
	 * Load完了時の処理を行います。
	 */
	protected abstract void hiddenLoadingView();

	/**
	 * 初期化
	 */
	protected void initialize() {
		setView(getActivity().getLayoutInflater().inflate(getContextLayoutId(),
				null));
	}

	/**
	 * Activityからの結果を処理します。
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * 戻るの処理
	 * 
	 * @return 現在のViewに戻る要素があれば True を返し 戻る要素がなければ False を返します。
	 */
	public abstract boolean onBackPressed();

	/**
	 * タブが切り替わった時点で現在のインスタンスを破棄する
	 */
	@Override
	public void onDestroy() {

	}

	/**
	 * 現在の表示モードを設定します。
	 * 
	 * @param mode
	 */
	protected void setCurrentMode(int mode) {
		this.currentMode = mode;
	};

	/**
	 * 画面要素を設定する拡張ポイントです。
	 */
	protected void setElements() {
	}

	/**
	 * 画面要素に対するEventを設定する拡張ポイントです。
	 */
	protected void setEvents() {
	}

	public void setPrevCurrentMode(int prevCurrentMode) {
		this.prevCurrentMode = prevCurrentMode;
	}

	/**
	 * Viewを設定します。
	 * 
	 * @param view
	 *            表示したいViewを設定して下さい。
	 */
	protected void setView(View view) {
		this.view = view;
	}

	/**
	 * ロード開始時の処理を行います。
	 */
	protected abstract void showLoadingView();

	/**
	 * 親ActivityからActivityを呼び出します。
	 * 
	 * @param intent
	 */
	protected void startActivity(Intent intent) {
		getActivity().startActivity(intent);
	}

	/**
	 * 親ActivityからActivityを呼び出します。
	 * 
	 * @param intent
	 * @param requestCode
	 */
	protected void startActivityForResult(Intent intent, int requestCode) {
		getActivity().startActivityForResult(intent, requestCode);
	}
}
