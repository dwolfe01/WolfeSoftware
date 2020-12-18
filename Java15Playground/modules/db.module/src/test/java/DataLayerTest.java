import database.NonHeapDataStructure;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DataLayerTest {

    NonHeapDataStructure inMemoryDB = new NonHeapDataStructure();

    @Test
    public void shouldStoreStringPolymorphism() throws IOException, ClassNotFoundException {
        String value = "Value";
        inMemoryDB.store("Key", value);
        assertEquals("Value", inMemoryDB.retrieve("Key"));
    }

    @Test
    public void shouldStoreDoubleAutoboxing() throws IOException, ClassNotFoundException {
        Double d = 5.1;
        inMemoryDB.store("Key", 5.1);
        Double value = inMemoryDB.retrieve("Key");
        assertEquals(d, value);
        assertEquals(true, value instanceof Double);
    }

    @Test
    public void shouldStoreArbitraryObject() throws IOException, ClassNotFoundException {
        String value = "This is class C";
        inMemoryDB.store("Key", new ArbitrarySerializableObject(value));
        assertEquals(value, inMemoryDB.<ArbitrarySerializableObject>retrieve("Key").getString());
    }

    @Test
    public void shouldStoreTwoArbitraryObjects() throws IOException, ClassNotFoundException {
        inMemoryDB.store("Key", new ArbitrarySerializableObject("This is a class"));
        inMemoryDB.store("Key2", new String("This is also a class"));
        assertEquals("This is a class", inMemoryDB.<ArbitrarySerializableObject>retrieve("Key").getString());
        assertEquals("This is also a class", inMemoryDB.<String>retrieve("Key2").toString());
    }

    //should allow for multithreading
    //update existing key
    //remove key
    //should maintain information about the object itself - more generics?

}
