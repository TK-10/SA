package sa2.dao;


	// ���ݎ������擾���� ts�g���܂킵
	Date date = new Date();
	long time = date.getTime();
	Timestamp ts = new Timestamp(time);

	// �����̂ݎ擾
	Calendar now = Calendar.getInstance(); // �C���X�^���X��

	int h = now.get(now.HOUR);   // �����擾
	int m = now.get(now.MINUTE); // �����擾
	int s = now.get(now.SECOND); // �b���擾

	//�摜�p�X�d����h�����߂ɉ����闐���̑���ɁA���Ԃ����v�������̂����v
	int tm = h + m + s;

	// ���Гo�^���\�b�h
	public int insertBook(BookEtForm etForm) {

		// �����̔Ԃ̋L�q
		String id = jdbcManager.selectBySql(String.class,
				" SELECT TBL_SEQ.NEXTVAL FROM DUAL ").getSingleResult();

		entity.bookNo = id;
		entity.bookNm = etForm.name;
		entity.bookImageFilePath = tm + etForm.image.getFileName();
		entity.recordDate = ts; // ���ݎ���ts����
		return jdbcManager.insert(entity).execute();

	}

	//CSV�o�^�p
	public int csvInsertBook(BookEtForm etForm) {

		// �R�[�h�̎����̔Ԃ̋L�q
		String id = jdbcManager.selectBySql(String.class,
				" SELECT TBL_SEQ.NEXTVAL FROM DUAL ").getSingleResult();

		entity.bookNo = id;
		entity.bookNm = name;
		entity.recordDate = ts; // ���ݎ���ts����
		return jdbcManager.insert(entity).execute();

	}

	// �d���`�F�b�N���\�b�h
	public long selectBookCount(BookEtForm etForm) {
		// DB�ɑ��݂��Ă��鏑�Ђł���΃C���T�[�g�������Ȃ��悤�ɓo�^�������擾
		return jdbcManager.from(Sa.class).where("BKITM_NM = ? ",
				etForm.name).getCount();
	}

	// �O����v(starts)�A�����܂��������\�b�h(contains)
	public List<Sa> searchBooks(BookListForm listform) {
		return jdbcManager.from(Sa.class).where(
				new SimpleWhere().starts("bookNo", listform.code).contains(
						"bookNM", listform.name)).getResultList();

	}

	public int csvBookUpdate(String code, String name, String GR) {

		// �ύX��ʂœ��͂��ꂽ�l�̎擾
		this.code = code;
		this.name = name;
		this.GR = GR;
		afterdate = ts;

		// ���ЕύX������updateBysql�Ŏ��s����
		return jdbcManager.updateBySql(
				" update TBK_BKITM set" + " BKITM_NM = ?,"
						+ " GR = ?,"
						+ " RECORD_DATE = ?" + " where " + " BKITM_NO = ?",
				// entity�N���X�Œ�`����Ă���^���w��
				String.class , String.class,
				Timestamp.class, String.class).params(name,GR,
				afterdate, code).execute();

	}
	
	//���Ѝ폜���\�b�h
	public int delBook(BookListForm listForm) {

		entity.bookNo = listForm.code;

		entity.version = (long) 1;

		return jdbcManager.delete(entity).execute();

	}

	// ���Ѝ폜�ō폜�ς݃G���[���\�b�h
	public long sakujoCode(BookListForm listform) {
		return jdbcManager.from(Sa.class).where("BKITM_NO = ? ",
				listform.code).getCount();
	}

	// �Ɖ��ʂ̒l�ێ����\�b�h
	public Sa viewBookSelect(BookViewForm viewform) {
		return jdbcManager.from(Sa.class).where("BKITM_NO = ? ",
				viewform.code).getSingleResult();
	}
