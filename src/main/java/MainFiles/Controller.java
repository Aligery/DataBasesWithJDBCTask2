package MainFiles;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Controller {
    private ObservableList<User> usersData = FXCollections.observableArrayList(); // объявляем коллекцию, которую будем заполнять
    @FXML
    private TextField IDforDelete;
    @FXML
    private TextField FieldForURLServer;
    @FXML
    private TextField NameUser;
    @FXML
    private TextField SNameUser;
    @FXML
    private TextField TNameUser;
    @FXML
    private DatePicker DateOfBirthUser;
    @FXML
    private TextField GroupOfUser;
    @FXML
    private Button ButtonForAddingStudent;
    @FXML
    private Button DeleteID;
    @FXML
    private TextField InfoForUser;
    @FXML
    private TableView<User> GlobalTableViewID; //TableColumn <S, T> (????)
    @FXML
    private TableColumn<User, Integer> IDStudentColumn;
    @FXML
    private TableColumn<User, String> NameColumnStudent;
    @FXML
    private TableColumn<User, String> SNameColumnFamily;
    @FXML
    private TableColumn<User, String> TNameColumnFamily;
    @FXML
    private TableColumn<User, String> DateOfBirthColumn;
    @FXML
    private TableColumn<User, String> GroupColumn;

    private static final String URL = "jdbc:mysql://localhost:3306/ilya_database";
    private static final String ParamForURL="?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String Username = "root";
    private static final String Password = "ROOT";
    @FXML
    private void initialize() //Инициализация окна при запуске
    {
        // устанавливаем тип и значение которое должно хранится в колонке
        IDStudentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumnStudent.setCellValueFactory(new PropertyValueFactory<>("fname"));
        SNameColumnFamily.setCellValueFactory(new PropertyValueFactory<>("lname"));
        TNameColumnFamily.setCellValueFactory(new PropertyValueFactory<>("tname"));
        DateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        GroupColumn.setCellValueFactory(new PropertyValueFactory<>("StudyGroup"));
        //
        FieldForURLServer.setText(URL);
        FieldForURLServer.setEditable(false);
        UpdateTable();
    }
    public void AddStudent(ActionEvent actionEvent) //Добавляем студента
    {
        if (NameUser.getText().isEmpty() && SNameUser.getText().isEmpty() && TNameUser.getText().isEmpty() && GroupOfUser.getText().isEmpty() && DateOfBirthUser.getPromptText().isEmpty()) //проверяем чтоб все поля были заполнены
        {
        InfoForUser.setText("Заполните все поля.");
        }
        else {
            Connection connection;
            try {
                connection = DriverManager.getConnection(URL + ParamForURL, Username, Password); //устанавливаем соединение с БД
                if (!connection.isClosed()) {
                    System.out.println("Соединение с БД Установлено!");
                    InfoForUser.setText("Соединение с БД установлено, обновляем таблицу.");
                    Statement statement = connection.createStatement(); // делаем Statement для общения с БД
                    statement.execute("insert into ilya_database.users (fname, lname, tname, StudyGroup, DateOfBirth) values ('" + //Добавляем нового человечка в БД, уникальный айди, присуждается самой БД
                            NameUser.getText()+"', '"+
                            SNameUser.getText()+ "', '"+
                            TNameUser.getText()+ "', '"+
                            GroupOfUser.getText()+"', '"+
                            DateOfBirthUser.getValue().getYear() + "-" + DateOfBirthUser.getValue().getMonthValue() + "-" + DateOfBirthUser.getValue().getDayOfMonth() + "');");
                }
                connection.close();
                if (connection.isClosed()) {
                    InfoForUser.setText("Соединение с БД закрыто!");
                    System.out.println("Соединение с БД закрыто!");
                }
            } catch (SQLException e) {
                InfoForUser.setText("Что-то пошло не так");
                e.printStackTrace();
            }

        }

        UpdateTable();
    }
    public void ActionDeleteCurrentID(ActionEvent actionEvent)  //Удаляем студента по заданному ID
    {
        System.out.println("Button pressed");
        if (IDforDelete.getText().isEmpty()) //проверяем поле на заполненность
        {
            System.out.println("ID");
            InfoForUser.setText("Wrong ID");
        }
        else
        {
            try { //конструкция TryCatch для перевода контента в строке, в инт
                int UserID = Integer.parseInt(IDforDelete.getText());
                System.out.println(UserID);
                InfoForUser.setText("UserID: "+UserID);
                //Если все хорошо, устанавливаем соединение
                Connection connection;
                try {
                    connection = DriverManager.getConnection(URL + ParamForURL, Username, Password); //устанавливаем соединение с БД
                    if (!connection.isClosed()) {
                        System.out.println("Соединение с БД Установлено!");
                        InfoForUser.setText("Соединение с БД установлено, обновляем таблицу.");
                        Statement statement = connection.createStatement(); // делаем Statement для общения с БД
                        statement.execute("delete from users where id="+UserID+";");
                    }
                    connection.close();
                    if (connection.isClosed()) {
                        InfoForUser.setText("Соединение с БД закрыто!");
                        System.out.println("Соединение с БД закрыто!");
                    }
                } catch (SQLException e) {
                    InfoForUser.setText("Что-то пошло не так");
                    e.printStackTrace();
                }
            }
            //
            catch (NumberFormatException e) //Говорим что пользователь нехороший человек, в случае ошибки
            {
                System.out.println("WrongID");
                InfoForUser.setText("WrongID");
            }
        }
        UpdateTable();
    }
    public void UpdateTable() //Метод для обновления содержимого таблица, для визуального отображения пользователю
    {
        usersData.clear(); //Очищаем старую коллекцию, для выгрузки новой, после изменений
        Connection connection;
        String query = "select * from users;";
        try {
            connection = DriverManager.getConnection(URL + ParamForURL, Username, Password); //устанавливаем соединение с БД
            if (!connection.isClosed()) {
                //Тело программы
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    usersData.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6))); //передаем в конструктор класса, значения, пока не закончим всю таблицу
                    GlobalTableViewID.setItems(usersData);//даем заполненную коллекцию
                }

            }
            connection.close();
            } catch (SQLException e) {
            InfoForUser.setText("Что-то пошло не так");
            e.printStackTrace();
        }
    }
}