package com.example;

import static org.junit.jupiter.api.Assertions.*;

import com.example.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        //given
        User user = User.builder()
            .username("ivan2@gmail.com")
            .firstname("Ivan")
            .lastname("Ivanov")
            .birthDate(LocalDate.of(2000, 1, 19))
            .age(10)
            .build();

        //when
        String sql = """
            insert
            into
            %s
            (%s)
            values
            (%s)
            """;
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
            .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
            .orElse(user.getClass().getName());

        Field[]fields=user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(fields)
            .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElse(field.getName()))
            .collect(Collectors.joining(", "));

        String columnValues=Arrays.stream(fields)
            .map(field -> "?")
            .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName,columnNames,columnValues));

        Connection conn=null;
        PreparedStatement stmn=conn.prepareStatement(sql.formatted(tableName,columnNames,columnValues));
        int count=0;
        for(Field field:fields){
            count++;
            field.setAccessible(true);
            stmn.setObject(count,field.get(user));
        }

    }
}