package com.ge.poc.datasource.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.ge.poc.datasource.model.Census;
import com.ge.poc.datasource.model.MSSqlUser;
import com.ge.poc.datasource.model.MongoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Configuration
public class DataAccessorServiceImpl implements DataAccessorService {


    @Autowired
    public DataSource msSqlDataSource;

    public final MongoTemplate mongoTemplate;

    public DataAccessorServiceImpl(MongoTemplate pMongoTemplate) {
        mongoTemplate = pMongoTemplate;
    }

    Logger logger = LoggerFactory.getLogger(DataAccessorServiceImpl.class);


    @Bean
    public DataSource msSqlDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://msqldb:1433;DatabaseName=tempdb;Connection Timeout=5;");
        dataSource.setUsername("sa");
        dataSource.setPassword("Anew!793Lengthy");
        return dataSource;
    }


    @Override
    public String getWelcomeMessage() {
        return "Welcome To Spring Boot + Docker DataSources Microservice@" + LocalDateTime.now() +"<br>";
    }

    @Override
    public List<MSSqlUser> getMsSqlUsers() {
        createMSSqlTableIfNotExists();
        try {
            Connection conn = msSqlDataSource.getConnection();
            String sqlCreate = "select * from Users";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery( sqlCreate );
            List<MSSqlUser> users = new ArrayList<>(  );
            while(result.next()) {
                users.add( new MSSqlUser(result.getString( "firstName"),result.getString( "lastName")));
            }
            conn.close();
            return users;
        } catch ( SQLException e ) {
            logger.error( "Error, couldn't connect to MSSQLServer datasource." );
            e.printStackTrace();
        }
        return new ArrayList<>(  );
    }

    @Override
    public List<MongoUser> getMongoDbUsers() {
        return mongoTemplate.findAll( MongoUser.class);
    }

    @Override
    public MongoUser addMongoDbUser( MongoUser user ) {
        return mongoTemplate.save(user);
    }

    @Override
    public boolean addUser( MSSqlUser user ) {
        createMSSqlTableIfNotExists();
        try {
            Connection conn = msSqlDataSource.getConnection();
            String sqlCreate = "INSERT INTO [dbo].[Users] ([firstName], [lastName]) VALUES (N'" +user.getFirstName() +"', N'"+user.getLastName()+"');";
            Statement stmt = conn.createStatement();
            boolean result =  stmt.execute( sqlCreate );
            conn.close();
            return true;
        } catch ( SQLException e ) {
            logger.error( "Error, couldn't Add the user to MSSQLServer datasource." );
            e.printStackTrace();
            return false;
        }
    }

    public void createMSSqlTableIfNotExists() {
        try {
            Connection conn = msSqlDataSource.getConnection();


            String sqlCreate = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Users' and xtype='U')\n" +
                    "BEGIN "
                    + "CREATE TABLE Users"
                    + "(id int IDENTITY(1,1) PRIMARY KEY,"
                    + "  firstName           VARCHAR(255) NOT NULL,"
                    + "   lastName VARCHAR(255))"
                    + "END";


            Statement stmt = conn.createStatement();
            stmt.execute(sqlCreate);
            conn.close();

        } catch ( SQLException e ) {
            logger.error( "Error, couldn't create the table in MSSQLServer datasource." );
            e.printStackTrace();
        }

    }
    @Override
    public List<Census> readFlatFile()
    {
        return loadObjectList( Census.class, "/census.csv" );

    }

    public <T> List<T> loadObjectList( Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            File file =new File(fileName);
            MappingIterator<T> readValues =
                    mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            logger.error( "The flatfile is not found." );
            return Collections.emptyList();
        }
    }



}
