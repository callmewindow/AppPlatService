package serviceInterface;

import entity.OperationResponse;

public interface ILocalStoreOperate {
    OperationResponse getDirInfo(String userId, String directory);

    OperationResponse createDir(String userId, String directory);

    OperationResponse copyDir(String userId, String directory, String type, String destDirectory);

    OperationResponse moveDir(String userId, String directory, String type, String destDirectory);

    OperationResponse renameDir(String userId, String directory, String type, String destDirectory);

    OperationResponse deletDir(String userId, String directory);

    OperationResponse downloadFile(String userId, String filePath);

    OperationResponse findFileMetadata(String userId, String filePath, boolean getFileInfo);

    OperationResponse uploadFile(String userId, String localFilePath, String fileName);

    OperationResponse copyFile(String userId, String filePath, String type, String destFileOrDir);

    OperationResponse moveFile(String userId, String filePath, String type, String destFileOrDir);

    OperationResponse renameFile(String userId, String filePath, String type, String destFileOrDir);

    OperationResponse deleteFile(String userId, String filePath);

    OperationResponse insertAccessInfo(String account, String name, String domain, String ip, String dateTime, String service, String url, String describe);
}
