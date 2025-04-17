package CardGameData;

import java.io.Serializable;

public class CheckData implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean checked;

    public CheckData(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}

