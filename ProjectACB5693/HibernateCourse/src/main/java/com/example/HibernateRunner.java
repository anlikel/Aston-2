package com.example;

import com.example.converters.BirthDayConverter;
import com.example.entity.BirthDay;
import com.example.entity.User;
import com.example.enums.Role;
import java.time.LocalDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.SQLException;


public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection>pool=null;
//        DriverManager
//            .getConnection("db.url","db.user","db.password");
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(BirthDayConverter.class);
        configuration.configure();
        try (SessionFactory factory = configuration.buildSessionFactory();
            Session session = factory.openSession();) {

            session.beginTransaction();
            User user = User.builder()
                .username("ivan2@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(new BirthDay(LocalDate.of(2000, 1, 19)))
                .role(Role.Admin)
                .build();
            session.save(user);
            session.getTransaction().commit();
        }
    }
}
