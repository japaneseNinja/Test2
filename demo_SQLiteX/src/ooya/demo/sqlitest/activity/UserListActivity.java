package ooya.demo.sqlitest.activity;

import java.util.ArrayList;

import ooya.demo.R;
import ooya.demo.sqlitest.activity.adapter.UserListAdapter;
import ooya.demo.sqlitest.ds.dao.UserDao;
import ooya.demo.sqlitest.dto.ds.UserDto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 検索結果表示.
 * @author Oya
 *
 */
public class UserListActivity extends Activity implements OnClickListener, OnItemClickListener{

	// //////////////////////////////////////////////////////////////////////////
	// インスタンスフィールド
	// //////////////////////////////////////////////////////////////////////////

	/** 登録 */
	private Button registButton;
	/** リスト */
	ListView listView;

	// //////////////////////////////////////////////////////////////////////////
	// イベントメソッド
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlist);

		registButton = (Button)findViewById(R.id.userlist_register_button);
		registButton.setOnClickListener(this);

		listView = (ListView)findViewById(R.id.userlist_listview);

		UserDao userDao = new UserDao(this);
		ArrayList<UserDto> users = userDao.select(new UserDto());

		UserListAdapter adapter =  new UserListAdapter(this, users);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();

		// 再取得
		UserDao userDao = new UserDao(this);
		ArrayList<UserDto> users = userDao.select(new UserDto());

		UserListAdapter adapter =  new UserListAdapter(this, users);

		listView.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {

		if (v.equals(registButton)) {	// 登録ボタン押下
			// 登録画面に遷移
			Intent intent = new Intent(this, UserEditActivity.class);
			intent.putExtra("state", "UPDATA");
			startActivity(intent);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		UserDto user = (UserDto) parent.getAdapter().getItem(position);

		// 登録画面に遷移
		Intent intent = new Intent(this, UserEditActivity.class);
		intent.putExtra("state", "UPDATA");
		intent.putExtra("name", user.name);
		intent.putExtra("age", user.age);
		intent.putExtra("hobby", user.hobby);
		startActivity(intent);



	}
}
