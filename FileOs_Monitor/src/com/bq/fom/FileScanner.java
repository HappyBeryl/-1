package com.bq.fom;

import java.io.File;

/*
统计扫描
 */
public class FileScanner {
    public static void scannerDirectory(FileTreeNode node) {
        //获取当前目录或文件列表
        File[] files = node.getFile().listFiles();
        if (files == null) {
            return;
        }
        //遍历子目录或者文件
        for (File file : files) {
            FileTreeNode child = new FileTreeNode();
            child.setFile(file);
            child.setFileName(file.getName());

            if (file.isDirectory()) {
                //如果是目录继续统计
                scannerDirectory(child);
            } else {
                //如果是普通文件 记录文件大小
                child.setTotalLength(file.length());
            }
            node.setTotalLength(node.getTotalLength() + child.getTotalLength());
            node.addChildNode(child);
        }
    }
}
