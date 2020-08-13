
public class BookViewAction {

	@Resource
	@ActionForm
	protected BookViewForm bookViewForm;

	// �o�^��ʂւ̎��s���\�b�h
	@Execute(validator = false)
	public String index() {
		return "bookView.jsp";
	}

	@Execute(validator = false)
	public String viewBook() {
		// S2Container������
		SingletonS2ContainerFactory.init();
		// �C���^�[�t�F�C�X�����������R���|�[�l���g�擾
		BookView bookView = SingletonS2Container.getComponent("BookView");
		// ���Џ���n�������̃C���^�[�t�F�C�X�̃��\�b�h
		bookView.viewBook(bookViewForm);

		bookViewForm.view_GR = BookViewForm.bookView.GR;


		// ��ʑJ��
		return "bookView.jsp";
	}
}

