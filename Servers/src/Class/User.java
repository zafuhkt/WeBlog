package Class;

public class User {
    private int id;
    private String name;
    private String password;
    private int posts;
    private int followers;
    private int followings;
    private String pic;

    public User(int id,String name,String password,int posts,int followers,int followings,String pic){
        this.id=id;
        this.name=name;
        this.password=password;
        this.posts=posts;
        this.followers=followers;
        this.followings=followings;
        this.pic=pic;
    }
    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                ", followers=" + followers +
                ", followings=" + followings +
                ", pic=" + pic +
                '}';
    }
}
