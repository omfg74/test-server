public class ServerThread extends Thread {
    public void run(){
    Server server = new Server();
            server.accept();
}
}
