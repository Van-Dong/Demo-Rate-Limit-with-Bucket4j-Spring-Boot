# Trên đây là 3 cách cài đặt rate limit cho api của ta bằng Bucket4j 
## Cách 1: Naive Rate Limit
* Chỉ dùng 1 Bucket cho 1 endpoint (api) => chỉ giới hạn tốc độ cho các public api được thôi
* Không hiệu quả cho lắm 

## Cách 2: Dùng Pricing Plan và cài trực tiếp trên method của controller 
* Phân loại người dùng thành Free Basic Professional và mỗi người dùng có rate limit khác nhau
* Mỗi người dùng có 1 api-key của riêng họ
* Cách này sẽ tạo 1 ConcurrentHashMap<String, Bucket> với key là api-key => có thể kiểm soát được rate limit cho mỗi người dùng
* Vấn để với người dùng free, do ta không dùng api-key cho họ nên mọi người dùng free sẽ dùng chung một Bucket :) 

## Cách 3: Dùng Pricing Plan và cài đặt ở Interceptor
* Giúp phân tách code rõ ràng hơn
* Có thể kiểm soát nhiều api một lúc 
* Ở cách này, tôi lại không cho người dùng free được truy cập vào (bởi ta cũng thường phải đăng nhập vào thời mới được dùng thử miễn phí mà)
* Cũng khá tốt, có thể triển khai thực tế 

## Cách 4: Dùng bucket4j-spring-boot-starter
* Mặc định là dùng naive rate limiting
* Cấu hình chỉ bằng file application.yml là xong (không cần viết class gì) => tuyệt vời
* Ở ví dụ trong baedlung thì họ dùng bộ đệm (Caffeine) để lưu api-key và Bucket nên thêm cấu hình cho bộ đệm khá nhiều
* Link: https://www.baeldung.com/spring-bucket4j 
* Cách này tôi chưa test