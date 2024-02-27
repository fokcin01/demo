package com.example.demo;

import client.to.ResourceTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class Controller {
    JPanel main = new JPanel();

    public JPanel getMain() {
        return main;
    }

    public void initialize() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/resources/all")).GET().build();
        HttpResponse resp = HttpClient.newHttpClient().send(req,HttpResponse.BodyHandlers.ofString());
        System.out.println("body: " + resp.body());
        TypeReference<List<ResourceTO>> typeReference = new TypeReference<>() {
        };
        ObjectMapper mapper = new ObjectMapper();
        List<ResourceTO> list = mapper.readValue(resp.body().toString(), typeReference);
        System.out.println("result: " + list);
        initTable();
    }

    Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                    "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                    "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                    "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                    "Pool", new Integer(10), new Boolean(false)}
    };
    String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

    public void initTable() {
        JTable table = new JTable(data, columnNames);
//        TableColumn column1 = new TableColumn();
//        TableColumn column2 = new TableColumn();
//        table.addColumn(column1);
//        table.addColumn(column2);
        main.add(table);
    }

    public static void main(String[] args) throws IOException, InterruptedException {



    }
}