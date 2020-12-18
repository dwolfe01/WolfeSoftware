package database;

import java.io.IOException;
import java.io.Serializable;

public interface DataLayer {
    void store(String key, Serializable object) throws IOException;
    <T> T retrieve(String key) throws IOException, ClassNotFoundException;
}
