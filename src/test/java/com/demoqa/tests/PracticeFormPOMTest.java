package com.demoqa.tests;

import com.demoqa.models.StudentModel;
import com.demoqa.pages.PracticeFormPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Lớp kiểm thử đáp ứng:
 * - Yêu cầu 6: Tổ chức theo mô hình Page Object Model (POM). Tách rời locator và UI logic,
 *   lớp test chỉ xử lý nghiệp vụ kiểm thử (Assertions).
 * - Yêu cầu 8: Tự động hóa quy trình hoàn chỉnh của form (End-to-End flow).
 *   Sử dụng explicit wait ở các bước quan trọng.
 *   Chụp ảnh màn hình ở ít nhất 2 thời điểm: trước khi submit và sau khi submit.
 */
public class PracticeFormPOMTest extends BaseTest {

    @Test(description = "Yêu cầu 6 & 8: Quy trình hoàn chỉnh tự động hóa Form theo mô hình POM")
    public void testCompleteStudentRegistrationFlow() throws IOException {
        System.out.println("==================================================================");
        System.out.println("BẮT ĐẦU TEST CASE: QUY TRÌNH TỰ ĐỘNG HÓA HOÀN CHỈNH FORM ĐĂNG KÝ");
        System.out.println("==================================================================");

        // 1. Chuẩn bị dữ liệu sinh viên hoàn chỉnh
        File uploadFile = new File("src/test/resources/sample_upload.txt");
        StudentModel student = new StudentModel(
                "Trung",
                "Nguyen",
                "trung.nguyen@example.com",
                "Male",
                "0987654321",
                "25",
                "December",
                "2001",
                Arrays.asList("Maths", "Computer Science"),
                Arrays.asList("Sports", "Music"),
                uploadFile.getAbsolutePath(),
                "123 Nguyen Trai, Thanh Xuan, Hanoi",
                "NCR",
                "Delhi"
        );

        // 2. Khởi tạo đối tượng Page Object và mở trang
        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        System.out.println("[Bước 1] Mở trang thành công: " + formPage.getCurrentUrl());
        Assert.assertEquals(formPage.getCurrentUrl(), PracticeFormPage.PAGE_URL, "URL trang không đúng!");

        // 3. Điền thông tin theo mô hình Page Object
        System.out.println("[Bước 2] Thực hiện điền toàn bộ dữ liệu sinh viên...");
        formPage.fillForm(student);

        // 4. CHỤP ẢNH MÀN HÌNH THỜI ĐIỂM 1: TRƯỚC KHI SUBMIT (Yêu cầu 8)
        System.out.println("[Bước 3] Chụp ảnh màn hình trước khi submit...");
        captureScreen("before_submit");

        // 5. Gửi form (Submit)
        System.out.println("[Bước 4] Nhấn nút Submit form...");
        formPage.submit();

        // 6. Chờ và kiểm tra Modal xác nhận xuất hiện (Explicit Wait)
        System.out.println("[Bước 5] Chờ Modal xác nhận xuất hiện bằng Explicit Wait...");
        boolean isModalDisplayed = formPage.isConfirmationModalDisplayed();
        Assert.assertTrue(isModalDisplayed, "Modal xác nhận không hiển thị sau khi submit form!");

        // 7. CHỤP ẢNH MÀN HÌNH THỜI ĐIỂM 2: SAU KHI SUBMIT (Yêu cầu 8)
        System.out.println("[Bước 6] Chụp ảnh màn hình sau khi submit...");
        captureScreen("after_submit");

        // 8. Kiểm tra tiêu đề modal
        String title = formPage.getConfirmationModalTitle();
        System.out.println("[Bước 7] Tiêu đề Modal: " + title);
        Assert.assertEquals(title, "Thanks for submitting the form", "Tiêu đề modal không đúng!");

        // 9. Lấy và kiểm tra dữ liệu đối chiếu với thông tin đã nhập
        Map<String, String> confirmationData = formPage.getConfirmationModalData();
        System.out.println("[Bước 8] Bảng thông tin xác nhận nhận được từ Modal:");
        confirmationData.forEach((key, value) -> System.out.println("   * " + key + ": " + value));

        Assert.assertEquals(confirmationData.get("Student Name"), student.getFullName(), "Họ và tên không khớp!");
        Assert.assertEquals(confirmationData.get("Student Email"), student.getEmail(), "Email không khớp!");
        Assert.assertEquals(confirmationData.get("Gender"), student.getGender(), "Giới tính không khớp!");
        Assert.assertEquals(confirmationData.get("Mobile"), student.getMobile(), "Số điện thoại không khớp!");
        Assert.assertTrue(confirmationData.get("Date of Birth").contains(student.getBirthDay() + " " + student.getBirthMonth() + "," + student.getBirthYear()), "Ngày sinh không khớp!");
        Assert.assertTrue(confirmationData.get("Subjects").contains("Maths"), "Môn học không chứa Maths!");
        Assert.assertTrue(confirmationData.get("Hobbies").contains("Sports"), "Sở thích không chứa Sports!");
        Assert.assertEquals(confirmationData.get("Picture"), uploadFile.getName(), "Tên file tải lên không khớp!");
        Assert.assertEquals(confirmationData.get("Address"), student.getCurrentAddress(), "Địa chỉ không khớp!");
        Assert.assertEquals(confirmationData.get("State and City"), student.getState() + " " + student.getCity(), "Bang và thành phố không khớp!");

        // 10. Đóng modal xác nhận
        formPage.closeConfirmationModal();

        System.out.println("==================================================================");
        System.out.println("HOÀN THÀNH TOÀN BỘ QUY TRÌNH KIỂM THỬ E2E THÀNH CÔNG RỰC RỠ!");
        System.out.println("==================================================================");
    }
}
