import java.util.ArrayList;
import java.util.List;

interface DocumentElement {
    String render();
}

class TextElement implements DocumentElement {

    private final String text;

    public TextElement(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text;
    }
}

class ImageElement implements DocumentElement {

    private final String imagePath;

    public ImageElement(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String render() {
        return "[IMAGE : " + imagePath + "]";
    }
}

class Document {

    private final String title;
    private final List<DocumentElement> elements;

    public Document(String title) {
        this.title = title;
        this.elements = new ArrayList<>();
    }

    public void addElement(DocumentElement element) {
        elements.add(element);
    }

    public void addElementAt(int index, DocumentElement element) {
        elements.add(index, element);
    }

    public void removeElement(int index) {
        elements.remove(index);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {

        StringBuilder content = new StringBuilder();

        for (DocumentElement element : elements) {
            content.append(element.render())
                    .append("\n");
        }

        return content.toString();
    }
}

interface DocumentStorage {
    void save(Document document);
}

class FileStorage implements DocumentStorage {

    @Override
    public void save(Document document) {

        System.out.println("Saving document to file...");
        System.out.println(document.getContent());
    }
}

class DatabaseStorage implements DocumentStorage {

    @Override
    public void save(Document document) {

        System.out.println("Saving document to database...");
        System.out.println(document.getContent());
    }
}

class DocumentEditor {

    private final Document document;

    public DocumentEditor(Document document) {
        this.document = document;
    }

    public void addText(String text) {
        document.addElement(new TextElement(text));
    }

    public void addImage(String imagePath) {
        document.addElement(new ImageElement(imagePath));
    }

    public void insertTextAt(int index, String text) {
        document.addElementAt(index, new TextElement(text));
    }

    public void insertImageAt(int index, String imagePath) {
        document.addElementAt(index, new ImageElement(imagePath));
    }

    public void removeContent(int index) {
        document.removeElement(index);
    }
}

class SaveService {

    private final DocumentStorage storage;

    public SaveService(DocumentStorage storage) {
        this.storage = storage;
    }

    public void save(Document document) {
        storage.save(document);
    }
}

public class code {

    public static void main(String[] args) {

        Document document = new Document("Google Docs");

        DocumentEditor editor = new DocumentEditor(document);

        editor.addText("Introduction to System Design");

        editor.addImage("architecture.png");

        editor.insertTextAt(
                1,
                "This content was inserted in between."
        );

        editor.addText("SOLID Principles");

        editor.removeContent(2);

        SaveService fileSaver =
                new SaveService(new FileStorage());

        SaveService dbSaver =
                new SaveService(new DatabaseStorage());

        fileSaver.save(document);

        System.out.println();

        dbSaver.save(document);
    }
}