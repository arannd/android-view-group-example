package jp.arannd.viewgroup.example;

import jp.arannd.viewgroup.wrapper.IViewSwitchable;
import jp.arannd.viewgroup.wrapper.IViewSwitchableActivityParam;
import jp.arannd.viewgroup.wrapper.ViewSwitchableActivityBase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.stackview.R;

public class MainActivity extends ViewSwitchableActivityBase {

	private class TabMode {
		public static final int FIRST = 0;
		public static final int SECOND = 1;
	}

	private IViewSwitchable getContentViewSwitchable(int mode) {
		IViewSwitchable viewGroup = null;
		if (mode == TabMode.FIRST) {
			viewGroup = new Tab1ViewGroup(this);
		}
		if (mode == TabMode.SECOND) {
			viewGroup = new Tab2ViewGroup(this);
		}
		return viewGroup;
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
		((Button) findViewById(R.id.firstButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onChangeView(getContentViewSwitchable(TabMode.FIRST));
					}

				});
		((Button) findViewById(R.id.secondButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onChangeView(getContentViewSwitchable(TabMode.SECOND));
					}

				});
		onChangeView(getContentViewSwitchable(TabMode.FIRST));
	}

}
