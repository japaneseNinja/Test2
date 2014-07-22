package ooya.demo.sqlitest.ds.dao;

import java.util.ArrayList;

import ooya.demo.sqlitest.ds.columns.UserColumns;
import ooya.demo.sqlitest.ds.db.MyOpenHelper;
import ooya.demo.sqlitest.dto.ds.UserDto;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDao {

	// //////////////////////////////////////////////////////////////////////////
	// staticフィールド
	// //////////////////////////////////////////////////////////////////////////

	/** テーブル名 */
	public static final String TABLE_NAME = "user";
	/** テーブル作成 */
	public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " //
			+ UserColumns._ID + " INTEGR PRIMARY KEY ," // ID
			+ UserColumns.NAME + " TEXT," // 名前
			+ UserColumns.AGE + " INTEGR NOT NULL," // 年齢
			+ UserColumns.HOBBY + " TEXT);"; // 趣味

	// //////////////////////////////////////////////////////////////////////////
	// インスタンスフィールド
	// //////////////////////////////////////////////////////////////////////////

	/** SqliteOpenHelper */
	private final MyOpenHelper myOpenHelper;

	/**
	 * コンストラクタ
	 *
	 * @param context
	 */
	public UserDao(Context context) {
		myOpenHelper = new MyOpenHelper(context);
	}

	// //////////////////////////////////////////////////////////////////////////
	// パブリックメソッド
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * INSERT
	 *
	 * @param myOpenHelper
	 *            SQLitesqlOpenHelper
	 * @param user
	 *            ユーザ情報
	 * @return ID(エラー時は-1)
	 */
	public long insert(UserDto user) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UserColumns.NAME, user.name);
		values.put(UserColumns.AGE, user.age);
		values.put(UserColumns.HOBBY, user.hobby);

		Log.d("##INSERT##", values.toString());

		long rowId = db.insert(TABLE_NAME, null, values);

		return rowId;
	}

	/**
	 * トランザクション付きのINSERT
	 *
	 * @param users
	 *            複数のユーザ情報
	 */
	public void insertWithTransaction(ArrayList<UserDto> users) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		db.beginTransaction();
		try {
			for (UserDto user : users) {
				ContentValues values = new ContentValues();
				values.put(UserColumns.NAME, user.name);
				values.put(UserColumns.AGE, user.age);
				values.put(UserColumns.HOBBY, user.hobby);

				Log.d("##INSERT##", values.toString());

				db.insert(TABLE_NAME, null, values);
			}

			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}


	}

	/**
	 * UPDATE
	 *
	 * @param myOpenHelper
	 *            SQLitesqlOpenHelper
	 * @param user
	 *            ユーザ情報
	 * @return 更新件数
	 */
	public int update(UserDto user) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UserColumns.NAME, user.name);
		values.put(UserColumns.AGE, user.age);
		values.put(UserColumns.HOBBY, user.hobby);

		String whereClause = UserColumns.NAME + " LIKE ?";

		Log.d("##UPDATA##", values.toString());
		Log.d("##UPDATA##", whereClause);
		Log.d("##UPDATA##", "? = " + user.name);

		String[] whereArgs = { user.name };
		db.close();

		return db.update(TABLE_NAME, values, whereClause, whereArgs);
	}

	/**
	 * DELETE
	 *
	 * @param myOpenHelper
	 *            SQLitesqlOpenHelper
	 * @param user
	 *            ユーザ情報
	 * @return 削除件数
	 */
	public int delete(UserDto user) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		String whereClause = UserColumns.NAME + " = ? ";
		String[] whereArgs = { user.name };

		Log.d("##DELETE##", whereClause);
		Log.d("##DELETE##", "? = " + user.name);

		db.close();

		return db.delete(TABLE_NAME, whereClause, whereArgs);

	}

	/**
	 * SELECT
	 *
	 * @param myOpenHelper
	 *            SQLitesqlOpenHelper
	 * @param user
	 *            ユーザ情報
	 * @return 検索結果
	 */
	public ArrayList<UserDto> select(UserDto user) {

		SQLiteDatabase db = myOpenHelper.getReadableDatabase();

		String[] columns = { UserColumns._ID, UserColumns.NAME, UserColumns.AGE, UserColumns.HOBBY };

		String selection = "";
		String[] selectionArgsStr = new String[1];
		Cursor cursor = null;

		if (user.name != null && !user.name.isEmpty()) {
			selection += UserColumns.NAME + " LIKE ? ";
			selectionArgsStr[0] = user.name;
			cursor = db.query(TABLE_NAME, columns, selection, selectionArgsStr, null, null, null);
		} else {
			selection += UserColumns.NAME + " LIKE '%' ";
			cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
		}

		Log.d("##SELECT##", selection);

		ArrayList<UserDto> users = new ArrayList<UserDto>();
		while (cursor.moveToNext()) {

			UserDto userDto = new UserDto();
			userDto.id = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns._ID));
			userDto.name = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.NAME));
			userDto.age = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.AGE));
			userDto.hobby = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.HOBBY));

			users.add(userDto);
		}

		cursor.close();
		db.close();

		return users;
	}

	/**
	 * レコード数を取得します.
	 *
	 * @return レコード数
	 */
	public long getCount() {

		SQLiteDatabase db = myOpenHelper.getReadableDatabase();

		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToLast();

		db.close();
		return cursor.getLong(0);

	}


}
