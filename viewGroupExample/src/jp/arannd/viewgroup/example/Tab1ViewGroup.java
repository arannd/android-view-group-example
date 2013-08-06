package jp.arannd.viewgroup.example;

import java.util.ArrayList;
import java.util.List;

import jp.arannd.viewgroup.wrapper.ViewSwitchableBase;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.stackview.R;

public class Tab1ViewGroup extends ViewSwitchableBase<MainActivity> {

	private class ViewMode {
		public final static int SECCOND = 1;
		public final static int TOP = 0;
	}

	private class RequestCode {
		public final static int TAB2 = 0;
	}

	private ViewGroup firstGroup;

	public List<Hoge> hogeList = new ArrayList<Hoge>();

	private ViewGroup loadGroup;

	private ViewGroup seccondGroup;

	public Tab1ViewGroup(MainActivity activity) {
		super(activity);
		new AsyncTask<String, Void, List<Hoge>>() {

			@Override
			protected List<Hoge> doInBackground(String... params) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Hoge> list = new ArrayList<Hoge>();
				for (int i = 0; i < 50000; i++) {
					Hoge hoge = new Hoge();
					hoge.setId(i);
					hoge.setName("name " + i);
					list.add(hoge);
				}
				return list;
			}

			@Override
			protected void onPostExecute(List<Hoge> result) {
				super.onPostExecute(result);
				hogeList.addAll(result);
				result = null;
				hiddenLoadingView();
			}
		}.execute("");
	}

	@Override
	protected void doChangeView() {
		hiddenLoadingView();
	}

	@Override
	protected int getContextLayoutId() {
		return R.layout.activity_tab1;
	}

	public void hiddenLoadingView() {
		int firstVisibility = getVisibilityModel(ViewMode.TOP);
		int seccondVisibility = getVisibilityModel(ViewMode.SECCOND);
		int loadVisibility = View.GONE;
		firstGroup.setVisibility(firstVisibility);
		seccondGroup.setVisibility(seccondVisibility);
		loadGroup.setVisibility(loadVisibility);
	}

	@Override
	public boolean onBackPressed() {
		boolean result = false;
		if (getCurrentMode() == ViewMode.TOP) {
		}
		if (getCurrentMode() == ViewMode.SECCOND) {
			doChangeViewAction(ViewMode.TOP);
			result = true;
		}
		return result;
	}

	@Override
	protected void setElements() {
		super.setElements();
		firstGroup = (ViewGroup) findViewById(R.id.firstLayout);
		seccondGroup = (ViewGroup) findViewById(R.id.seccondLayout);
		loadGroup = (ViewGroup) findViewById(R.id.loadLayout);
	}

	@Override
	protected void setEvents() {
		super.setEvents();
		findViewById(R.id.hogeButton).setOnClickListener(
				new NavigateOnClickListener(ViewMode.SECCOND));
		findViewById(R.id.tab2Button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChangeViewStack(new Tab2ViewGroup(getActivity()),
						RequestCode.TAB2);
			}
		});
	}

	public void showLoadingView() {
		int firstVisibility = View.GONE;
		int seccondVisibility = View.GONE;
		int loadVisibility = View.VISIBLE;
		firstGroup.setVisibility(firstVisibility);
		seccondGroup.setVisibility(seccondVisibility);
		loadGroup.setVisibility(loadVisibility);
	}
}
