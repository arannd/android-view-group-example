package jp.arannd.viewgroup.example;

import jp.arannd.viewgroup.wrapper.IViewSwitchable;
import jp.arannd.viewgroup.wrapper.IViewSwitchableActivityParam;
import jp.arannd.viewgroup.wrapper.ViewSwitchableActivityBase;
import android.os.Bundle;

import com.example.stackview.R;

public class MainActivity extends ViewSwitchableActivityBase {

	private class TabMode {
		public static final int FIRST = 0;
		public static final int SECOND = 1;
	}

	@Override
	protected IViewSwitchableActivityParam getViewGroupManageActivityParam() {
		return new IViewSwitchableActivityParam() {

			@Override
			public int getCntentViewGroupLayoutId() {
				return R.id.content;
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.firstButton).setOnClickListener(
				new TabChangeOnClickListener(TabMode.FIRST));
		findViewById(R.id.firstButton).setOnClickListener(
				new TabChangeOnClickListener(TabMode.SECOND));
		onChangeView(createContentViewSwitchable(TabMode.FIRST));
	}

	@Override
	protected IViewSwitchable createContentViewSwitchable(int mode) {
		IViewSwitchable viewSwitchable = null;
		if (mode == TabMode.FIRST) {
			viewSwitchable = new Tab1ViewGroup(this);
		}
		if (mode == TabMode.SECOND) {
			viewSwitchable = new Tab2ViewGroup(this);
		}
		return viewSwitchable;
	}

}
