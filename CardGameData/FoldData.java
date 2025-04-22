package CardGameData;

import java.io.Serializable;

public class FoldData implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean folded;
    private String username;

    public FoldData(boolean folded, String username) {
        this.folded = folded;
        this.username = username;
    }

    public boolean isFolded() {
        return folded;
    }
    
    public String getUsername()
    {
    	return username;
    }
}
