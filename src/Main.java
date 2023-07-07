import base.BaseController;
import base.BaseModel;
import base.BaseView;

public class Main {
    public static void main(String[] args) {
        printMainMenu();
    }

    public static void printMainMenu()  {

        BaseView baseView = new BaseView();
        BaseModel baseModel = new BaseModel();
        BaseController baseController = new BaseController(baseModel,baseView);
        baseController.showFirstMenu();
    }

}