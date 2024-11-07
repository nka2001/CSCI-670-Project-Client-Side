package csci610.dataclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;

public class SecondaryController {

    @FXML
    private PieChart PalletSpaceChart;
    @FXML
    private PieChart SKUByDept;
    @FXML
    private ListView<?> ActiveUsers;

    private BufferedReader in;
    private PrintWriter out;

    private String receivedCode;

    private volatile boolean listening = true;

    @FXML
    public void initialize() {

        ConnectionManager.getInstance();
        in = ConnectionManager.getInstance().getInputStream();
        out = ConnectionManager.getInstance().getOutputStream();

        loadPalletPie();

    }

    private void loadPalletPie() {
        out.println("LOADPALLETS");

        int truecount;
        int falsecount;

        try {
            String fromServer = in.readLine();

            ArrayList<Integer> arr = new ArrayList<>();

            arr.add(fromServer.indexOf(1));
            arr.add(fromServer.indexOf(4));
            
            truecount = arr.get(0);//get the true count, position 0
            falsecount = arr.get(1);//get the false count, position 1

            ObservableList<PieChart.Data> pcd = FXCollections.observableArrayList(
            new PieChart.Data("Spaces Used", truecount),
            new PieChart.Data("Spaces Free", falsecount)
            );
            
            PalletSpaceChart.setData(pcd);

            

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void loadSKUPie() {

    }

    private void loadActiveUsers() {

    }

}
