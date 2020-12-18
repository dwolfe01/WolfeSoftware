package database;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class NonHeapDataStructure implements DataLayer {

    private ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
    private int endOfBuffer = 0;
    private record MemoryLocation(int size, int offset) {}
    private Map index = new HashMap<String, MemoryLocation>();

    @Override
    public void store(String key, Serializable serializable) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(serializable);
            byte[] objectAsByteArray = baos.toByteArray();
            byteBuffer.position(endOfBuffer);
            byteBuffer.put(objectAsByteArray);
            index.put(key, new MemoryLocation(objectAsByteArray.length, endOfBuffer));
            endOfBuffer = byteBuffer.position();
        }
    }

    @Override
    public <T> T retrieve(String key) throws IOException, ClassNotFoundException {
        MemoryLocation ml = (MemoryLocation)index.get(key);
        byte[] returnBytes = new byte[ml.size];
        byteBuffer.position(ml.offset);
        byteBuffer.get(returnBytes);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(returnBytes))){
            return (T) ois.readObject();
        }
    }

}
