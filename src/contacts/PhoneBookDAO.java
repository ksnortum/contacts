package contacts;

import java.io.*;

public class PhoneBookDAO {
    public static void serialize(Object obj, String fileName) {
        try (
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(obj);
        } catch (FileNotFoundException e) {
            System.err.println("File not found writing object");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception writing object");
            e.printStackTrace();
        }
    }

    public static Object deserialize(String fileName) {
        Object obj = null;

        try (
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis)
        ) {
            obj = ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("File not found reading object");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception reading object");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found reading object");
            e.printStackTrace();
        }

        return obj;
    }
}
