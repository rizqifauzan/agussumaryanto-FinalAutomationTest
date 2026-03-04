Workflow Automation Test

1. Mempersiapkan library, dependencies, test framework, konfigurasi Cucumber Runtime, dan Allure dalam 1 file build.gradle

=== API Testing ===
Language: Java ;
Class Name: TestRunnerApi ;
URL: https://dummyapi.io/ ;
Framework testing: JUnit ;
Framework BDD: Cucumber ;
Build tool: Gradle ;
Testing library: RestAssured ;
Assertions library: assertj-core ;
Reporting library: Cucumber report & Allure report ;
Cucumber report path: FinalAutomationTest\reports\cucumber-results\ ;
Allure report path: FinalAutomationTest\build\reports\allure-report\

=== Web UI Testing ===
Language: Java ;
Class Name: TestRunnerWeb ;
URL: https://www.demoblaze.com ;
Framework testing: JUnit ;
Framework BDD: Cucumber ;
Build tool: Gradle ;
Testing library: Selenium ;
Assertions library: assertj-core ;
Reporting library: Cucumber report & Allure report ;
Cucumber report path: FinalAutomationTest\reports\cucumber-results\ ;
Allure report path: FinalAutomationTest\build\reports\allure-report\


2. Menuliskan test case dengan format gherkin dalam file .feature dan disimpan pada direktori resources 
--> Test case terdiri dari positif dan negatif

File .feature untuk API Test
	api.feature
File .feature untuk Web UI Test
	signup.feature -> testing signup
	login.feature -> testing login
	logout.feature -> testing logout
	product.feature -> testing browsing & add to cart
	payment.feature -> testing payment/checkout
	e2e_purchase.feature -> testing end-to-end full flow, dari login -> pilih produk -> checkout -> logouta

3. Membuat step definition berdasarkan setiap .feature file untuk memanggil page object model

=== Step definition untuk API Testing ===
ApiStepdefs.java -> implementasi step dari api.feature

=== Step definition untuk Web UI Testing ===
CommonSteps.java -> berisi langkah umum klik tombol, navigasi halaman
AuthSteps.java -> berisi Login, Logout, Signup, verifikasi
ProductSteps.java -> manajemen product, mencari produk, pilih produk, add-to-cart
CheckoutSteps.java -> proses checkout dan pembayaran

4. Membuat POM (Page Object Model) untuk API test dan Web UI test

=== POM untuk API Testing ===
ApiBasePage.java -> Base class untuk GET, POST, PUT, DELETE request
TagApiPage.java -> endpoint tag
UserApiPage.java -> endpoint user

=== POM untuk Web UI Testing ===
HomePage.java -> elemen dan aksi halaman utama
LoginPage.java -> form login
SignUpPage.java -> form registrasi
ProductPage.java -> halaman produk
CartPage.java -> interaksi dengan keranjang belanja
CheckoutPage.java -> form checkout & konfirmasi pembelian

5. Membuat hooks untuk test before dan after class (APIHooks.java dan WebHooks.java) supaya setiap test case bersih dan independen

6. Membuat runner untuk menghubungkan feature files (BDD) dengan step definitions (TestRunnerApi.java dan TestRunnerWeb.java) dan menghasilkan test report

7. Membuat config.properties untuk menyimpan konfigurasi seperti baseURL, informasi browser pilihan, timeout global

8. Membuat utils sebagai helper/utilitas untuk mendukung Web UI testing
DriverFactory.java → membuat instance WebDriver (Chrome/Firefox), setup driver path
ConfigReader.java → membaca konfigurasi dari config.properties
WaitHelper.java → menyediakan explicit/implicit waits agar interaksi UI stabil dan tidak flaky

9. Report test tersimpan dalam format .html pada direktori
FinalAutomationTest\reports\cucumber-results\ -> untuk cucumber report 
FinalAutomationTest\build\reports\allure-report\ -> untuk allure report

10. Menambahkan file .gitattributes (opsional) untuk menampilkan bahasa yang dipakai (pada repo github) serta menghilangkan warning dari hasil running nya

11. Mempersiapkan GitHub Actions dengan membuat file FinalAutomationTest.yml pada direktori .github/workflows untuk otomatisasi pengujian kode alur kerja langsung dari repositori 
