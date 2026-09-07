# NovaTech

Ứng dụng Android bán điện thoại và đồ điện tử viết bằng Kotlin + Jetpack Compose.

## Chức năng hiện có

- Danh sách sản phẩm mẫu
- Tìm kiếm và lọc theo danh mục
- Xem chi tiết sản phẩm
- Thêm vào giỏ hàng
- Tăng, giảm và xóa sản phẩm khỏi giỏ
- Tính tổng tiền
- Thanh điều hướng dưới: Trang chủ, Danh mục, Giỏ hàng, Tài khoản
- Hình minh họa sản phẩm lưu cục bộ, không cần Internet
- Màn hình tài khoản mẫu
- Đăng ký, đăng nhập và đăng xuất bằng Firebase Authentication
- Lưu hồ sơ khách hàng vào Cloud Firestore
- Giỏ hàng được lưu cục bộ bằng Room, không mất khi đóng ứng dụng
- Hilt quản lý dependency injection cho ViewModel, database và Firebase
- Chế độ Sáng, Tối hoặc theo hệ thống được lưu bằng DataStore
- Design System Material 3 dùng chung cho màu sắc, chữ, hình dạng và nút
- Liquid Glass khúc xạ nền thật bằng Kyant Backdrop, có blur, lens, vibrancy và thanh điều hướng nổi
- Trạng thái tải, trống và lỗi được chuẩn hóa thành component dùng lại
- Trang chủ hiện đại với banner tự động chuyển
- Thanh tìm kiếm cố định và biểu tượng giỏ hàng có số lượng
- Flash Sale có đồng hồ đếm ngược
- Lưới sản phẩm hai cột, nhãn giảm giá và Freeship
- Thông báo nhanh khi thêm sản phẩm vào giỏ
- Logo ứng dụng NovaTech

## Cấu trúc mã nguồn

- `core/domain`: model, repository contract và kiểu kết quả dùng chung
- `core/data`: Room, DataStore, dữ liệu mẫu và repository implementation
- `di`: các Hilt module cho database, Firebase và coroutine dispatcher
- `feature/auth`: màn hình và ViewModel đăng nhập/đăng ký
- `feature/home`: ViewModel cửa hàng và giỏ hàng
- `ui/screens`: các màn hình mua sắm hiện tại
- `ui/components`: thành phần giao diện dùng lại
- `ui/theme`: Design System Material 3 và quản lý chế độ Sáng/Tối

Các phiên bản thư viện được quản lý tập trung trong `gradle/libs.versions.toml`.

## Chạy dự án

1. Cài **Android SDK Platform 36** trong SDK Manager.
2. Mở thư mục dự án `EC402` bằng Android Studio.
3. Chờ Android Studio đồng bộ Gradle.
4. Đảm bảo `app/google-services.json` đã có trên máy (file này không đưa lên GitHub).
5. Chọn máy ảo hoặc điện thoại Android thật.
6. Nhấn **Run app**.

Nếu Gradle yêu cầu JDK mới, chọn **Embedded JDK 21** tại Gradle JDK. Dự án dùng AGP `8.13.2`, Compose `1.10.0` và `io.github.kyant0:backdrop:1.0.3`.

Hiệu ứng blur cần Android 12 trở lên; hiệu ứng lens/khúc xạ đầy đủ cần Android 13 trở lên. Thiết bị cũ vẫn hiển thị bề mặt kính bán trong suốt.

## Trạng thái lộ trình

- Giai đoạn 1 — Kiến trúc và build: hoàn thành.
- Giai đoạn 2 — Design System, Liquid Glass và Dark Mode: hoàn thành.
- Giai đoạn 3 — Đồng bộ sản phẩm, danh mục và banner bằng Firestore: bước tiếp theo.
