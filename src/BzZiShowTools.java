import com.nlf.calendar.EightChar;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;
import com.nlf.calendar.eightchar.DaYun;
import com.nlf.calendar.eightchar.LiuNian;
import com.nlf.calendar.eightchar.Yun;
import java.time.Year;
import java.util.Date;


/**
 * ClassName: BzZiShowTools
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author tiengming
 * @Creatr 2023/8/2 21:44
 * @Version 1.0
 */
public class BzZiShowTools {
    public static void main(String[] args) {
        CustomerList customers = new CustomerList(50);
        System.out.println(customers.getCustomer(1));
        boolean loopFlag = true;
        do {
            System.out.println("---------------------八字排盘系统--------------------");
            nowTimes();
            System.out.println("                   1 八 字 排 盘");
            System.out.println("                   2 修 改 记 录");
            System.out.println("                   3 删 除 记 录");
            System.out.println("                   4 排 盘 记 录");
            System.out.println("                   5 退      出");
            System.out.println("---------------------------------------------------");
            System.out.print("                     请选择(1-5)：");

            char key = CMUtility.readMenuSelection();
            System.out.println();
            switch (key) {
                case '1':
                    baZiShow(customers);
                    break;
                case '2':
                    modifyRecords(customers);
                    break;
                case '3':
                    deleteRecords(customers);
                    break;
                case '4':
                    listAllRecords(customers);
                    break;
                case '5':
                    System.out.print("确认是否退出(Y/N)：");
                    char yn = CMUtility.readConfirmSelection();
                    if (yn == 'Y')
                        loopFlag = false;
                    break;
            }
        } while (loopFlag);
    }

    private static void nowTimes() {
        Solar setdate = Solar.fromDate(new Date());
        Lunar date = Lunar.fromDate(new Date());
        EightChar nowdate = new EightChar(date);
        System.out.println("\t\t\t\t\t   现在时间");
        System.out.println("\t\t\t\t" + setdate.toYmdHms());
        String lunarshow = "\t\t\t" + date.getYearInChinese() + "年\t" + date.getMonthInChinese() + "月\t\t" + date.getDayInChinese() + "日\t" + date.getTimeZhi() + "时";
        System.out.println(lunarshow);
        String ganzhishow = "\t\t\t\t" + nowdate.getYear() + "\t\t" + nowdate.getMonth() + "\t\t" + nowdate.getDay() + "\t\t" + nowdate.getTime();
        String xunkong = "\t\t旬空：" + nowdate.getYearXunKong() + "(年)\t" + nowdate.getMonthXunKong() + "(月)\t" + nowdate.getDayXunKong() + "(日)\t" + nowdate.getTimeXunKong() + "(时)";
        System.out.println(ganzhishow);
        System.out.println(xunkong);
        System.out.println("---------------------------------------------------");

    }

    private static void baZiShow(CustomerList customers) {
        System.out.println("请输入你的阴历出生时间，精确到到分钟，输入格式为：2023,08,03,18,45");
        String birthtime = CMUtility.readDates();
        Lunar lunar = Lunar.fromYmdHms(CMUtility.scannerLunarYear(birthtime), CMUtility.scannerLunarMonth(birthtime), CMUtility.scannerLunarDay(birthtime), CMUtility.scannerLunarHour(birthtime), CMUtility.scannerLunarMinute(birthtime), 0);
        EightChar lunarInfo = lunar.getEightChar();
        System.out.println(lunarInfo);
        //排盘显示
        System.out.println("请输入你的性别（男/女）：");
        int genderSymbol = CMUtility.readChar();
        Yun yun = lunarInfo.getYun(genderSymbol);
        // 起运
        System.out.println("出生" + yun.getStartYear() + "年" + yun.getStartMonth() + "个月" + yun.getStartDay() + "天后起运");
        System.out.println("-------------------------大运------------------------");
        DaYun[] daYunArr = yun.getDaYun();
        int[] nowDunIndex = {-1};
        boolean isFirstMatch = true;
        showOtherDaYunFlowYear(daYunArr, nowDunIndex, isFirstMatch);//遍历大运，并获取今年坐在大运索引。
        System.out.println("----------------------------------------------------");
        System.out.println("---------------------目前大运流年---------------------");
        LiuNian[] LiuNianArr = daYunArr[nowDunIndex[0]].getLiuNian();
        toShowLiuNian(LiuNianArr);
        //是否需要存档
        boolean nextFlag = true;//用来结束下方do while
        boolean isAddRecord = false;//用来判断是否已添加记录
        System.out.println();
        do {
            System.out.println("----------------------------------------------------");
            System.out.println("请选择接下来的操作：");
            System.out.println("1. 查看其他大运的流年");
            System.out.println("2. 保存当前八字");
            System.out.println("3. 返回主界面");
            char nextKey = CMUtility.readMenuSelection();
            switch (nextKey) {
                case '1':
                    System.out.println("您的大运为：");
                    showOtherDaYunFlowYear(daYunArr, nowDunIndex, isFirstMatch);
                    System.out.println("请输入您需要查看的大运的序号，序号从0起：");
                    nowDunIndex[0] = CMUtility.readInt();
                    LiuNian[] LiuNianArrChoose = daYunArr[nowDunIndex[0]].getLiuNian();
                    toShowLiuNian(LiuNianArrChoose);
                    break;
                case '2':
                    if (!isAddRecord) {
                        System.out.println("请输入你的名字（昵称）：");
                        String name = CMUtility.readString(5);
                        addRecords(name, genderSymbol, birthtime, customers);
                        isAddRecord = true;
                    } else {
                        System.out.println("抱歉，你已经添加过记录了！，请不要重复添加");
                    }
                    break;
                case '3':
                    System.out.print("确认是否退出(Y/N)：");
                    char yn = CMUtility.readConfirmSelection();
                    if (yn == 'Y') {
                        nextFlag = false;
                    }
                    break;
            }
        } while (nextFlag);
    }

    /*
    用来遍历大运的方法，并且获取今年所在大运索引。
     */
    public static void showOtherDaYunFlowYear(DaYun[] daYunArr, int[] nowDunIndex, boolean isFirstMatch) {
        Year currentYear = Year.now(); // 获取当前年份，来确定今年位于哪步大运。
        for (int i = 0, j = daYunArr.length; i < j; i++) {
            DaYun daYun = daYunArr[i];
            System.out.println("大运[" + i + "] = " + daYun.getStartYear() + "年 " + daYun.getStartAge() + "岁 " + daYun.getGanZhi());
            if (isFirstMatch && daYun.getStartYear() > currentYear.getValue()) {
                nowDunIndex[0] = i;
                isFirstMatch = false;
            }
        }
    }

    /*
    用来遍历指定大运中流年的方法。
     */
    public static void toShowLiuNian(LiuNian[] LiuNianArr) {
        for (int i = 0, j = LiuNianArr.length; i < j; i++) {
            LiuNian liuNian = LiuNianArr[i];
            System.out.println("流年[" + i + "] = " + liuNian.getYear() + "年 " + liuNian.getAge() + "岁 " + liuNian.getGanZhi());
        }
    }

    private static void listAllRecords(CustomerList customerList) {
        System.out.println("---------------------八字记录列表---------------------");
        Customer[] custs = customerList.getAllCustomers();
        if (custs.length == 0) {
            System.out.println("没有八字记录！");
        } else {
            System.out.println("编号\t姓名\t性别\t出生时间");
            for (int i = 0; i < custs.length; i++) {
                System.out.println((i + 1) + "\t\t" + custs[i].getDetails());
            }
        }
        System.out.println("----------------------------------------------------\n");
        System.out.println("如果需要查看详细的排盘，请输入你需要查看的编号，如果不需要请输入(-1)。");
        int checkIndex = CMUtility.readInt();
        if (checkIndex == -1) {
            return;
        }
        System.out.println("你需要查看的八字记录为：" + custs[checkIndex - 1].getDetails());
        Lunar lunar = Lunar.fromYmdHms(CMUtility.scannerLunarYear(custs[checkIndex - 1].getBirthtime()),CMUtility.scannerLunarMonth(custs[checkIndex - 1].getBirthtime()),CMUtility.scannerLunarDay(custs[checkIndex - 1].getBirthtime()),CMUtility.scannerLunarHour(custs[checkIndex - 1].getBirthtime()),CMUtility.scannerLunarMinute(custs[checkIndex - 1].getBirthtime()),0);
        EightChar lunarInfo = lunar.getEightChar();
        Yun yun = lunarInfo.getYun(custs[checkIndex - 1].getGenderSymbol());
        System.out.println("出生" + yun.getStartYear() + "年" + yun.getStartMonth() + "个月" + yun.getStartDay() + "天后起运");
        System.out.println("-------------------------大运------------------------");
        DaYun[] daYunArr = yun.getDaYun();
        int[] nowDunIndex = {-1};
        boolean isFirstMatch = true;
        showOtherDaYunFlowYear(daYunArr, nowDunIndex, isFirstMatch);//遍历大运，并获取今年坐在大运索引。
        System.out.println("----------------------------------------------------");
        System.out.println("---------------------目前大运流年---------------------");
        LiuNian[] LiuNianArr = daYunArr[nowDunIndex[0]].getLiuNian();
        toShowLiuNian(LiuNianArr);
        boolean nextFlag = true;//用来结束下方do while
        System.out.println();
        do {
            System.out.println("----------------------------------------------------");
            System.out.println("请选择接下来的操作：");
            System.out.println("1. 查看其他大运的流年");
            System.out.println("2. 返回主界面");
            char nextKey = CMUtility.readMenuSelection();
            switch (nextKey) {
                case '1':
                    System.out.println("您的大运为：");
                    showOtherDaYunFlowYear(daYunArr, nowDunIndex, isFirstMatch);
                    System.out.println("请输入您需要查看的大运的序号，序号从0起：");
                    nowDunIndex[0] = CMUtility.readInt();
                    LiuNian[] LiuNianArrChoose = daYunArr[nowDunIndex[0]].getLiuNian();
                    toShowLiuNian(LiuNianArrChoose);
                    break;
                case '2':
                    System.out.print("确认是否退出(Y/N)：");
                    char yn = CMUtility.readConfirmSelection();
                    if (yn == 'Y') {
                        nextFlag = false;
                    }
                    break;
            }
        } while (nextFlag);

    }


    private static void deleteRecords(CustomerList customerList) {
        System.out.println("---------------------目前已记录八字列表---------------------");
        Customer[] custs = customerList.getAllCustomers();
        if (custs.length == 0) {
            System.out.println("没有八字记录！");
        } else {
            System.out.println("编号\t姓名\t性别\t出生时间");
            for (int i = 0; i < custs.length; i++) {
                System.out.println((i + 1) + "\t\t" + custs[i].getDetails());
            }
        }
        int deleteIndex = 0;
        Customer cust = null;
        for (; ; ) {
            System.out.print("请选择待删除客户编号(-1退出)：");
            deleteIndex = CMUtility.readInt();
            if (deleteIndex == -1) {
                return;
            }

            cust = customerList.getCustomer(deleteIndex - 1);
            if (cust == null) {
                System.out.println("无法找到指定客户！");
            } else
                break;
        }
        System.out.print("确认是否删除(Y/N)：");
        char yn = CMUtility.readConfirmSelection();
        if (yn == 'N')
            return;

        boolean flag = customerList.deleteCustomer(deleteIndex - 1);
        if (flag) {
            System.out
                    .println("---------------------删除完成---------------------");
        } else {
            System.out.println("----------无法找到指定客户,删除失败--------------");
        }
    }

    private static void modifyRecords(CustomerList customerList) {
        System.out.println("---------------------目前已记录八字列表---------------------");
        Customer[] custs = customerList.getAllCustomers();
        if (custs.length == 0) {
            System.out.println("没有八字记录！");
        } else {
            System.out.println("编号\t姓名\t性别\t出生时间");
            for (int i = 0; i < custs.length; i++) {
                System.out.println((i + 1) + "\t\t" + custs[i].getDetails());
            }
        }
        int modifyIndex = 0;
        Customer cust = null;
        for (; ; ) {
            System.out.print("请选择待修改客户编号(-1退出)：");
            modifyIndex = CMUtility.readInt();
            if (modifyIndex == -1) {
                return;
            }

            cust = customerList.getCustomer(modifyIndex - 1);
            if (cust == null) {
                System.out.println("无法找到指定客户！");
            } else
                break;
        }
        System.out.print("姓名(" + cust.getName() + ")：");
        String name = CMUtility.readString(4, cust.getName());

        System.out.print("性别(" + cust.gender(cust.getGenderSymbol()) + ")：");
        System.out.println("请输入你的性别（男/女）：");
        int genderSymbol = CMUtility.readChar();

        System.out.print("出生时间(" + cust.getBirthtime() + ")：");
        String birthtime = CMUtility.readDates();

        cust = new Customer(name, genderSymbol, birthtime);

        boolean flag = customerList.replaceCustomer(modifyIndex - 1, cust);
        if (flag) {
            System.out
                    .println("---------------------修改完成---------------------");
        } else {
            System.out.println("----------无法找到指定客户,修改失败--------------");
        }
    }


    private static void addRecords(String name, int genderSymbol, String birthtime, CustomerList customers) {
        Customer cust = new Customer(name, genderSymbol, birthtime);
        boolean flag = customers.addCustomer(cust);
        if (flag) {
            System.out.println("-------------------添加八字记录完成-------------------");
        } else {
            System.out.println("----------------记录已满,无法添加-----------------");
        }
    }

}