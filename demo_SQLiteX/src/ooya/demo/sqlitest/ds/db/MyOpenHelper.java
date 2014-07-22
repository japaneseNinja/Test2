package ooya.demo.sqlitest.ds.db;

import ooya.demo.sqlitest.ds.dao.UserDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelperクラス.
 * @author Oya
 *
 */
public class MyOpenHelper extends SQLiteOpenHelper {

	// //////////////////////////////////////////////////////////////////////////
	// staticフィールド
	// //////////////////////////////////////////////////////////////////////////

	/** タグ */
	private static final String TAG = "##SqliteOpenHelperDemo##";

	/** バージョン */
	private static final int DATABASE_VERSION = 1;
	/** DB名 */
    public static final String DATABASE_NAME = "demo.db";

	// //////////////////////////////////////////////////////////////////////////
	// インスタンスフィールド
	// //////////////////////////////////////////////////////////////////////////

    /**
     * コンストラクタ
     * @param context コンテキスト
     */
	public MyOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// //////////////////////////////////////////////////////////////////////////
	// イベントフィールド
	// //////////////////////////////////////////////////////////////////////////

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		db.execSQL(UserDao.CREATE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade()");
		// 追加SQLを記述
	}

}
