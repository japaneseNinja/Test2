package ooya.demo.sqlitest.activity;

import ooya.demo.R;
import ooya.demo.sqlitest.ds.dao.UserDao;
import ooya.demo.sqlitest.dto.ds.UserDto;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ユーザ登録画面.
 *
 * @author Oya
 *
 */
public class UserEditActivity extends Activity implements OnClickListener {

	// //////////////////////////////////////////////////////////////////////////
	// インスタンスフィールド
	// //////////////////////////////////////////////////////////////////////////

	// View
	/** 入力：名前 */
	private EditText nameEditText;
	/** 入力：年齢 */
	private EditText ageEditText;
	/** 入力：趣味 */
	private EditText hobbyEditText;
	/** 登録ボタン */
	private Button registButton;

	// //////////////////////////////////////////////////////////////////////////
	// イベントメソッド
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.useredit);

		// 名前
		nameEditText = (EditText) findViewById(R.id.useredit_name_edittext);
		// 年齢
		ageEditText = (EditText) findViewById(R.id.useredit_age_edittext);
		// 趣味
		hobbyEditText = (EditText) findViewById(R.id.useredit_hobby_edittext);
		// 追加ボタン
		registButton = (Button) findViewById(R.id.useredit_register_button);
		registButton.setOnClickListener(this);

		String state = getIntent().getStringExtra("state");

		if ("UPDATA".equals(state)) { // 更新
			String name = getIntent().getStringExtra("name");
			nameEditText.setText(name);
			String age = getIntent().getStringExtra("age");
			ageEditText.setText(age);
			String hobby = getIntent().getStringExtra("hobby");
			hobbyEditText.setText(hobby);
		}

	}

	@Override
	public void onClick(View v) {

		if (v.equals(registButton)) { // 登録ボタン押下

			UserDao userDao = new UserDao(getApplicationContext());
			UserDto userDto = new UserDto();
			userDto.name = nameEditText.getText().toString();
			userDto.age = ageEditText.getText().toString();
			userDto.hobby = hobbyEditText.getText().toString();

			long rowId = userDao.insert(userDto);
			if (rowId != -1) { // 登録成功
				finish();
			} else { // 登録失敗
				Toast.makeText(this, "登録に失敗しました", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
