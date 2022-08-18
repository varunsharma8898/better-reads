package com.varun.selfstudy.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.varun.selfstudy.vo.Author;

@Repository
public interface AuthorRepository extends CassandraRepository<Author, String> {
    // CassandraRepository<Author, String>  == here author is the table and string is the primary key

}
