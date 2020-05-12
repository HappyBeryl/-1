package com.bq.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
  主程序：继承Application类 重写start()方法
 */
public class FileAndOSMonitorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Stage:舞台 Scene:场景
        //1. 加载.fxml文件
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                getResource("FOMonitor_tab.fxml"));

        //2. 真正的加载
        Parent root = loader.load();

        //加载磁盘空间统计 把统计页面加载到主舞台
        FileAndOSMonitorController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        //3. 创建Scene对象 即场景
        Scene scene = new Scene(root, 800, 600);

        //4. 给stage设置标题
        primaryStage.setTitle("FileAndOSMonitor");

        //5. 将scene添加到stage
        primaryStage.setScene(scene);

        //关闭时停止程序
        primaryStage.setOnCloseRequest(event -> controller.shutdown());

        //6. 展示
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
