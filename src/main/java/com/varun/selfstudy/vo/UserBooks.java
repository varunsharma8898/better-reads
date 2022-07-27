package com.varun.selfstudy.vo;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table("books_by_userid_and_bookid")
public class UserBooks {

    // In the repository pattern, you have to specify just one class as primary-key in the repository
    // since we have 2 keys in the table, we have to create a primary key class and use it in the repo.
    @PrimaryKey
    private UserBooksPrimaryKey primaryKey;

    @Column("reading_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String readingStatus;

    @Column("started_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate startedDate;

    @Column("completed_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate completedDate;

    @Column("rating")
    @CassandraType(type = CassandraType.Name.INT)
    private int rating;

    @Column("review")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String review;
}
