package util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveImageViewer {
    private static Drive driveService = null;
    private static final String APPLICATION_NAME = "Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "tokens/user--token";
//    private static final String CREDENTIALS_FILE_PATH = "D:\\Gg_API\\client_secret_1046008021894-lspc16jhqj8fnrfpbqj6q0bqm6n073ur.apps.googleusercontent.com.json"; // Cập nhật đúng đường dẫn
    private static final String CREDENTIALS_FILE_PATH = System.getenv("GOOGLE_CREDENTIALS_PATH");
    // Hàm lấy credentials cho Google Drive API
    private static Credential getCredentials() throws IOException, GeneralSecurityException {
        try {
            InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    Collections.singletonList("https://www.googleapis.com/auth/drive.readonly"))
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(getTokenDirectoryForUser("truongmaiduc18@gmail.com"))))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        } catch (TokenResponseException e) {
            if (e.getDetails() != null && "invalid_grant".equals(e.getDetails().getError())) {
                System.out.println("Token invalid or expired. Deleting and reauthorizing...");

                // Xóa thư mục token bị lỗi
                deleteTokenDirectory("truongmaiduc18@gmail.com");

                // Gọi lại chính hàm này để tạo mới
                return getCredentials();
            } else {
                throw e;
            }
        }
    }

    private static String getTokenDirectoryForUser(String email) {
        return "tokens/" + email;
    }
    public static void deleteTokenDirectory(String email) {
        java.io.File tokenDir = new java.io.File(getTokenDirectoryForUser(email));

        if (tokenDir.exists()) {
            for (java.io.File file : tokenDir.listFiles()) {
                file.delete();
            }
            tokenDir.delete();
            System.out.println("Deleted old token directory.");
        }
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Tạo Drive service
        Drive service = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Tìm file Untitle1.jpg trong folder pic-Truong
        String folderName = "pic_Truong";
        String fileName = "bell.png";

        List<File> file = findFileInSharedFolder(service,null, folderName, fileName);
        if (file.isEmpty()) {
            System.out.println("File not found!");
            return;
        }

        // Tải file xuống và hiển thị ảnh
//        java.io.File downloadedFile = downloadFile(service, file);
//        displayImage(downloadedFile);
        InputStream inputStream = service.files().get(file.get(0).getId()).executeMediaAsInputStream();
        displayImage(inputStream);
    }
    public  static Image  getImageByFileName(String fileName) throws GeneralSecurityException, IOException {
        // Tạo Drive service
        Drive service = getDriveService();

        // Tìm file Untitle1.jpg trong folder pic-Truong
        String folderName = "pic_Truong";

        File file = null;
        try {
            List<File> l = findFileInSharedFolder(service,null, folderName, fileName);
            file = l.isEmpty()?null:l.get(0);
        } catch (IOException e) {
            LoggerUtil.getLogger().error("Lỗi tìm file", e);
            return getPlaceholderImage();
        }
        // trả về ảnh
        assert file != null;
        InputStream inputStream = service.files().get(file.getId()).executeMediaAsInputStream();
        return ImageIO.read(inputStream);
    }
    public static Drive getDriveService() throws GeneralSecurityException, IOException {
        if (driveService == null) {
            driveService = new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
        return driveService;
    }
    // Các hàm hỗ trợ tìm file, tải file và hiển thị ảnh
    public static File findFileInFolder(Drive service, String folderName, String fileName) throws IOException {
        FileList folderList = service.files().list()
                .setQ("name = '" + folderName + "' and mimeType = 'application/vnd.google-apps.folder'")
                .setFields("files(id, name)")
                .execute();

        if (folderList.getFiles().isEmpty()) {
            System.out.println("Folder not found!");
            return null;
        }

        String folderId = folderList.getFiles().get(0).getId();

        FileList fileList = service.files().list()
                .setQ("'" + folderId + "' in parents and name = '" + fileName + "' and mimeType contains 'image/'")
                .setFields("files(id, name)")
                .execute();

        if (fileList.getFiles().isEmpty()) {
            System.out.println("File not found!");
            return null;
        }
        return fileList.getFiles().get(0);
    }
    public static List<File> findFileInSharedFolder(Drive service, String folderId, String folderName, String fileName) throws IOException {
        // Nếu đã có folderId, không cần tìm nữa
        if (folderId == null || folderId.isEmpty()) {
            FileList folderList = service.files().list()
                    .setQ("name = '" + folderName + "' and mimeType = 'application/vnd.google-apps.folder' and sharedWithMe")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .setSupportsAllDrives(true)
                    .execute();

            if (folderList.getFiles().isEmpty()) {
                System.out.println("Folder not found or not shared with you!");
                return Collections.emptyList();
            }
            folderId = folderList.getFiles().get(0).getId();
        }

        // Tìm file trong folder ID được chia sẻ
        FileList fileList = service.files().list()
                .setQ("'" + folderId + "' in parents and name = '" + fileName + "' and mimeType contains 'image/' and trashed = false")
                .setSpaces("drive")
                .setFields("files(id, name, webViewLink, webContentLink)")
                .setSupportsAllDrives(true)
                .execute();

        List<File> files = fileList.getFiles();
        if (files.isEmpty()) {
            System.out.println("File not found in shared folder!");
        }
        return files;
    }

    public static java.io.File downloadFile(Drive service, File file) throws IOException {
        java.io.File localFile = new java.io.File(file.getName());
        try (OutputStream outputStream = new FileOutputStream(localFile)) {
            service.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
        }
        System.out.println("File downloaded successfully: " + localFile.getAbsolutePath());
        return localFile;
    }

    public static void displayImage(InputStream inputStream ) throws IOException {
        Image image = ImageIO.read(inputStream);
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public static Image getPlaceholderImage() {
        return new ImageIcon("src/main/resources/pic/placeholder.png").getImage();
    }
}
