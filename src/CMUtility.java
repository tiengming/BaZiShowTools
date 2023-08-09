import java.util.*;

/**
 * CMUtility工具类：
 * 将不同的功能封装为方法，就是可以直接通过调用方法使用它的功能，而无需考虑具体的功能实现细节。
 */
public class CMUtility {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * 用于界面菜单的选择。该方法读取键盘，如果用户键入’1’-’5’中的任意字符，则方法返回。返回值为用户键入字符。
     */
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);
            c = str.charAt(0);
            if (c != '1' && c != '2' &&
                    c != '3' && c != '4' && c != '5') {
                System.out.print("选择错误，请重新输入：");
            } else break;
        }
        return c;
    }

    /**
     * 从键盘读取一个字符，并将其作为方法的返回值。
     */
    public static int readChar() {
        String str = readKeyBoard(1, false);
        int genderSymbol = 0;
        if (str.equals("男")) {
            genderSymbol = 1;
        }
        return genderSymbol;
    }

    /**
     * 从键盘读取一个字符，并将其作为方法的返回值。
     * 如果用户不输入字符而直接回车，方法将以defaultValue 作为返回值。
     */
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }

    /**
     * 从键盘读取一个长度不超过2位的整数，并将其作为方法的返回值。
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(2, false);
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }

    /**
     * 从键盘读取一个长度不超过2位的整数，并将其作为方法的返回值。
     * 如果用户不输入字符而直接回车，方法将以defaultValue 作为返回值。
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(2, true);
            if (str.equals("")) {
                return defaultValue;
            }

            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }

    /**
     * 从键盘读取一个长度不超过limit的字符串，并将其作为方法的返回值。
     */
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * 从键盘读取一个长度不超过limit的字符串，并将其作为方法的返回值。
     * 如果用户不输入字符而直接回车，方法将以defaultValue 作为返回值。
     */
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValue : str;
    }

    /**
     * 用于确认选择的输入。该方法从键盘读取‘Y’或’N’，并将其作为方法的返回值。
     */
    public static char readConfirmSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("选择错误，请重新输入：");
            }
        }
        return c;
    }

    private static String readKeyBoard(int limit, boolean blankReturn) {
        String line = "";

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() == 0) {
                if (blankReturn) return line;
                else continue;
            }

            if (line.length() < 1 || line.length() > limit) {
                System.out.print("输入长度（不大于" + limit + "）错误，请重新输入：");
                continue;
            }
            break;
        }

        return line;
    }

    public static String readDates() {
        String line = "";
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() < 1 || line.length() > 20) {
                System.out.print("请检查你输入的时间，输入长度（不大于20）错误，请重新输入：");
                continue;
            }
        return line;
        }
        return line;
    }
    public static int substringToInt(String str, int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex < str.length() && startIndex <= endIndex) {
            String subStr = str.substring(startIndex, endIndex + 1);
            return Integer.parseInt(subStr);
        } else {
            throw new IndexOutOfBoundsException("Invalid index range");
        }
    }
    public static int scannerLunarYear(String cutdate) {
        int scannerlunaryear = substringToInt(cutdate, 0, 3);
        return scannerlunaryear;
    }
    public static int scannerLunarMonth(String cutdate) {
       int scannerlunarmonth = substringToInt(cutdate, 5, 6);
        return scannerlunarmonth;
    }
    public static int scannerLunarDay(String cutdate) {
        int scannerlunarday = substringToInt(cutdate, 8, 9);
        return scannerlunarday;
    }
    public static int scannerLunarHour(String cutdate) {
        int scannerlunarhour = substringToInt(cutdate, 11, 12);
        return scannerlunarhour;
    }
    public static int scannerLunarMinute(String cutdate) {
        int scannerlunarminute = substringToInt(cutdate, 14, 15);
        return scannerlunarminute;
    }
}

