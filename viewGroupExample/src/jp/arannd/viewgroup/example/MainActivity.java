package jp.arannd.viewgroup.example;

import jp.arannd.viewgroup.wrapper.IViewGroup;

import com.example.stackview.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity {

	private class TabMode {
		public static final int FIRST = 0;
		public static final int SECOND = 1;
	}

	private IViewGroup contentViewGroup;

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
	public void onBackPressed() {
		if (!contentViewGroup.onBackPressed()) {
			super.onBackPressed();
		}

	}

	public void onChangeView(int mode) {
		viewGroup.removeAllViews();
		if (contentViewGroup != null) {
			contentViewGroup.onDestroy();
		}
		contentViewGroup = null;
		contentViewGroup = getContentViewGroup(mode);
		viewGroup.addView(contentViewGroup.getView());
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
						onChangeView(TabMode.FIRST);
					}

				});
		((Button) findViewById(R.id.secondButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onChangeView(TabMode.SECOND);
					}

				});
		onChangeView(TabMode.FIRST);
	}

}
