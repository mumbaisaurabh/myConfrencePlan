import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MyConfrencePlan {
    private static List<String> inputCombinedList = new ArrayList<>();
    private static List<String> processedList = new ArrayList<>();
    private static int trackNumber = 0;

    public static void main(String[] args) throws Exception {

        toReadTheInput();

        toProcessData();

        toWriteTheOutput();

    }

    private static void toProcessData() {

        beforeLunchProcess(inputCombinedList);

    }

    private static void beforeLunchProcess(List<String> inputCombinedList) {

        if (inputCombinedList.isEmpty()) {
            return;
        }

        LocalTime time = LocalTime.of(9, 0);

        processedList.add("Track " + ++trackNumber + ":");
        int remainingTime = 180;

        List<String> tempList = new ArrayList<>(inputCombinedList);

        for (String s : inputCombinedList) {
            if (remainingTime > 0) {
                int progDura = Integer.valueOf(s.split("_")[0]);
                String progData = s.split("_")[1];
                if (progDura <= remainingTime) {

                    processedList.add(time + "AM " + progData);
                    tempList.remove(s);
                    remainingTime -= progDura;
                    time = time.plusMinutes(progDura);
                }
            } else {
                break;
            }
        }

        if (remainingTime > 0) {
            processedList.add(time + "AM " + "Networking Event.");
        }

        if (!tempList.isEmpty()) {
            afterLunchProcess(tempList);
        }

    }

    private static void afterLunchProcess(List<String> inputCombinedList) {

        int remainingTime = 240;
        processedList.add("12:00PM Lunch");
        LocalTime time = LocalTime.of(1, 0);
        List<String> tempList = new ArrayList<>(inputCombinedList);

        for (String s : inputCombinedList) {
            if (remainingTime > 0) {

                int progDura = Integer.valueOf(s.split("_")[0]);
                String progData = s.split("_")[1];
                if (progDura <= remainingTime) {
                    processedList.add(time + "PM " + progData);
                    tempList.remove(s);
                    remainingTime -= progDura;
                    time = time.plusMinutes(progDura);
                }
            } else {
                break;
            }
        }

        if (remainingTime > 0) {
            processedList.add(time + "PM " + "Networking Event.");

        }

        if (!inputCombinedList.isEmpty()) {
            beforeLunchProcess(tempList);
        }

    }


    private static void toReadTheInput() throws IOException {
        File file = new File("C:\\Users\\saurabhtiw\\Desktop\\clincaseTest\\myInputFile.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        int totalProgramme = 0;
        int i = 0;
        while ((st = br.readLine()) != null) {
            if (i == 0) {
                totalProgramme = Integer.valueOf(st);
                i++;
            } else {

                String[] items = st.split(" ");
                String dur = items[items.length - 1].toLowerCase();
                String ss = dur.contains("min") ? dur.replace("min", "") : "5";
                inputCombinedList.add(ss + "_" + st);

                System.out.println("Last element : " + ss);

            }
        }

        System.out.println("Before List : " + inputCombinedList);

        inputCombinedList.sort((o1, o2) -> Integer.valueOf(o2.split("_")[0]) - Integer.valueOf(o1.split("_")[0]));

        System.out.println("After List : " + inputCombinedList);
    }

    private static void toWriteTheOutput() {
        try (FileWriter writer = new FileWriter("C:\\Users\\saurabhtiw\\Desktop\\clincaseTest\\outputFile.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (String ss : processedList)
                bw.write(ss + "\n");

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

}
