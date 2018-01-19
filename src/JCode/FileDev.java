package JCode;

import java.io.File;

public class FileDev extends File {
    public FileDev(String pathname) {
        super(pathname);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
