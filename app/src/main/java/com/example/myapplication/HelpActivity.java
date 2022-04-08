package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView tv = findViewById(R.id.tv);
        tv.setText("1. Lập trình sự kiện MainActivity.java\n" +
                "Chọn Quản lý người dùng thì chuyển sang UserActivity\n" +
                "Chọn Test Quiz thì chuyển sang TestQuizActivity\n" +
                "Chọn Help thì hiển thị thông tin trợ giúp\n" +
                "Tạo ít nhất 6 bản ghi dữ liệu listuser\n" +
                "2. Xử lý sự kiện UserActivity.java thực hiện yêu cầu sau\n" +
                "a. Đọc dữ liệu từ listuser và load lên ListView\n" +
                "b. Khi lựa chọn một Item trong ListView thì hiển thị giá trị lên màn hình (Toast).\n" +
                "c. Sau khi nhập tên user (Id, Name) vào và click btthem thì lớp tự động thêm dữ liệu vào listuser đồng thời cập nhật trên giao diện \n" +
                "d. Sau khi nhập tên user (Id, Name) vào và click btsua thì lấy nội dung của EditText và thay đổi tuong ứng đối tượng đang được chọn, sửa dữ liệu tương ứng listuser, đồng thời cập nhật trên giao diện \n" +
                "e. click btxoa thì hiển thị ra màn hình kèm thông báo AlertDialog hỏi lại người dùng có xóa ko? Có thực hiện xóa trong table user trong CSDL và cập nhật trên giao diện, ko thì quay lại activity_main.xml \n" +
                "f. Khi click vào listview thì hiển thị menu gồm Thêm/sửa và xóa\n" +
                "g. Lập trình sự kiện cho các item của menu giống như ý 234 ở trên");
    }
}