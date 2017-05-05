package guesscolor.explead.ru.guesscolor.beans;


import guesscolor.explead.ru.guesscolor.MainActivity;
import guesscolor.explead.ru.guesscolor.utils.Utils;

/**
 * Created by develop on 30.01.2017.
 */

public class ButtonLevel {

    private int number;

    public static final int STATUS_OPEN = 1, STATUS_CURRENT = 2, STATUS_CLOSE = 3;
    private int status;

    public ButtonLevel(int number) {
        this.number = number;
        installStatus();
    }

    public void installStatus() {
        int current = MainActivity.getPref().getInt(Utils.CURRENT_LEVEL, 1);
        if(number == current) {
            status = STATUS_CURRENT;
        }
        if(number > current) {
            status = STATUS_CLOSE;
        }
        if(number < current) {
            status = STATUS_OPEN;
        }
    }

    public int getStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

}