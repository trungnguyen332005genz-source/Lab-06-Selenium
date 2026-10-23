# Lab 06 Selenium - Automation Testing DemoQA Practice Form

Dự án kiểm thử tự động Web sử dụng **Java**, **Selenium WebDriver 4**, **TestNG** và mô hình **Page Object Model (POM)** trên trang [DemoQA Practice Form](https://demoqa.com/automation-practice-form).

Tương thích hoàn toàn với:
- **Eclipse IDE for Enterprise Java and Web Developers - 2026-09**
- **Java 17+ (JDK 25)**
- **Google Chrome**

---

## 🚀 Các tính năng chính đã hoàn thành

1. **Kiểm tra Mở trang & Tiêu đề:** Xác minh URL và Title không rỗng.
2. **Điền thông tin cá nhân:** Họ, Tên, Email, Số điện thoại, in kết quả ra Console.
3. **Date Picker:** Lựa chọn Năm, Tháng, Ngày qua giao diện React Datepicker.
4. **Upload File:** Tải tệp tin mẫu từ máy tính và xác minh tên tệp.
5. **Kiểm tra Modal Xác nhận:** Đọc và đối chiếu dữ liệu bảng kết quả sau khi Submit.
6. **Page Object Model (POM):** Phân tách lớp Page (`PracticeFormPage`, `BasePage`) và Test.
7. **Data-Driven Testing (DDT):** Chạy kiểm thử tự động với 3 bộ dữ liệu độc lập.
8. **Quy trình E2E Hoàn chỉnh:** Tự động hóa đầy đủ quy trình, tích hợp **Explicit Wait** tại ít nhất 2 vị trí quan trọng, chụp ảnh màn hình trước và sau khi Submit lưu vào thư mục `screenshots/`.

---

## 🛠️ Hướng dẫn chạy kiểm thử

### Cách 1: Chạy bằng dòng lệnh (Command Line)
Tại thư mục gốc dự án:
```powershell
# Chạy toàn bộ Test Suite (9 Test cases)
.\mvn.cmd test

# Hoặc nếu máy đã có biến môi trường 'mvn':
mvn test

# Chạy riêng từng lớp kiểm thử:
.\mvn.cmd test -Dtest=FormBasicRequirementsTest
.\mvn.cmd test -Dtest=PracticeFormPOMTest
.\mvn.cmd test -Dtest=PracticeFormDataDrivenTest
```

### Cách 2: Chạy trong Eclipse IDE 2026-09
1. Mở **Eclipse IDE 2026-09**.
2. Chọn `File` -> `Import...` -> `Maven` -> `Existing Maven Projects`.
3. Chọn thư mục dự án: `E:\Lab 06 Selenium\Lab 06 Selenium`.
4. Nhấp chuột phải vào `src/test/resources/testng.xml` -> Chọn `Run As` -> `TestNG Suite`.

---

## 📸 Thư mục Ảnh chụp Màn hình (Screenshots)
Các ảnh chụp được lưu tự động tại `screenshots/`:
- `before_submit_*.png`: Ảnh chụp giao diện form sau khi đã điền đầy đủ dữ liệu trước khi nhấn Submit.
- `after_submit_*.png`: Ảnh chụp giao diện Modal thông báo xác nhận thành công sau khi Submit.
- `FAIL_*.png`: Ảnh chụp tự động nếu có bất kỳ bước kiểm thử nào gặp lỗi.

---

## 📂 Báo cáo chi tiết
Vui lòng xem file [BAO_CAO_LAB06.md](file:///E:/Lab%2006%20Selenium/Lab%2006%20Selenium/BAO_CAO_LAB06.md) để đọc báo cáo kết quả chi tiết từng kịch bản kiểm thử.
