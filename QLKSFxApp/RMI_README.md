# Hướng dẫn chạy Project JavaFX Gradle với RMI Client/Server

## Giới thiệu

Project này sử dụng mô hình RMI (Remote Method Invocation) để tách biệt lớp giao diện người dùng (JavaFX) và lớp truy cập dữ liệu (DAO). Điều này cho phép ứng dụng hoạt động theo mô hình phân tán, với client và server có thể chạy trên các máy tính khác nhau.

## Cấu trúc mô hình RMI

1. **RMI Server**: Chứa các đối tượng DAO và xử lý các yêu cầu từ client
2. **RMI Client**: Ứng dụng JavaFX sử dụng RMIService để gọi các phương thức từ xa

## Chuẩn bị

1. Đảm bảo tất cả các entity đều triển khai Serializable:
   ```java
   import java.io.Serializable;

   @Entity
   public class TenEntity implements Serializable {
       private static final long serialVersionUID = 1L;
       // Các thuộc tính và phương thức
   }
   ```

2. Đảm bảo file `rmi.policy` đã được đặt trong thư mục gốc của dự án

## Cách chạy ứng dụng

### Bước 1: Khởi động RMI Server

RMI Server phải được khởi động trước khi chạy ứng dụng JavaFX. Để khởi động RMI Server:

**Sử dụng batch file (Windows):**
```
run_rmi_server.bat
```

**Hoặc sử dụng Gradle trực tiếp:**
```
./gradlew runRMIServer
```

Khi RMI Server khởi động thành công, bạn sẽ thấy thông báo "RMI Server is ready!" và "All DAO services bound to registry".

### Bước 2: Khởi động ứng dụng JavaFX

Sau khi RMI Server đã khởi động, bạn có thể khởi động ứng dụng JavaFX:

```
./gradlew run
```

Ứng dụng JavaFX sẽ kết nối đến RMI Server và sử dụng các đối tượng DAO từ xa.

## Cách sử dụng RMI trong code JavaFX

Để sử dụng RMI trong các controller JavaFX, bạn có thể sử dụng class `RMIService`:

```java
// Lấy instance của RMIService
RMIService rmiService = RMIService.getInstance();

// Sử dụng các DAO từ xa
try {
    // Ví dụ: Lấy danh sách tài khoản
    List<TaiKhoan> taiKhoans = rmiService.getTaiKhoanDAO().getAllTaiKhoan();

    // Xử lý dữ liệu
    for (TaiKhoan tk : taiKhoans) {
        System.out.println(tk);
    }
} catch (Exception e) {
    // Xử lý ngoại lệ
    e.printStackTrace();
}
```

## Xử lý sự cố

### Lỗi kết nối RMI

Nếu ứng dụng JavaFX không thể kết nối đến RMI Server, hãy kiểm tra:

1. **RMI Server đã được khởi động chưa?**
   - Đảm bảo RMI Server đang chạy và hiển thị thông báo "RMI Server is ready!"

2. **Cổng 9090 có bị chặn không?**
   - Kiểm tra tường lửa và đảm bảo cổng 9090 được mở

3. **Địa chỉ IP có đúng không?**
   - Nếu chạy trên máy khác, hãy đảm bảo đã cấu hình đúng địa chỉ IP trong `java.rmi.server.hostname`

### Lỗi Serialization

Nếu gặp lỗi liên quan đến serialization, hãy đảm bảo:

1. **Tất cả các entity đều triển khai Serializable**
   - Mỗi entity phải có `implements Serializable` và `private static final long serialVersionUID = 1L;`

2. **Các entity có cùng cấu trúc trên cả client và server**
   - Đảm bảo các entity có cùng các trường và phương thức

## Chạy trên máy khác nhau

Nếu muốn chạy RMI Server và Client trên các máy khác nhau:

1. **Trên máy chủ (Server):**
   - Cấu hình `java.rmi.server.hostname` thành địa chỉ IP của máy chủ
   - Khởi động RMI Server

2. **Trên máy khách (Client):**
   - Cập nhật `RMIService.java` để sử dụng địa chỉ IP của máy chủ thay vì "localhost"
   - Khởi động ứng dụng JavaFX
