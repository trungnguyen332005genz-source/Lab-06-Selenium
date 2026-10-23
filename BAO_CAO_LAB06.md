# BÁO CÁO KẾT QUẢ THỰC HÀNH KIỂM THỬ TỰ ĐỘNG - LAB 06 SELENIUM

**Đề tài:** Tự động hóa kiểm thử biểu mẫu đăng ký sinh viên (Student Registration Form)  
**Website kiểm thử:** [DemoQA Practice Form](https://demoqa.com/automation-practice-form)  
**Môi trường phát triển:** 
* IDE: Eclipse IDE for Enterprise Java and Web Developers - 2026-09
* Ngôn ngữ: Java 17+ (JDK 25)
* Công cụ quản lý: Apache Maven 3.9+
* Framework kiểm thử: Selenium WebDriver 4.25.0, TestNG 7.10.2
* Trình duyệt: Google Chrome

---

## 1. MỤC TIÊU BÀI THỰC HÀNH
Xây dựng kịch bản kiểm thử tự động toàn diện cho trang thực hành DemoQA Practice Form nhằm:
- Làm chủ thao tác Selenium WebDriver với đa dạng các thành phần web: Text Box, Radio Button, Checkbox, Date Picker dropdowns, File Upload, Dynamic Dropdowns, Modal Dialogs.
- Áp dụng kỹ thuật Explicit Wait tại các bước tương tác quan trọng nhằm tăng tính ổn định của bài kiểm thử.
- Thiết kế kiểm thử chuẩn theo mô hình **Page Object Model (POM)**: phân tách rõ ràng giữa cấu trúc giao diện (Locators & Page Actions) và kịch bản kiểm thử (Test Logic & Assertions).
- Triển khai kỹ thuật **Data-Driven Testing (DDT)** thông qua TestNG `@DataProvider` để kiểm thử form với nhiều bộ dữ liệu khác nhau.
- Tự động ghi nhận thông tin ra Console, bắt lỗi và chụp ảnh màn hình minh chứng tại các mốc thời điểm quan trọng (trước submit và sau submit).

---

## 2. CẤU TRÚC DỰ ÁN (PROJECT STRUCTURE)

```
Lab 06 Selenium/
├── pom.xml                                      # Quản lý thư viện Maven (Selenium 4, TestNG, Commons-IO)
├── mvn.cmd                                      # Script hỗ trợ chạy lệnh Maven nhanh
├── .project / .classpath / .settings            # Cấu hình chuẩn cho Eclipse IDE 2026-09
├── .gitignore
├── BAO_CAO_LAB06.md                             # Báo cáo kết quả chạy kiểm thử
├── README.md                                    # Hướng dẫn chi tiết cài đặt và sử dụng
├── screenshots/                                 # Thư mục chứa ảnh chụp tự động
│   ├── before_submit_20261023_190540.png        # Ảnh chụp trước khi bấm Submit
│   └── after_submit_20261023_190540.png         # Ảnh chụp sau khi Modal xác nhận xuất hiện
└── src/
    ├── main/java/com/demoqa/
    │   ├── models/
    │   │   └── StudentModel.java                # Đối tượng dữ liệu chứa thông tin sinh viên
    │   └── pages/
    │       ├── BasePage.java                    # Base class chứa WebDriver, Explicit Wait & JS Executor
    │       └── PracticeFormPage.java            # Page Object cho trang DemoQA Form
    └── test/
        ├── java/com/demoqa/tests/
        │   ├── BaseTest.java                    # Quản lý vòng đời WebDriver (@Before/@After) & captureScreen
        │   ├── FormBasicRequirementsTest.java   # Kiểm thử tuần tự các Yêu cầu 1 đến 5
        │   ├── PracticeFormPOMTest.java         # Yêu cầu 6 & 8: E2E POM Test & Chụp màn hình
        │   └── PracticeFormDataDrivenTest.java  # Yêu cầu 7: Data-Driven với 3 bộ dữ liệu
        └── resources/
            ├── sample_upload.txt                # Tệp mẫu phục vụ kiểm thử Upload File
            └── testng.xml                       # File cấu hình Suite chạy toàn bộ bài kiểm thử
```

---

## 3. NỘI DUNG CHI TIẾT CÁC YÊU CẦU & KẾT QUẢ THỰC HIỆN

### Yêu cầu 1: Mở trang và kiểm tra tiêu đề
- **Mã nguồn:** `FormBasicRequirementsTest.java` (`testRequirement1_OpenPageAndCheckTitle`)
- **Thực hiện:**
  - Mở trình duyệt Google Chrome với cấu hình tối ưu.
  - Điều hướng tới `https://demoqa.com/automation-practice-form`.
  - Kiểm tra `driver.getCurrentUrl()` bằng Assert: Trùng khớp chính xác với URL yêu cầu.
  - Kiểm tra `driver.getTitle()`: Tiêu đề không null và không rỗng.
- **Kết quả Console:**
  ```text
  === BẮT ĐẦU YÊU CẦU 1: KIỂM TRA MỞ TRANG VÀ TIÊU ĐỀ ===
  URL hiện tại: https://demoqa.com/automation-practice-form
  Tiêu đề trang: 'demosite'
  === HOÀN THÀNH YÊU CẦU 1 THÀNH CÔNG ===
  ```

### Yêu cầu 2: Điền thông tin cá nhân
- **Mã nguồn:** `FormBasicRequirementsTest.java` (`testRequirement2_FillPersonalInformation`)
- **Thực hiện:**
  - Điền Họ: `Nguyen`, Tên: `Van A`, Email: `vana.nguyen@example.com`, Giới tính: `Male`, Số điện thoại: `0987654321`.
  - Xác minh giá trị trên form qua thuộc tính `value`.
  - In toàn bộ thông tin đã nhập ra Console.
- **Kết quả Console:**
  ```text
  === BẮT ĐẦU YÊU CẦU 2: ĐIỀN THÔNG TIN CÁ NHÂN ===
  --- NỘI DUNG ĐÃ NHẬP VÀO FORM ---
  Họ: Nguyen
  Tên: Van A
  Email: vana.nguyen@example.com
  Số điện thoại: 0987654321
  === HOÀN THÀNH YÊU CẦU 2 THÀNH CÔNG ===
  ```

### Yêu cầu 3: Chọn ngày sinh bằng date picker
- **Mã nguồn:** `FormBasicRequirementsTest.java` (`testRequirement3_SelectDateOfBirthPicker`)
- **Thực hiện:**
  - Nhấp vào trường `#dateOfBirthInput` để kích hoạt React Datepicker.
  - Sử dụng đối tượng `Select` để chọn Tháng `May` và Năm `1998`.
  - Tìm và nhấp chọn Ngày `15` trong tháng đang chọn.
  - Kiểm tra định dạng ngày sinh hiển thị (`15 May 1998`).
- **Kết quả Console:**
  ```text
  === BẮT ĐẦU YÊU CẦU 3: CHỌN NGÀY SINH BẰNG DATE PICKER ===
  Giá trị ngày sinh hiển thị trong ô input: 15 May 1998
  === HOÀN THÀNH YÊU CẦU 3 THÀNH CÔNG ===
  ```

### Yêu cầu 4: Tải tệp lên form
- **Mã nguồn:** `FormBasicRequirementsTest.java` (`testRequirement4_UploadFile`)
- **Thực hiện:**
  - Chuẩn bị file `src/test/resources/sample_upload.txt`.
  - Sử dụng phương thức `sendKeys(absolutePath)` vào thẻ `input[type='file']` (`#uploadPicture`).
  - Kiểm tra tên file ghi nhận tại trường upload.
- **Kết quả Console:**
  ```text
  === BẮT ĐẦU YÊU CẦU 4: TẢI TỆP LÊN FORM ===
  Tên file ghi nhận sau khi upload: sample_upload.txt
  === HOÀN THÀNH YÊU CẦU 4 THÀNH CÔNG ===
  ```

### Yêu cầu 5: Kiểm tra thông báo xác nhận sau submit
- **Mã nguồn:** `FormBasicRequirementsTest.java` (`testRequirement5_SubmitAndVerifyModal`)
- **Thực hiện:**
  - Điền đầy đủ thông tin hợp lệ vào form, nhấn nút Submit.
  - Áp dụng **Explicit Wait** chờ Modal `.modal-content` hiển thị.
  - Kiểm tra tiêu đề modal: `"Thanks for submitting the form"`.
  - Trích xuất bảng kết quả và so sánh toàn bộ thông tin với dữ liệu gốc đã nhập.
  - Ghi toàn bộ kết quả kiểm tra ra Console.
- **Kết quả Console:**
  ```text
  === BẮT ĐẦU YÊU CẦU 5: XÁC NHẬN SAU KHI SUBMIT FORM ===
  Tiêu đề modal xác nhận: Thanks for submitting the form
  --- KẾT QUẢ XÁC NHẬN TRÊN BẢNG MODAL ---
    Student Name : Hoang Le
    Address : 123 Vo Thi Sau, Quan 3, TP.HCM
    Student Email : hoang.le@example.com
    Date of Birth : 20 October,2000
    Gender : Male
    Mobile : 0912345678
  === HOÀN THÀNH YÊU CẦU 5 THÀNH CÔNG ===
  ```

### Yêu cầu 6: Tổ chức theo Page Object Model (POM)
- Tách biệt hoàn toàn:
  - `PracticeFormPage`: Đóng gói toàn bộ Locators (`By`) và các thao tác giao diện (`open`, `setFirstName`, `setDateOfBirth`, `uploadFile`, `submit`, `getConfirmationModalData`, `closeConfirmationModal`).
  - `BasePage`: Chứa các tiện ích dùng chung, phương thức cuộn trang, xử lý che khuất (JavascriptExecutor), và Explicit Wait.
  - `PracticeFormPOMTest`: Lớp kiểm thử chỉ chứa logic Assertions, giúp mã nguồn rõ ràng, dễ bảo trì, dễ mở rộng.

### Yêu cầu 7: Kiểm thử với nhiều bộ dữ liệu (Data-Driven Testing)
- **Mã nguồn:** `PracticeFormDataDrivenTest.java`
- **Thực hiện:** Sử dụng TestNG `@DataProvider` cung cấp 3 bộ dữ liệu:
  1. **Bộ 1:** Nguyễn Văn An - Nam - 15/05/1998 - Môn học: Maths - Sở thích: Sports - Địa chỉ: Cầu Giấy, Hà Nội - Bang: NCR, City: Delhi.
  2. **Bộ 2:** Trần Thị Bích - Nữ - 20/10/2000 - Môn học: English - Sở thích: Reading - Địa chỉ: Lê Lợi, Đà Nẵng - Bang: NCR, City: Gurgaon.
  3. **Bộ 3:** Lê Hoàng Nam - Other - 05/01/1995 - Môn học: Computer Science - Sở thích: Music - Địa chỉ: Nguyễn Huệ, TP.HCM - Bang: Uttar Pradesh, City: Agra.
- Chạy tự động cùng một test case qua 3 lần lặp, mỗi lần đều xác nhận độc lập và in chi tiết ra Console.
- **Kết quả:** 3/3 bộ dữ liệu đều Passed.

### Yêu cầu 8: Tự động hóa quy trình hoàn chỉnh của form & Chụp ảnh màn hình
- **Mã nguồn:** `PracticeFormPOMTest.java` (`testCompleteStudentRegistrationFlow`)
- **Điểm nổi bật:**
  - Quy trình E2E khép kín: Mở form -> Điền đầy đủ tất cả các trường (Text, Radio, Checkbox, Select Date, Autocomplete Subjects, Upload File, Address, Dropdown State/City) -> Submit -> Kiểm tra Modal.
  - Sử dụng Explicit Wait tại ít nhất 2 vị trí quan trọng:
    1. Chờ phần tử input và datepicker sẵn sàng tương tác (`ExpectedConditions.elementToBeClickable`).
    2. Chờ Modal xác nhận hiển thị thành công (`ExpectedConditions.visibilityOfElementLocated`).
  - Chụp ảnh màn hình theo đúng mẫu code đặc tả trong PDF:
    - **Thời điểm 1 (Trước submit):** `screenshots/before_submit_*.png`
    - **Thời điểm 2 (Sau submit):** `screenshots/after_submit_*.png`
    - Tự động chụp ảnh khi test gặp lỗi (`FAIL_*`) qua `@AfterMethod`.

---

## 4. TỔNG HỢP KẾT QUẢ THỰC THI (TEST EXECUTION SUMMARY)

Khi chạy bộ kiểm thử qua Maven (`mvn test`):
```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
...
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 95.40 s -- in TestSuite
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

| STT | Tên Test Case | Mô tả | Trạng thái |
|:---:|:---|:---|:---:|
| 1 | `testRequirement1_OpenPageAndCheckTitle` | Yêu cầu 1: Mở Chrome, kiểm tra URL và Tiêu đề trang | **PASSED** |
| 2 | `testRequirement2_FillPersonalInformation` | Yêu cầu 2: Điền thông tin cá nhân (Họ, Tên, Email, Mobile) | **PASSED** |
| 3 | `testRequirement3_SelectDateOfBirthPicker` | Yêu cầu 3: Chọn ngày sinh bằng Date Picker | **PASSED** |
| 4 | `testRequirement4_UploadFile` | Yêu cầu 4: Tải tệp lên form và kiểm tra tên tệp | **PASSED** |
| 5 | `testRequirement5_SubmitAndVerifyModal` | Yêu cầu 5: Gửi form và xác minh Modal kết quả | **PASSED** |
| 6 | `testCompleteStudentRegistrationFlow` | Yêu cầu 6 & 8: Quy trình hoàn chỉnh E2E & Chụp ảnh màn hình | **PASSED** |
| 7 | `testSubmitFormWithMultipleDataSets [Bộ 1]` | Yêu cầu 7: Data-Driven Sinh viên An Nguyen Van | **PASSED** |
| 8 | `testSubmitFormWithMultipleDataSets [Bộ 2]` | Yêu cầu 7: Data-Driven Sinh viên Bich Tran Thi | **PASSED** |
| 9 | `testSubmitFormWithMultipleDataSets [Bộ 3]` | Yêu cầu 7: Data-Driven Sinh viên Nam Le Hoang | **PASSED** |

---

## 5. HƯỚNG DẪN IMPORT VÀO ECLIPSE IDE 2026-09

1. Khởi động **Eclipse IDE for Enterprise Java and Web Developers - 2026-09**.
2. Chọn Workspace mặc định hoặc thư mục mong muốn.
3. Trên thanh menu Eclipse, chọn:  
   `File` -> `Import...` -> `Maven` -> `Existing Maven Projects` -> Nhấn `Next`.
4. Tại ô **Root Directory**, nhấn `Browse...` và chọn thư mục:  
   `E:\Lab 06 Selenium\Lab 06 Selenium`
5. Eclipse sẽ tự động nhận diện file `pom.xml`. Nhấn `Finish`.
6. Chạy kiểm thử trực tiếp trên Eclipse:
   - Nhấp chuột phải vào file `src/test/resources/testng.xml` -> Chọn `Run As` -> `TestNG Suite`.
   - Hoặc nhấp chuột phải vào Project -> Chọn `Run As` -> `Maven test`.
