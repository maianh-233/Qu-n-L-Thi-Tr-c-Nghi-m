package QuanLyTracNghiem.BUS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public InputValidator(){

    }

    //Hàm kiểm tra mật khẩu có đủ chữ, số , kí tự đặc biệt hay không
    public boolean isValidPassword(String password) {
        // Biểu thức chính quy kiểm tra chữ cái, số và ký tự đặc biệt
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public static boolean isValidVietnamesePhoneNumber(String phoneNumber) {
        // Kiểm tra định dạng số điện thoại bằng regex
        return phoneNumber.matches("^(0\\d{9}|\\+84\\d{9})$");
    }


    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
