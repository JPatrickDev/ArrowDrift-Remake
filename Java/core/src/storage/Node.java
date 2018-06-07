package storage;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Jack on 30/04/2018.
 */
public class Node {
    private String name;
    private HashMap<String, String> data = new HashMap<String, String>();
    private ArrayList<Node> children = new ArrayList<Node>();

    public Node(String name) {
        this.name = name;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public void addValue(String key, String value) {
        this.data.put(key, value);
    }

    public void addTexture(String key,Texture texture){
        texture.getTextureData().prepare();
        Pixmap map = texture.getTextureData().consumePixmap();
        String value = texture.getWidth() + ":" + texture.getHeight() + ":" +  Pixmap.Format.toGdx2DPixmapFormat(texture.getTextureData().getFormat());

        for(int x = 0; x != texture.getWidth(); x++){
            for(int y = 0; y != texture.getHeight();y++){
                int i = map.getPixel(x,y);
                value += ":" + i;
            }
        }
        System.out.println(value);
        addValue(key,value);
    }

    public Texture getTexture(String key){
        String data = getValue(key);
        String[] info = data.split(":");
        int width = Integer.parseInt(info[0]);
        int height = Integer.parseInt(info[1]);
        Pixmap.Format format = Pixmap.Format.fromGdx2DPixmapFormat(Integer.parseInt(info[2]));
        Pixmap map = new Pixmap(width,height,format);
        for(int x = 0; x != width; x++){
            for(int y = 0; y != height;y++){
                int i = x + y * width;
                i += 3;
                int color = Integer.parseInt(info[i]);
                map.setColor(color);
                map.fillRectangle(x,y,1,1);
            }
        }
        return new Texture(map);
    }

    public String getValue(String key) {
        return this.data.get(key);
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public Node getChild(String metadata) {
        for (Node n : children) {
            if (n.name.equals(metadata)) {
                return n;
            }
        }
        return null;
    }

    public boolean hasChild(String name) {
        for (Node n : children) {
            if (n.name.equals(name))
                return true;
        }
        return false;
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    public void toZip(ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(name + "");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream dataoutput = getNewZipEntry("data", baos);
        for (String key : data.keySet()) {
            String value = data.get(key);
            String s = key + ":" + value + "\n";
            dataoutput.write(s.getBytes());
        }
        dataoutput.closeEntry();
        for (Node n : children) {
            n.toZip(dataoutput);
        }
        out.putNextEntry(entry);
        out.write(baos.toByteArray());
        out.closeEntry();
    }

    public static Node fromZip(ZipInputStream in) throws IOException {
        ZipEntry e = in.getNextEntry();
        return new Node(e.getName());
    }

    private ZipOutputStream getNewZipEntry(String name, ByteArrayOutputStream baos) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(name);
        zos.putNextEntry(entry);
        return zos;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", data=" + data +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void removeChild(String name) {
        Iterator<Node> nodeIterator = children.iterator();
        while(nodeIterator.hasNext()){
            Node next = nodeIterator.next();
            if(next.getName().equals(name)){
                nodeIterator.remove();
                return;
            }
        }
    }
}