package com.bq.gui;
import com.bq.fom.FileScanner;
import com.bq.fom.FileTreeNode;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;


import com.bq.fom.OSResource;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
 控制模块：把UI和逻辑关联起来
 */
public class FileAndOSMonitorController {
    //控件id作为controller的属性，向控件里添加程序
    @FXML private LineChart cpuChart;
    @FXML private TreeTableView<FileTreeNode> fileStat;
    @FXML private Text osType;
    @FXML private Text cpuArch;
    @FXML private Text cpuCore;

    private final Image image = new Image(getClass().getClassLoader().
            getResourceAsStream("Folder.png"));

    //禁止目录展示要传入的参数
    private Stage primaryStage = null;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Timer timer = new Timer();

    private TimerTask timerTask = null;

    /*
    响应CPU占有率 按钮的方法
     */
    public void handleCPUSelectionChanged(Event event) {

        Tab tab = (Tab) event.getTarget();
        //选中的时候绘制曲线
        if (tab.isSelected()) {
            //创建定时器任务
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    OSResource.XYPair[] xyPairs = OSResource.getCPUPercetage();

                    XYChart.Series series = new XYChart.Series();

                    //数据到XYChart的转换
                    for (OSResource.XYPair xyPair : xyPairs) {
                        //把自己的坐标点转化为XYChart.Data这个类型
                        XYChart.Data data = new XYChart.Data(xyPair.getX(), xyPair.getY());
                        series.getData().add(data);
                    }

                    //gui程序 要渲染 填充数据必须在主线程中执行
                    // 把数据切换到主线程执行
                    Platform.runLater(
                            () -> {
                                //清空历史数据
                                if (cpuChart.getData().size() > 0) {
                                    cpuChart.getData().remove(0); //从0开始把所有东西移除
                                }
                                //数据加入到cpuChart
                                cpuChart.getData().add(series);

                                osType.setText(OSResource.getOSName());

                                cpuArch.setText(OSResource.geCPUArch());

                                cpuCore.setText(OSResource.geCPUCore());
                            }
                    );
                }
            };
            //第二个参数表示任务安排好以后立即执行一次，第三个参数表示周期执行时间 ms
            timer.schedule(timerTask, 0, 1000);
        } else {
            //没被选中 取消掉定时器
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
        }
    }

    public void shutdown() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /*
    响应磁盘空间扫描的方法
     */
    public void handleSelectFile(ActionEvent actionEvent) {

        //触发事件 重新选择的时候先把数据清掉
        fileStat.setRoot(null);

        //选择磁盘目录
        DirectoryChooser directoryChooser = new DirectoryChooser();

        //弹出一个对话框 进行选择目录 File对象表示我们选择的目录或文件
        File file = directoryChooser.showDialog(primaryStage);


        //目录比较大时，递归比较费时间
        // 文件扫描会比较慢
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //渲染文件统计
                FileTreeNode rootNode = new FileTreeNode();
                rootNode.setFile(file);
                rootNode.setFileName(file.getName());

                //扫描目录
                FileScanner.scannerDirectory(rootNode);

                //渲染
                //把rootNode转化为TreeItem 根节点 保证根节点有图标
                TreeItem rootItem = new TreeItem(rootNode, new ImageView(image));

                //设置树容许展开
                rootItem.setExpanded(true);

                //子节点转换
                fillTreeItem(rootNode, rootItem);

                //转换到主线程执行
                Platform.runLater(
                        ()-> {
                            fileStat.setRoot(rootItem);
                        }
                );
            }
        });
        //线程执行完自动释放资源
        thread.setDaemon(true);
        thread.start();
    }

    //子目录转换
    private void fillTreeItem(FileTreeNode rootNode, TreeItem rootItem) {
        //获取子节点
        List<FileTreeNode> childs = rootNode.getChildrens();
        //遍历子节点
        for (FileTreeNode node : childs) {
            TreeItem item = new TreeItem(node);

            //保证子目录有图标
           if (node.getChildrens().size() > 0){
               item.setGraphic(new ImageView(image));
           }

            rootItem.getChildren().add(item);

            // 递归调用，转换子目录
            fillTreeItem(node, item);
        }
    }
}
