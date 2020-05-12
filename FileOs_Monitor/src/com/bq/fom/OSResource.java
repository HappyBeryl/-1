package com.bq.fom;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/*
 获取系统资源
 */
public class OSResource {
    //获取系统资源jdk提供的接口
    private static OperatingSystemMXBean mxBean =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    //数组长度
    private static final int DATA_LENGTH = 60;

    //最前面的坐标
    private static int firstIndex = DATA_LENGTH;

    //记录坐标的数组
    private static XYPair[] xyPairs = new XYPair[DATA_LENGTH];

    //初始化数组
    static {
        for (int i = 0; i < xyPairs.length; i++) {
            xyPairs[i] = new XYPair(0,0);
        }
    }


    public static class XYPair
    {
        private double x; //时间
        private double y; //cpu占有率

        private XYPair(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

    }


    //获取CPU占有率
    public static XYPair[] getCPUPercetage() {
        double cpu =  mxBean.getSystemCpuLoad();
        moveCPUData(cpu);
        return xyPairs;
    }

    //获取操作系统版本
    public static String getOSName() {
        return mxBean.getName();
    }

    //获取cpu核数
    public static String geCPUCore() {
        int num = mxBean.getAvailableProcessors();
        String str = Integer.toString(num);
        return str;
    }

    //获取cpu架构
    public static String geCPUArch() {
        return mxBean.getArch();
    }

    //移动数据
    private static void moveCPUData(double cpuPercetage){
        int movIdx = -1; //移动的坐标
        if (firstIndex == 0){
            movIdx = firstIndex + 1;
        }else
            {
                movIdx = firstIndex; firstIndex--;
            }
            for (; movIdx < xyPairs.length; ++movIdx){
                xyPairs[movIdx-1].setX(xyPairs[movIdx].getX()-1);
                xyPairs[movIdx-1].setY(xyPairs[movIdx].getY());
        }
        movIdx--;
        xyPairs[movIdx] = new XYPair(movIdx, cpuPercetage);
    }


}
