package storage;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Jack on 30/04/2018.
 */
public class StorageSystem {


    private Node root;
    public static final int version = 1;

    public StorageSystem(String name) {
        root = new Node(name);
        //Setup the metadata for the storage system
        Node meta = new Node("metadata");
        meta.addValue("version", "" + version);
        meta.addValue("createdAt","" + System.currentTimeMillis());
        root.addChild(meta);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node n) {
        this.root = n;
    }

    public Node getMetaData() {
        return root.getChild("metadata");
    }

    public void save(String file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ZipOutputStream zos = new ZipOutputStream(baos);
            root.toZip(zos);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            baos.flush();
            baos.close();
        }
        try {
            OutputStream outputStream = Gdx.files.external(file).write(false);
            baos.writeTo(outputStream);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        StorageSystem system = new StorageSystem("userdata");

        Node settings = new Node("settings");
        settings.addValue("sound", "true");

        Node levels = new Node("levels");
        Node levelOne = new Node("1");
        levelOne.addValue("score", "2");
        levels.addChild(levelOne);
        Node levelTwo = new Node("2");
        levelOne.addValue("score", "3");
        levels.addChild(levelTwo);
        system.getRoot().addChild(levels);
        system.getRoot().addChild(settings);

        system.save("test.zip");


        StorageSystem s = StorageSystem.fromFile("test.zip");

    }


    public static StorageSystem fromFile(String file) throws IOException {
        InputStream stream = null;
        try {
            stream = Gdx.files.external(file).read();
            Node node = loadFromfile(true,stream, null);
            if (node != null) {
                StorageSystem system = new StorageSystem(node.getName());
                system.setRoot(node);
                return system;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null)
                stream.close();
        }
        return null;
    }

    private static Node loadFromfile(boolean top,InputStream fileInputStream, Node currentNode) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        String entryName = "";
        ZipEntry entry = zipInputStream.getNextEntry();
        if (currentNode == null) {
            currentNode = new Node(entry.getName());
        }
        while (entry != null) {
            entryName = entry.getName();
            System.out.println(entryName);
            if (!entryName.equals("data")) {
                Node n = new Node(entryName);
                currentNode.addChild(n);
                loadFromfile(false,zipInputStream, n);
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    String[] split = line.split(":");
                    String key = split[0];
                    String value = line.replaceFirst(key + ":", "");
                    currentNode.addValue(key, value);
                }
            }
            entry = zipInputStream.getNextEntry();
        }
        if(top){
            fileInputStream.close();
        }
        if (currentNode.getChildren().isEmpty())
            return currentNode;
        return currentNode.getChildren().get(0);
    }
}
