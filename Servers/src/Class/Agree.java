package Class;

public class Agree {
    int id;
    int user_id;
    String name;
    String text;
    String head;  //用户头像

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Agree(int id, int user_id, String name, String text, String head) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.text = text;
        this.head = head;
    }
}
