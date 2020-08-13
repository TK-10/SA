
public class BookViewAction {

	@Resource
	@ActionForm
	protected BookViewForm bookViewForm;

	// 登録画面への実行メソッド
	@Execute(validator = false)
	public String index() {
		return "bookView.jsp";
	}

	@Execute(validator = false)
	public String viewBook() {
		// S2Container初期化
		SingletonS2ContainerFactory.init();
		// インターフェイスを実装したコンポーネント取得
		BookView bookView = SingletonS2Container.getComponent("BookView");
		// 書籍情報を渡す処理のインターフェイスのメソッド
		bookView.viewBook(bookViewForm);

		bookViewForm.view_GR = BookViewForm.bookView.GR;


		// 画面遷移
		return "bookView.jsp";
	}
}

