package CardGameData;

import java.io.Serializable;

public class FoldData implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean folded;

    public FoldData(boolean folded) {
        this.folded = folded;
    }

    public boolean isFolded() {
        return folded;
    }
}
