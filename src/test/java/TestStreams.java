import java.util.ArrayList;
import java.util.List;

public class TestStreams implements Runnable {
    public static void main(String[] args) {
        TestStreams test = new TestStreams();
        test.run();
        test.showNameWithM();
        test.showAll();
        test.showOverSeven();
    }

    List<String> listOfPidorasi = new ArrayList();

    @Override
    public void run() {

        listOfPidorasi.add("Kitya Vislyi");
        listOfPidorasi.add("Dementiy");
        listOfPidorasi.add("Yusha");
        listOfPidorasi.add("Momo");
        listOfPidorasi.add("Stavros");


    }

    public void showNameWithM() {
        System.out.println("======ShowNameWithM started work======");
        listOfPidorasi.stream()
                .filter(name -> name.toLowerCase().contains("m"))
                .forEach(System.out::println);
        System.out.println("======ShowNameWithM ended work======");
    }

    public void showAll() {
        System.out.println("======ShowAll started work======");
        listOfPidorasi.stream()
                .forEach(System.out::println);
        System.out.println("======ShowAll ended work======");
    }

    public void showOverSeven() {
        System.out.println("======ShowOverSeven started work======");
        listOfPidorasi.stream()
                .filter(name -> name.toLowerCase().contains("m"))
                .filter(name -> name.length() > 7)
                .forEach(System.out::println);
        System.out.println("======ShowOverSeven ended work======");
    }
}
