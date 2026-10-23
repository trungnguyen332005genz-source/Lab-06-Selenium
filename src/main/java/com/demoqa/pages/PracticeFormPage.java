package com.demoqa.pages;

import com.demoqa.models.StudentModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PracticeFormPage đại diện cho trang DemoQA Student Registration Form
 * theo mô hình Page Object Model (POM).
 * URL: https://demoqa.com/automation-practice-form
 */
public class PracticeFormPage extends BasePage {

    public static final String PAGE_URL = "https://demoqa.com/automation-practice-form";

    // Locators cho các trường thông tin cơ bản
    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By genderMaleLabel = By.cssSelector("label[for='gender-radio-1']");
    private final By genderFemaleLabel = By.cssSelector("label[for='gender-radio-2']");
    private final By genderOtherLabel = By.cssSelector("label[for='gender-radio-3']");
    private final By mobileInput = By.id("userNumber");

    // Locators Date Picker
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By monthSelect = By.className("react-datepicker__month-select");
    private final By yearSelect = By.className("react-datepicker__year-select");

    // Locators Subjects, Hobbies, File Upload
    private final By subjectsInput = By.id("subjectsInput");
    private final By hobbySportsLabel = By.cssSelector("label[for='hobbies-checkbox-1']");
    private final By hobbyReadingLabel = By.cssSelector("label[for='hobbies-checkbox-2']");
    private final By hobbyMusicLabel = By.cssSelector("label[for='hobbies-checkbox-3']");
    private final By uploadPictureInput = By.id("uploadPicture");

    // Locators Address, State, City, Submit
    private final By currentAddressInput = By.id("currentAddress");
    private final By stateInput = By.id("react-select-3-input");
    private final By cityInput = By.id("react-select-4-input");
    private final By submitButton = By.id("submit");

    // Locators Modal xác nhận sau submit
    private final By confirmationModal = By.cssSelector(".modal-content");
    private final By modalTitle = By.id("example-modal-sizes-title-lg");
    private final By modalTableRows = By.cssSelector(".modal-content tbody tr");
    private final By closeModalButton = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Mở trang DemoQA Practice Form
     */
    public PracticeFormPage open() {
        driver.get(PAGE_URL);
        removeAdsAndBanners();
        waitForVisibility(firstNameInput);
        return this;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void setFirstName(String firstName) {
        sendKeys(firstNameInput, firstName);
    }

    public String getFirstNameValue() {
        return waitForVisibility(firstNameInput).getAttribute("value");
    }

    public void setLastName(String lastName) {
        sendKeys(lastNameInput, lastName);
    }

    public String getLastNameValue() {
        return waitForVisibility(lastNameInput).getAttribute("value");
    }

    public void setEmail(String email) {
        sendKeys(emailInput, email);
    }

    public String getEmailValue() {
        return waitForVisibility(emailInput).getAttribute("value");
    }

    public void selectGender(String gender) {
        if ("Female".equalsIgnoreCase(gender)) {
            click(genderFemaleLabel);
        } else if ("Other".equalsIgnoreCase(gender)) {
            click(genderOtherLabel);
        } else {
            click(genderMaleLabel);
        }
    }

    public void setMobile(String mobile) {
        sendKeys(mobileInput, mobile);
    }

    public String getMobileValue() {
        return waitForVisibility(mobileInput).getAttribute("value");
    }

    /**
     * Chọn ngày sinh thông qua giao diện Date Picker
     * @param day Ngày (ví dụ "15" hoặc "05")
     * @param month Tháng bằng tiếng Anh (ví dụ "January", "May", "October")
     * @param year Năm (ví dụ "1998", "2000")
     */
    public void setDateOfBirth(String day, String month, String year) {
        WebElement dobElement = waitForVisibility(dateOfBirthInput);
        scrollToElement(dobElement);
        click(dateOfBirthInput);

        // Chờ giao diện date picker hiển thị
        waitForVisibility(monthSelect);

        // Chọn tháng
        Select selectMonth = new Select(waitForVisibility(monthSelect));
        selectMonth.selectByVisibleText(month);

        // Chọn năm
        Select selectYear = new Select(waitForVisibility(yearSelect));
        selectYear.selectByVisibleText(year);

        // Chọn ngày cụ thể trong tháng (loại trừ ngày thuộc tháng trước/sau)
        int dayNum = Integer.parseInt(day);
        String dayXpath = String.format("//div[contains(@class,'react-datepicker__day') and not(contains(@class,'outside-month')) and text()='%d']", dayNum);
        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath)));
        dayElement.click();
    }

    public String getDateOfBirthValue() {
        return waitForVisibility(dateOfBirthInput).getAttribute("value");
    }

    /**
     * Thêm môn học (Subjects)
     */
    public void addSubject(String subject) {
        WebElement element = waitForVisibility(subjectsInput);
        scrollToElement(element);
        element.sendKeys(subject);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Chọn sở thích (Hobbies)
     */
    public void selectHobby(String hobby) {
        if ("Sports".equalsIgnoreCase(hobby)) {
            click(hobbySportsLabel);
        } else if ("Reading".equalsIgnoreCase(hobby)) {
            click(hobbyReadingLabel);
        } else if ("Music".equalsIgnoreCase(hobby)) {
            click(hobbyMusicLabel);
        }
    }

    /**
     * Tải tệp lên form (Yêu cầu 4)
     * @param filePath Đường dẫn tương đối hoặc tuyệt đối của file
     */
    public void uploadFile(String filePath) {
        File file = new File(filePath);
        WebElement uploadElement = driver.findElement(uploadPictureInput);
        scrollToElement(uploadElement);
        uploadElement.sendKeys(file.getAbsolutePath());
    }

    public String getUploadedFileName() {
        WebElement uploadElement = driver.findElement(uploadPictureInput);
        String val = uploadElement.getAttribute("value");
        if (val != null && val.contains("\\")) {
            return val.substring(val.lastIndexOf('\\') + 1);
        }
        return val;
    }

    public void setCurrentAddress(String address) {
        sendKeys(currentAddressInput, address);
    }

    /**
     * Chọn State và City bằng ô nhập react-select
     */
    public void selectState(String state) {
        if (state != null && !state.isEmpty()) {
            WebElement stateEl = waitForVisibility(stateInput);
            scrollToElement(stateEl);
            stateEl.sendKeys(state);
            stateEl.sendKeys(Keys.ENTER);
        }
    }

    public void selectCity(String city) {
        if (city != null && !city.isEmpty()) {
            WebElement cityEl = waitForVisibility(cityInput);
            scrollToElement(cityEl);
            cityEl.sendKeys(city);
            cityEl.sendKeys(Keys.ENTER);
        }
    }

    /**
     * Gửi toàn bộ form bằng cách điền từ StudentModel
     */
    public void fillForm(StudentModel student) {
        if (student.getFirstName() != null) setFirstName(student.getFirstName());
        if (student.getLastName() != null) setLastName(student.getLastName());
        if (student.getEmail() != null) setEmail(student.getEmail());
        if (student.getGender() != null) selectGender(student.getGender());
        if (student.getMobile() != null) setMobile(student.getMobile());

        if (student.getBirthDay() != null && student.getBirthMonth() != null && student.getBirthYear() != null) {
            setDateOfBirth(student.getBirthDay(), student.getBirthMonth(), student.getBirthYear());
        }

        if (student.getSubjects() != null) {
            for (String sub : student.getSubjects()) {
                addSubject(sub);
            }
        }

        if (student.getHobbies() != null) {
            for (String h : student.getHobbies()) {
                selectHobby(h);
            }
        }

        if (student.getPicturePath() != null && !student.getPicturePath().isEmpty()) {
            uploadFile(student.getPicturePath());
        }

        if (student.getCurrentAddress() != null) {
            setCurrentAddress(student.getCurrentAddress());
        }

        if (student.getState() != null) {
            selectState(student.getState());
        }

        if (student.getCity() != null) {
            selectCity(student.getCity());
        }
    }

    /**
     * Bấm nút Submit form
     */
    public void submit() {
        removeAdsAndBanners();
        WebElement btn = waitForClickable(submitButton);
        scrollToElement(btn);
        try {
            btn.click();
        } catch (Exception e) {
            clickViaJs(btn);
        }
    }

    /**
     * Kiểm tra Modal xác nhận có hiển thị hay không (sử dụng Explicit Wait)
     */
    public boolean isConfirmationModalDisplayed() {
        try {
            return waitForVisibility(confirmationModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getConfirmationModalTitle() {
        return waitForVisibility(modalTitle).getText();
    }

    /**
     * Lấy toàn bộ dữ liệu xác nhận trong bảng kết quả dưới dạng Map<Label, Value>
     */
    public Map<String, String> getConfirmationModalData() {
        Map<String, String> data = new HashMap<>();
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(modalTableRows));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                String label = cols.get(0).getText().trim();
                String val = cols.get(1).getText().trim();
                data.put(label, val);
            }
        }
        return data;
    }

    /**
     * Đóng modal xác nhận
     */
    public void closeConfirmationModal() {
        WebElement closeBtn = driver.findElement(closeModalButton);
        scrollToElement(closeBtn);
        try {
            closeBtn.click();
        } catch (Exception e) {
            clickViaJs(closeBtn);
        }
    }
}
