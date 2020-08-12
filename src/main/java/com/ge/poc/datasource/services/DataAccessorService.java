package com.ge.poc.datasource.services;

import com.ge.poc.datasource.model.Census;
import com.ge.poc.datasource.model.MSSqlUser;
import com.ge.poc.datasource.model.MongoUser;

import java.util.List;

public interface DataAccessorService {
    String getWelcomeMessage();
    List<MSSqlUser> getMsSqlUsers();
    List<MongoUser> getMongoDbUsers();
    MongoUser addMongoDbUser( MongoUser user);
    boolean addUser( MSSqlUser MSSqlUser );
    List<Census> readFlatFile();

}
