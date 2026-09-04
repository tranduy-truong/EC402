# TechStore

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
- Trang chủ hiện đại với banner tự động chuyển
- Thanh tìm kiếm cố định và biểu tượng giỏ hàng có số lượng
- Flash Sale có đồng hồ đếm ngược
- Lưới sản phẩm hai cột, nhãn giảm giá và Freeship
- Thông báo nhanh khi thêm sản phẩm vào giỏ
- Logo ứng dụng TechStore

## Cấu trúc mã nguồn

- `ui/screens`: các màn hình của ứng dụng
- `ui/components`: thành phần giao diện dùng lại
- `ui/theme`: màu sắc và giao diện Material 3
- `util`: hàm tiện ích

## Chạy dự án

1. Mở thư mục `TechStore` bằng Android Studio.
2. Chờ Android Studio đồng bộ Gradle.
3. Chọn máy ảo hoặc điện thoại Android thật.
4. Nhấn **Run app**.

Nếu Android Studio hỏi tạo/cập nhật Gradle Wrapper, chọn phiên bản Gradle tương thích với AGP 8.13.0.

## Bước phát triển tiếp theo

Thêm ảnh thật bằng Coil, màn hình đăng nhập, địa chỉ nhận hàng, thanh toán và kết nối Firebase/REST API.
