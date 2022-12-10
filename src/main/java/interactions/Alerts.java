package interactions;

import javafx.scene.control.Alert;

public class Alerts {
    static Alert a = new Alert(Alert.AlertType.NONE);

    public static void alertInfo(String message){
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        a.setTitle(message);
        a.show();
    }

    public static void alertError(String message){
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.setTitle(message);
        a.show();
    }

    public static Alert alertConfirm(){
        return a;
    }
}
