import java.io.Serializable;

public class ArbitrarySerializableObject implements Serializable {
    private final String value;

    public ArbitrarySerializableObject(String value) {
        this.value = value;
    }

    public String getString(){
        return value;
    }
}