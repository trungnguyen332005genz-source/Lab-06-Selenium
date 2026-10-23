package com.demoqa.tests;

import com.demoqa.models.StudentModel;
import com.demoqa.pages.PracticeFormPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Yêu cầu 7: Kiểm thử với nhiều bộ dữ liệu (Data-Driven Testing).
 * - Tạo ít nhất 3 bộ dữ liệu đầu vào khác nhau.
 * - Chạy cùng một test case với nhiều bộ dữ liệu.
 * - Mỗi bộ dữ liệu phải có kết quả kiểm tra riêng.
 * - In ra console thông tin của từng lần chạy.
 */
public class PracticeFormDataDrivenTest extends BaseTest {

    @DataProvider(name = "studentRegistrationData")
    public Object[][] provideStudentData() {
        File uploadFile = new File("src/test/resources/sample_upload.txt");
        String filePath = uploadFile.getAbsolutePath();

        return new Object[][]{
                // Bộ dữ liệu 1: Nam, sinh năm 1998, sở thích Thể thao
                {
                        1,
                        new StudentModel(
                                "An",
                                "Nguyen Van",
                                "an.nguyen@example.com",
                                "Male",
                                "0987654321",
                                "15",
                                "May",
                                "1998",
                                Collections.singletonList("Maths"),
                                Collections.singletonList("Sports"),
                                filePath,
                                "Số 15 đường Cầu Giấy, Hà Nội",
                                "NCR",
                                "Delhi"
                        )
                },
                // Bộ dữ liệu 2: Nữ, sinh năm 2000, sở thích Đọc sách
                {
                        2,
                        new StudentModel(
                                "Bich",
                                "Tran Thi",
                                "bich.tran@example.com",
                                "Female",
                                "0912345678",
                                "20",
                                "October",
                                "2000",
                                Collections.singletonList("English"),
                                Collections.singletonList("Reading"),
                                filePath,
                                "Số 88 đường Lê Lợi, Đà Nẵng",
                                "NCR",
                                "Gurgaon"
                        )
                },
                // Bộ dữ liệu 3: Khác, sinh năm 1995, sở thích Âm nhạc
                {
                        3,
                        new StudentModel(
                                "Nam",
                                "Le Hoang",
                                "nam.le@example.com",
                                "Other",
                                "0933445566",
                                "5",
                                "January",
                                "1995",
                                Collections.singletonList("Computer Science"),
                                Collections.singletonList("Music"),
                                filePath,
                                "Số 200 đường Nguyễn Huệ, TP.HCM",
                                "Uttar Pradesh",
                                "Agra"
                        )
                }
        };
    }

    @Test(dataProvider = "studentRegistrationData", description = "Yêu cầu 7: Data-Driven Testing với 3 bộ dữ liệu khác nhau")
    public void testSubmitFormWithMultipleDataSets(int testCaseId, StudentModel student) {
        System.out.println("------------------------------------------------------------------");
        System.out.println(">>> ĐANG CHẠY BỘ DỮ LIỆU #" + testCaseId + " CHO SINH VIÊN: " + student.getFullName() + " <<<");
        System.out.println("Thông tin đầu vào: " + student);

        PracticeFormPage formPage = new PracticeFormPage(driver);
        formPage.open();

        // 1. Điền form
        formPage.fillForm(student);

        // 2. Submit
        formPage.submit();

        // 3. Kiểm tra Modal xác nhận xuất hiện
        boolean isDisplayed = formPage.isConfirmationModalDisplayed();
        Assert.assertTrue(isDisplayed, "[Bộ " + testCaseId + "] Modal xác nhận không xuất hiện sau khi submit!");

        // 4. Lấy dữ liệu và xác thực độc lập cho từng bộ dữ liệu
        Map<String, String> data = formPage.getConfirmationModalData();
        System.out.println("[Bộ " + testCaseId + "] Kết quả xác nhận từ Modal:");
        System.out.println("   -> Họ và tên: " + data.get("Student Name"));
        System.out.println("   -> Email: " + data.get("Student Email"));
        System.out.println("   -> Giới tính: " + data.get("Gender"));
        System.out.println("   -> Số điện thoại: " + data.get("Mobile"));
        System.out.println("   -> Ngày sinh: " + data.get("Date of Birth"));

        Assert.assertEquals(data.get("Student Name"), student.getFullName(),
                "[Bộ " + testCaseId + "] Họ và tên không khớp!");
        Assert.assertEquals(data.get("Student Email"), student.getEmail(),
                "[Bộ " + testCaseId + "] Email không khớp!");
        Assert.assertEquals(data.get("Gender"), student.getGender(),
                "[Bộ " + testCaseId + "] Giới tính không khớp!");
        Assert.assertEquals(data.get("Mobile"), student.getMobile(),
                "[Bộ " + testCaseId + "] Số điện thoại không khớp!");

        // 5. Đóng modal
        formPage.closeConfirmationModal();

        System.out.println(">>> HOÀN THÀNH BỘ DỮ LIỆU #" + testCaseId + ": PASSED! <<<");
        System.out.println("------------------------------------------------------------------");
    }
}
