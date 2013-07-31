package jp.arannd.viewgroup.example;

import jp.arannd.viewgroup.wrapper.IViewGroup;
import jp.arannd.viewgroup.wrapper.IViewGroupManageActivityParam;
import jp.arannd.viewgroup.wrapper.ViewGroupManageActivityBase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.stackview.R;

public class MainActivity extends ViewGroupManageActivityBase {

	private class TabMode {
		public static final int FIRST = 0;
		public static final int SECOND = 1;
	}

	private ViewGroup viewGroup;

	private IViewGroup getContentViewGroup(int mode) {
		IViewGroup viewGroup = null;
		if (mode == TabMode.FIRST) {
			viewGroup = new Tab1ViewGroup(this);
		}
		if (mode == TabMode.SECOND) {
			viewGroup = new Tab2ViewGroup(this);
		}
		return viewGroup;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewGroup = (ViewGroup) findViewById(R.id.content);
		((Button) findViewById(R.id.firstButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onChangeView(getContentViewGroup(TabMode.FIRST));
					}

				});
		((Button) findViewById(R.id.secondButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onChangeView(getContentViewGroup(TabMode.SECOND));
					}

				});
		onChangeView(getContentViewGroup(TabMode.FIRST));
	}

	@Override
	protected IViewGroupManageActivityParam getViewGroupManageActivityParams() {
		return new IViewGroupManageActivityParam() {
			@Override
			public ViewGroup getCntentViewGroup() {
				return viewGroup;
			}
		};
	}

}
