package serviceInterface;

import entity.OperationResponse;

public interface IAccessStatisic {
    OperationResponse insertAccessStatic(String userId, String account, String domain, String url, String describe, String ip);
}
