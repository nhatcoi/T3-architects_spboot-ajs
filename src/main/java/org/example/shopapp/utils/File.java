package org.example.shopapp.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public class File {
    // Lưu file vào thư mục uploads
    public static String storeFile(MultipartFile file) throws Exception {
        // Get original filename and clean it to avoid any unsafe characters
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Tạo một tên tệp tin duy nhất bằng cách thêm một UUID (mã định danh duy nhất toàn cầu) vào trước tên tệp gốc
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;

        // save file tại...
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra xem thư mục uploads đã tồn tại chưa. Nếu chưa, tạo mới thư mục này.
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Define the destination path for the file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // Sao chép nội dung của tệp tin từ InputStream của đối tượng MultipartFile vào vị trí đích. Nếu đã có tệp tin với tên tương tự ở thư mục đích, nó sẽ bị ghi đè (do tùy chọn REPLACE_EXISTING).
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
