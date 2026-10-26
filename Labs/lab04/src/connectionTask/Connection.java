package connectionTask;

public class Connection {

    private int id;

    Connection(int id) {
        this.id = id;
    }

    public void executeQuery(String query){
        System.out.println("Connection #" + id + " executing: " + query);
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return "Connection{" + "id=" + id + '}';
    }

}
