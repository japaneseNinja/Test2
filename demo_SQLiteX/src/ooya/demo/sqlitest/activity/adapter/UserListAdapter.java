package ooya.demo.sqlitest.activity.adapter;

import java.util.List;

import ooya.demo.R;
import ooya.demo.sqlitest.dto.ds.UserDto;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * UserListアダプター.
 * @author admin
 *
 */
public class UserListAdapter extends ArrayAdapter<UserDto> {

	// //////////////////////////////////////////////////////////////////////////
	// インスタンスフィールド
	// //////////////////////////////////////////////////////////////////////////

	private final LayoutInflater layoutInflater;

	/**
	 * コンストラクタ
	 * @param context コンテキスト
	 * @param items ユーザ情報
	 */
	public UserListAdapter(Context context, List<UserDto> items) {
		super(context, 0, items);
		layoutInflater = LayoutInflater.from(context);
	}

	// //////////////////////////////////////////////////////////////////////////
	// イベントメソッド
	// //////////////////////////////////////////////////////////////////////////

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder = null;

		if (convertView == null) {
			view = layoutInflater.inflate(R.layout.userlist_listitem, null);
			holder = new ViewHolder();
			holder.idTextView = (TextView)view.findViewById(R.id.listitem_id_textview);
			holder.nameTextView = (TextView)view.findViewById(R.id.listitem_name_textview);
			holder.ageTextView = (TextView)view.findViewById(R.id.listitem_age_texteview);
			holder.hobbyTextView = (TextView)view.findViewById(R.id.listitem_hobby_texteview);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		UserDto user = getItem(position);
		holder.idTextView.setText(user.id);
		holder.nameTextView.setText(user.name);
		holder.ageTextView.setText(user.age);
		holder.hobbyTextView.setText(user.hobby);

		return view;
	}

	// //////////////////////////////////////////////////////////////////////////
	// インナークラス
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ビューホルダー
	 */
	private class ViewHolder{

		TextView idTextView;
		TextView nameTextView;
		TextView ageTextView;
		TextView hobbyTextView;
	}
}
