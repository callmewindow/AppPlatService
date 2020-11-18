package serviceInterface;

import entity.OperationResponse;

public interface IDigitalSignatures {
	OperationResponse digitalSignatures(String signature);
    Boolean insertUserToDB(String userId, String account, String domain, String permission, String name);
}