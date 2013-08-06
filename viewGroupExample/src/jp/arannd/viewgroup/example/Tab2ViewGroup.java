package jp.arannd.viewgroup.example;

import java.util.ArrayList;
import java.util.List;

import jp.arannd.viewgroup.wrapper.ViewSwitchableBase;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import com.example.stackview.R;

public class Tab2ViewGroup extends ViewSwitchableBase<MainActivity> {

	public class ViewMode {
		public final static int TOP = 0;
	}

	private ViewGroup firstGroup;

	public List<Hoge> hogeList = new ArrayList<Hoge>();

	private ViewGroup loadGroup;

	public Tab2ViewGroup(MainActivity activity) {
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
				for (int i = 0; i < 40000; i++) {
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

	}

	@Override
	protected int getContextLayoutId() {
		return R.layout.activity_tab2;
	}

	public void hiddenLoadingView() {
		int firstVisibility = getVisibilityModel(ViewMode.TOP);
		int loadVisibility = View.GONE;
		firstGroup.setVisibility(firstVisibility);
		loadGroup.setVisibility(loadVisibility);
	}

	@Override
	public boolean onBackPressed() {
		boolean result = false;
		if (getCurrentMode() == ViewMode.TOP) {
		}
		return result;
	}

	@Override
	protected void setElements() {
		super.setElements();
		firstGroup = (ViewGroup) findViewById(R.id.firstLayout);
		loadGroup = (ViewGroup) findViewById(R.id.loadLayout);
	}

	public void showLoadingView() {
		int firstVisibility = View.GONE;
		int loadVisibility = View.VISIBLE;
		firstGroup.setVisibility(firstVisibility);
		loadGroup.setVisibility(loadVisibility);
	}
}
