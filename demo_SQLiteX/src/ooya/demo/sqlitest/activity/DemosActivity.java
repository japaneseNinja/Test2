package ooya.demo.sqlitest.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 * @author Oya
 *
 */
public class DemosActivity extends ListActivity {

	// //////////////////////////////////////////////////////////////////////////
	// staticフィールド
	// //////////////////////////////////////////////////////////////////////////

	private static String[][] DATAS = new String[][] {
	{"SQLite", UserListActivity.class.getCanonicalName()}
	};


	// //////////////////////////////////////////////////////////////////////////
	// イベントメソッド
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<String> items = new ArrayList<String>();
		for (String[] data : DATAS) {
			items.add(data[0]);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		String className = DATAS[position][1];
		Class<?> cls = null;
		try {
			cls = Class.forName(className);
		} catch (Exception e) {
		}

		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

}
