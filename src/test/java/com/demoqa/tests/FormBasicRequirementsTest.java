package com.demoqa.tests;

import com.demoqa.pages.PracticeFormPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Kiểm thử chi tiết các yêu cầu từ 1 đến 5 theo tài liệu hướng dẫn bài thực hành Lab 06:
 * - Yêu cầu 1: Mở trang và kiểm tra tiêu đề.
 * - Yêu cầu 2: Điền thông tin cá nhân.
 * - Yêu cầu 3: Chọn ngày sinh bằng date picker.
 * - Yêu cầu 4: Tải tệp lên form.
 * - Yêu cầu 5: Kiểm tra thông báo xác nhận sau submit.
 */
public class FormBasicRequirementsTest extends BaseTest {

    /**
     * Yêu cầu 1: Mở trang và kiểm tra tiêu đề
     * a. Viết chương trình Selenium mở trình duyệt Chrome.
     * b. Truy cập trang https://demoqa.com/automation-practice-form.
     * c. Kiểm tra URL hiện tại có đúng với trang đã yêu cầu.
     * d. Kiểm tra tiêu đề trang không rỗng.
     */
    @Test(priority = 1, description = "Yêu cầu 1: Mở trang và kiểm tra tiêu đề, URL")
    public void testRequirement1_OpenPageAndCheckTitle() {
        System.out.println("=== BẮT ĐẦU YÊU CẦU 1: KIỂM TRA MỞ TRANG VÀ TIÊU ĐỀ ===");
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        // c. Kiểm tra URL hiện tại
        String currentUrl = formPage.getCurrentUrl();
        System.out.println("URL hiện tại: " + currentUrl);
        Assert.assertEquals(currentUrl, PracticeFormPage.PAGE_URL, "URL hiện tại không trùng khớp với URL yêu cầu!");

        // d. Kiểm tra tiêu đề trang không rỗng
        String pageTitle = formPage.getPageTitle();
        System.out.println("Tiêu đề trang: '" + pageTitle + "'");
        Assert.assertNotNull(pageTitle, "Tiêu đề trang bị null!");
        Assert.assertFalse(pageTitle.trim().isEmpty(), "Tiêu đề trang không được để rỗng!");
        System.out.println("=== HOÀN THÀNH YÊU CẦU 1 THÀNH CÔNG ===");
    }

    /**
     * Yêu cầu 2: Điền thông tin cá nhân
     * a. Mở trang form thực hành.
     * b. Điền các trường: Họ, Tên, Email, Số điện thoại.
     * c. Nhập dữ liệu hợp lệ vào từng trường.
     * d. In ra console nội dung đã nhập.
     */
    @Test(priority = 2, description = "Yêu cầu 2: Điền thông tin cá nhân và in ra console")
    public void testRequirement2_FillPersonalInformation() {
        System.out.println("=== BẮT ĐẦU YÊU CẦU 2: ĐIỀN THÔNG TIN CÁ NHÂN ===");
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        String firstName = "Van A";
        String lastName = "Nguyen";
        String email = "vana.nguyen@example.com";
        String mobile = "0987654321";

        formPage.setFirstName(firstName);
        formPage.setLastName(lastName);
        formPage.setEmail(email);
        formPage.selectGender("Male");
        formPage.setMobile(mobile);

        // Kiểm tra và in ra console nội dung đã nhập
        String actualFirstName = formPage.getFirstNameValue();
        String actualLastName = formPage.getLastNameValue();
        String actualEmail = formPage.getEmailValue();
        String actualMobile = formPage.getMobileValue();

        System.out.println("--- NỘI DUNG ĐÃ NHẬP VÀO FORM ---");
        System.out.println("Họ: " + actualLastName);
        System.out.println("Tên: " + actualFirstName);
        System.out.println("Email: " + actualEmail);
        System.out.println("Số điện thoại: " + actualMobile);

        Assert.assertEquals(actualFirstName, firstName, "Tên nhập vào không khớp!");
        Assert.assertEquals(actualLastName, lastName, "Họ nhập vào không khớp!");
        Assert.assertEquals(actualEmail, email, "Email nhập vào không khớp!");
        Assert.assertEquals(actualMobile, mobile, "Số điện thoại nhập vào không khớp!");

        System.out.println("=== HOÀN THÀNH YÊU CẦU 2 THÀNH CÔNG ===");
    }

    /**
     * Yêu cầu 3: Chọn ngày sinh bằng date picker
     * a. Mở trang form thực hành.
     * b. Chọn trường ngày sinh bằng giao diện date picker.
     * c. Chọn đúng năm, tháng và ngày theo dữ liệu cho trước.
     * d. Kiểm tra giá trị hiển thị sau khi chọn có đúng định dạng không.
     */
    @Test(priority = 3, description = "Yêu cầu 3: Chọn ngày sinh bằng Date Picker")
    public void testRequirement3_SelectDateOfBirthPicker() {
        System.out.println("=== BẮT ĐẦU YÊU CẦU 3: CHỌN NGÀY SINH BẰNG DATE PICKER ===");
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        String day = "15";
        String month = "May";
        String year = "1998";

        formPage.setDateOfBirth(day, month, year);

        String dobValue = formPage.getDateOfBirthValue();
        System.out.println("Giá trị ngày sinh hiển thị trong ô input: " + dobValue);

        // Định dạng hiển thị của DatePicker trên DemoQA là: "dd MMM yyyy" (ví dụ: "15 May 1998")
        Assert.assertTrue(dobValue.contains(day), "Giá trị hiển thị không chứa ngày " + day);
        Assert.assertTrue(dobValue.contains(month), "Giá trị hiển thị không chứa tháng " + month);
        Assert.assertTrue(dobValue.contains(year), "Giá trị hiển thị không chứa năm " + year);

        System.out.println("=== HOÀN THÀNH YÊU CẦU 3 THÀNH CÔNG ===");
    }

    /**
     * Yêu cầu 4: Tải tệp lên form
     * a. Mở một trang web có chức năng upload file.
     * b. Chọn một tệp ảnh hoặc tệp văn bản từ máy tính.
     * c. Gửi đường dẫn file vào ô upload.
     * d. Kiểm tra tên file đã được tải lên thành công.
     */
    @Test(priority = 4, description = "Yêu cầu 4: Tải tệp lên form và kiểm tra tên file")
    public void testRequirement4_UploadFile() {
        System.out.println("=== BẮT ĐẦU YÊU CẦU 4: TẢI TỆP LÊN FORM ===");
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        String relativePath = "src/test/resources/sample_upload.txt";
        File file = new File(relativePath);
        Assert.assertTrue(file.exists(), "File mẫu tải lên không tồn tại: " + file.getAbsolutePath());

        formPage.uploadFile(file.getAbsolutePath());

        String uploadedFileName = formPage.getUploadedFileName();
        System.out.println("Tên file ghi nhận sau khi upload: " + uploadedFileName);
        Assert.assertEquals(uploadedFileName, "sample_upload.txt", "Tên file upload không đúng!");

        System.out.println("=== HOÀN THÀNH YÊU CẦU 4 THÀNH CÔNG ===");
    }

    /**
     * Yêu cầu 5: Kiểm tra thông báo xác nhận sau submit
     * a. Điền đầy đủ thông tin hợp lệ vào form.
     * b. Nhấn nút Submit.
     * c. Chờ thông báo hoặc modal xác nhận xuất hiện.
     * d. Kiểm tra nội dung xác nhận có đúng với dữ liệu đã nhập.
     * e. Ghi kết quả kiểm tra ra console.
     */
    @Test(priority = 5, description = "Yêu cầu 5: Điền đầy đủ, Submit và kiểm tra Modal xác nhận")
    public void testRequirement5_SubmitAndVerifyModal() {
        System.out.println("=== BẮT ĐẦU YÊU CẦU 5: XÁC NHẬN SAU KHI SUBMIT FORM ===");
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        String firstName = "Hoang";
        String lastName = "Le";
        String email = "hoang.le@example.com";
        String mobile = "0912345678";
        String gender = "Male";
        String day = "20";
        String month = "October";
        String year = "2000";

        formPage.setFirstName(firstName);
        formPage.setLastName(lastName);
        formPage.setEmail(email);
        formPage.selectGender(gender);
        formPage.setMobile(mobile);
        formPage.setDateOfBirth(day, month, year);
        formPage.setCurrentAddress("123 Vo Thi Sau, Quan 3, TP.HCM");

        // Nhấn nút Submit
        formPage.submit();

        // Chờ modal xác nhận xuất hiện (sử dụng Explicit Wait bên trong BasePage/PracticeFormPage)
        Assert.assertTrue(formPage.isConfirmationModalDisplayed(), "Modal xác nhận không xuất hiện sau khi submit!");

        String modalTitle = formPage.getConfirmationModalTitle();
        System.out.println("Tiêu đề modal xác nhận: " + modalTitle);
        Assert.assertEquals(modalTitle, "Thanks for submitting the form", "Tiêu đề modal không chính xác!");

        var modalData = formPage.getConfirmationModalData();
        System.out.println("--- KẾT QUẢ XÁC NHẬN TRÊN BẢNG MODAL ---");
        modalData.forEach((k, v) -> System.out.println("  " + k + " : " + v));

        Assert.assertEquals(modalData.get("Student Name"), firstName + " " + lastName, "Student Name không khớp!");
        Assert.assertEquals(modalData.get("Student Email"), email, "Student Email không khớp!");
        Assert.assertEquals(modalData.get("Gender"), gender, "Gender không khớp!");
        Assert.assertEquals(modalData.get("Mobile"), mobile, "Mobile không khớp!");
        Assert.assertTrue(modalData.get("Date of Birth").contains(day + " " + month + "," + year), "Date of Birth không khớp!");

        System.out.println("=== HOÀN THÀNH YÊU CẦU 5 THÀNH CÔNG ===");
    }
}
