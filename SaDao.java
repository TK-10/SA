package sa2.dao;


	// 現在時刻を取得する ts使いまわし
	Date date = new Date();
	long time = date.getTime();
	Timestamp ts = new Timestamp(time);

	// 日時のみ取得
	Calendar now = Calendar.getInstance(); // インスタンス化

	int h = now.get(now.HOUR);   // 時を取得
	int m = now.get(now.MINUTE); // 分を取得
	int s = now.get(now.SECOND); // 秒を取得

	//画像パス重複を防ぐために加える乱数の代わりに、時間を合計したものを合計
	int tm = h + m + s;

	// 書籍登録メソッド
	public int insertBook(BookEtForm etForm) {

		// 自動採番の記述
		String id = jdbcManager.selectBySql(String.class,
				" SELECT TBL_SEQ.NEXTVAL FROM DUAL ").getSingleResult();

		entity.bookNo = id;
		entity.bookNm = etForm.name;
		entity.bookImageFilePath = tm + etForm.image.getFileName();
		entity.recordDate = ts; // 現在時刻tsを代入
		return jdbcManager.insert(entity).execute();

	}

	//CSV登録用
	public int csvInsertBook(BookEtForm etForm) {

		// コードの自動採番の記述
		String id = jdbcManager.selectBySql(String.class,
				" SELECT TBL_SEQ.NEXTVAL FROM DUAL ").getSingleResult();

		entity.bookNo = id;
		entity.bookNm = name;
		entity.recordDate = ts; // 現在時刻tsを代入
		return jdbcManager.insert(entity).execute();

	}

	// 重複チェックメソッド
	public long selectBookCount(BookEtForm etForm) {
		// DBに存在している書籍であればインサート処理しないように登録件数を取得
		return jdbcManager.from(Sa.class).where("BKITM_NM = ? ",
				etForm.name).getCount();
	}

	// 前方一致(starts)、あいまい検索メソッド(contains)
	public List<Sa> searchBooks(BookListForm listform) {
		return jdbcManager.from(Sa.class).where(
				new SimpleWhere().starts("bookNo", listform.code).contains(
						"bookNM", listform.name)).getResultList();

	}

	public int csvBookUpdate(String code, String name, String GR) {

		// 変更画面で入力された値の取得
		this.code = code;
		this.name = name;
		this.GR = GR;
		afterdate = ts;

		// 書籍変更処理をupdateBysqlで実行する
		return jdbcManager.updateBySql(
				" update TBK_BKITM set" + " BKITM_NM = ?,"
						+ " GR = ?,"
						+ " RECORD_DATE = ?" + " where " + " BKITM_NO = ?",
				// entityクラスで定義されている型を指定
				String.class , String.class,
				Timestamp.class, String.class).params(name,GR,
				afterdate, code).execute();

	}
	
	//書籍削除メソッド
	public int delBook(BookListForm listForm) {

		entity.bookNo = listForm.code;

		entity.version = (long) 1;

		return jdbcManager.delete(entity).execute();

	}

	// 書籍削除で削除済みエラーメソッド
	public long sakujoCode(BookListForm listform) {
		return jdbcManager.from(Sa.class).where("BKITM_NO = ? ",
				listform.code).getCount();
	}

	// 照会画面の値保持メソッド
	public Sa viewBookSelect(BookViewForm viewform) {
		return jdbcManager.from(Sa.class).where("BKITM_NO = ? ",
				viewform.code).getSingleResult();
	}
