package Class;

public class Comment {
    int id;
    int user_id;
    String name;
    String time;
    String place;
    String text;
    String head;  //用户头像
    public Comment(){

    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Comment(int id, int user_id, String name, String time, String place, String text, String head) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.time = time;
        this.place = place;
        this.text = text;
        this.head = head;
    }
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", text='" + text + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
