package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Controller {
    @FXML
    private TableView<ResourceTO> tableView;
    @FXML
    private TableColumn<ResourceTO, Integer> idColumn;
    @FXML
    private TableColumn<ResourceTO, String> nameColumn;
    @FXML
    private TableColumn<ResourceTO, Integer> priceColumn;
    ResourceTO firstResource = new ResourceTO(1, "chukcha", 25);
    ResourceTO secondResource = new ResourceTO(2, "churka", 35);

    public void initialize() {
        ObservableList<ResourceTO> pohui = FXCollections.observableArrayList(firstResource, secondResource);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(pohui);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/resources/all")).GET().build();
        HttpResponse resp = HttpClient.newHttpClient().send(req,HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.body());
    }
}