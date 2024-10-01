package com.sample.javafx_demo;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


class Item
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleStringProperty dateofarrival;
    private final SimpleFloatProperty price;

    public Item(String id, String name, int quantity, String dateofarrival, float price)
    {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price=new SimpleFloatProperty(price);
        this.dateofarrival=new SimpleStringProperty(dateofarrival);
    }

    public SimpleStringProperty idProperty()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id.set(id);
    }
    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }
    public SimpleIntegerProperty quantityProperty()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity.set(quantity);
    }
    public SimpleFloatProperty priceProperty()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price.set(price);
    }
    public SimpleStringProperty getDateofarrival()
    {
        return dateofarrival;
    }

    public void setDateofarrival(String dateofarrival)
    {
        this.dateofarrival.set(dateofarrival);
    }
}

public class InventoryManagement extends Application
{
    private TableView<Item> table;
    private ObservableList<Item> itemList;
    private TextField idField, nameField, quantityField, priceField, DateField;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        table = new TableView<>();
        itemList = FXCollections.observableArrayList();
        table.setItems(itemList);

        TableColumn<Item, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setPrefWidth(130);
        TableColumn<Item, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        TableColumn<Item, String> dateColumn = new TableColumn<>("Date of Arrival");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateofarrival());
        dateColumn.setPrefWidth(150);
        TableColumn<Item, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        table.getColumns().addAll(idColumn, nameColumn,quantityColumn, priceColumn,dateColumn);
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(8);
        form.setHgap(10);

        idField = new TextField();
        nameField = new TextField();
        quantityField = new TextField();
        priceField=new TextField();
        DateField=new TextField();

        form.add(new Label("ID:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Quantity:"), 0, 2);
        form.add(quantityField, 1, 2);
        form.add(new Label("Price:"), 0, 3);
        form.add(priceField, 1, 3);
        form.add(new Label("Date of Arrival:"), 0, 4);
        form.add(DateField, 1, 4);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> addItem());
        updateButton.setOnAction(e -> updateItem());
        deleteButton.setOnAction(e -> deleteItem());

        VBox vbox = new VBox(10, form, addButton, updateButton, deleteButton, table);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addItem()
    {
        String id = idField.getText();
        String name = nameField.getText();
        String dateofarrival=DateField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Quantity must be a valid number.");
            return;
        }
        float price;
        try {
            price = Float.parseFloat(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Price must be a valid float literal.");
            return;
        }

        itemList.add(new Item(id, name, quantity,dateofarrival,price));
        clearFields();
    }

    private void updateItem()
    {
        Item selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String id = idField.getText();
            String name = nameField.getText();
            String dateofarrival=DateField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                showAlert("Quantity must be a valid number.");
                return;
            }
            float price;
            try {
                price = Integer.parseInt(priceField.getText());
            } catch (NumberFormatException e) {
                showAlert("Price must be a valid float literal.");
                return;
            }

            selectedItem.setId(id);
            selectedItem.setName(name);
            selectedItem.setQuantity(quantity);
            selectedItem.setPrice(price);
            selectedItem.setDateofarrival(dateofarrival);
            table.refresh();
            clearFields();
        }
        else {
            showAlert("Please select an item to update.");
        }
    }

    private void deleteItem()
    {
        Item selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemList.remove(selectedItem);
            clearFields();
        }
        else {
            showAlert("Please select an item to delete.");
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        quantityField.clear();
        priceField.clear();
        DateField.clear();
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}